<!--第三步：初始化Datatables-->
$(document).ready( function () {

  var oTable;
  var table = $('#table');
  var opts = $.extend(true, {}, window.dataTablesSettings, {});

  //jquery_datatable插件的默认参数皮质
  var datatableConfig = {
    //"bStateSave": true, //是否把获得数据存入cookie
    //"bLengthChange":false, //关闭每页显示多少条数据
    "bProcessing": true, // 加载
    "bAutoWidth": true, // 自动宽度
    "bServerSide": true, // 这个用来指明是通过服务端来取数据
    "sAjaxSource": "/emp/list", // 请求的地址
    "fnServerData": retrieveData, // 获取数据的处理函数
    "bPaginate": true,  // 是否分页。
    "bFilter": true, // 是否有搜索框
    "sPaginationType": "full_numbers", //分页样式
    "pageLength": 40, // 页面默认显示记录数
    "bBootstrap" : true,

    // 列名称定义
/*    "aoColumns" : [ {
      "mDataProp" : "id"
    }, {
      "mDataProp" : "username"
    }, {
      "mDataProp" : "position"
    },{
      "mDataProp" : "salary"
    }, {
      "mDataProp" : "start_date"
    } ],*/

    "lengthMenu": [
      [10, 20, 50],
      [10, 20, 50] // 更改每页显示记录数
    ],

    "language": opts,

    "buttons" :[
      /*{
        extend: 'excelHtml5',
        text: '<i class="icon-login" style="font-weight: bold"></i>导出',
        className: 'btn-sm excelExport',
        exportOptions: {
          'columns': '2,3,4,5,6,7,8,9,10,11,12',//导出的列，默认全部
        }
      }*/
    ],

    // columnDefs：与columns非常相似，该数组可以针对特定的列，多列或者所有列定义。数组可以任意长度。通过targets参数设置一个列或者多列，该属性定义可以如下：
    // 0或正整数 - 从左边的列索引计数 ，负整数 - 列索引从右边计数 ，一个字符串 - 类名称将被匹配上的TH为列 ；字符串“_all” - 所有的列（即指定一个默认值）
    "columnDefs": [
      {
        searchable: false, //是否在列上应用查询过滤
        targets: [0]
      }, {
        sortable: false,  //是否在某一列上关闭排序
        targets: [0]
      }, {
        sDefaultContent: '', //默认值
        aTargets: [ '_all' ]
      }, {
        orderable: false,
        aTargets: [0]
      }, {
        aTargets: [0], // 第一列checkbox
        sClass : 'text-center',
        "render": function ( data, type, full, meta ) {
          return '<input type="checkbox" class="group-checkable" data-set="#favourable .checkboxes"/>';
        }
      },{
        targets: [-1], // 最后一列
        sClass : 'text-center',
        "render": function ( data, type, full, meta ) {
          return "<div class='btn-group'>" +
              "<button id='editRow' class='btn btn-primary btn-sm' type='button'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span></button>" +
              "<button id='delRow' class='btn btn-primary btn-sm' type='button'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span></button>" +
              "</div>"
        }
      },
    ],

    // 排序
    "order": [
      [1, "asc"]
    ],

    "columns" : [
      {
        // title :'<input type="checkbox" class="group-checkable" data-set="#favourable .checkboxes" id="checkAll"/>',
        // class :'table-checkbox',
        width : '2%',
      },
      {
        // title:'序号',
        width: '2%',
        data: function parseData(data, type, row, meta) { // 序号
          var startIndex = meta.settings._iDisplayStart;
          return startIndex + meta.row + 1;
        }
      },
      {
        // title:'编&nbsp;&nbsp;号',
        name : 'ACCT_ID',
        width : '3%',
        data : function (row) {
          return row.id;
        }
      },
      {
        // title:'姓&nbsp;&nbsp;名',
        name : 'USERNAME',
        width : '10%' ,
        data : function parseData(row) {
          return row.username;
        }
      },
      {
        // title:'位&nbsp;&nbsp;置',
        name : 'POSITION',
        width : '10%' ,
        data : function parseData(row) {
          return row.position;
        }
      },
      {
        // title:'工&nbsp;&nbsp资',
        name : 'SALARY',
        width : '8%' ,
        data : function parseData(row) {
          return row.salary;
        }
      },
      {
        // title: '起始时间',
        name : 'START_DATE',
        width : '12%' ,
        data : function parseData(row) {
          return AIot.formatDate(row.startDate)
        }
      },{
        // title: '操&nbsp;&nbsp;作',
        width: '5%',
      },
    ],

  };

  oTable = table.dataTable(datatableConfig);

  // 3个参数的名字可以随便命名,但必须是3个参数,少一个都不行
  function retrieveData(sSource, data, fnCallback) {
    $.ajax( {
      url: sSource,
      type: "get",
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      async : true,
      data: {
        data : JSON.stringify(data),  // 系列化对象,把对象的类型转换为字符串类型
      },
      success: function(result) {
        console.log(result);
        //服务器端返回的对象的returnObject部分是要求的格式,把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
        fnCallback(result);
      },
      error : function(err) {
        if (err.responseJSON.status == '404'){
          console.log(err);
          alert(err.responseJSON.message);
        }  else {
          alert('服务器错误');
        }
      }
    });
  }


  
  function Emp(name, position, salary, start_date, office, extn) {
    this.name = name;
    this.position = position;
    this.salary = salary;
    this.start_date = start_date;
    this._office = office;
    this.extn = extn;
    
    this.office = function () {
      return this._office;
    }
  };

  var table1 = $('#table1').dataTable({
    // 表格里使用垂直滚动条
    scrollY : 400,

    // 这样初始化，排序将会打开
    // 搜索功能仍然是关闭
    ordering: true,

    //是否开启服务器模式
    serverSide: false,
    info : true,
    paging: true,
    processing:false,
    searching: true,
    // 国际化
    language : {
      "sProcessing": "处理中...",
      "sLengthMenu": "显示 _MENU_ 项结果",
      "sZeroRecords": "没有匹配结果",
      "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
      "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
      "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
      "sInfoPostFix": "",
      "sSearch": "搜索:",
      "sUrl": "",
      "sEmptyTable": "表中数据为空",
      "sLoadingRecords": "载入中...",
      "sInfoThousands": ",",
      "oPaginate": {
        "sFirst": "首页",
        "sPrevious": "上页",
        "sNext": "下页",
        "sLast": "末页"
      },
      "oAria": {
        "sSortAscending": ": 以升序排列此列",
        "sSortDescending": ": 以降序排列此列"
      },
    },

    buttons: [{
      extend: 'excelHtml5',
      text: '<i class="icon-login" style="font-weight: bold"></i>导出',
      className: 'btn-sm excelExport'
    }],

    data : [
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421"),
      new Emp('Tiger Nixon',"System Architect","$3,120", "2011/04/25","Edinburgh","5421")
    ],

    //data 这里是固定不变的，name, position, salary, start_date, office, extn 为你数据里对应的属性
    columns : [
      {
        width : '10%' ,
        name : "name",
        data: 'name'
      },
      {
        width : '10%' ,
        name : 'position' ,
        data: 'position'
      },
      {
        width : '10%' ,
        name : 'salary' ,
        data: 'salary'
      },
      {
        width : '10%' ,
        name : 'start_date' ,
        data: 'start_date'
      },
      {
        width : '10%' ,
        name : 'office' ,
        data: 'office()'
      },
      {
        width : '10%' ,
        name : 'extn' ,
        data: 'extn'
      }
    ]
  });

  // 给行绑定选中事件
  // on(event,childSelector,data,function)规定只能添加到指定的子元素上的事件处理程序（且不是选择器本身
  $("#table tbody").on("click","tr",function () {
    if ($(this).hasClass("selected")){
      $(this).removeClass("selected");
    }else {
      table.$("tr.selected").removeClass("selected");
      $(this).addClass("selected");
    }
  });

  //给按钮绑定点击事件
  $("#table_button").click(function () {
    var column1 = table.row('.selected').data().column1;
    var column2 = table.row('.selected').data().column2;
    alert("第一列内容："+column1 + "；第二列内容： " + column2);
  });





} );