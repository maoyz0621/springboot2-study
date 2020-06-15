<!--第三步：初始化Datatables-->
$(document).ready(function () {

  var oTable;
  var rowData;
  var $table = $('#table');
  var $createEmpManagement = $('#createEmpManagement');
  var $createSubmitBtn = $('#createSubmitBtn');
  var $editEmpManagement = $('#editEmpManagement');
  var $editSubmitBtn = $('#editSubmitBtn');
  var $delEmpManagement = $('#delEmpManagement');
  var $delSubmitBtn = $('#delSubmitBtn');

  var opts = $.extend(true, {}, window.dataTablesSettings, {});
  // 表单验证信息
  var formValidate = $.extend(true, {}, window.validateOpts, {
    debug : true, //调试模式，即使验证成功也不会跳转到目标页面
    rules: {       // 规则
      username: {
        required: true,
        minlength: 2,
        maxlength: 5,
      },
      position: "required",
      salary: {
        required: true,
      },
      startDate: {
        required: true,
        date: true
      },
    },
    messages: {      // 说明信息
      username: {
        required: "请输入用户名",
        minlength: "用户名至少由两个字母组成",
        maxlength: "用户名不能超五两个字母组成"
      },
      position: "请输入一个正确的地址",
      salary: "请输入您的工资",
      startDate: {
        required: "请输入时间",
        date: "请按照格式：2018-11-12"
      }
    }
  });

  var $addForm = $('#addForm');

  // todo 封装请求参数
  var sendData = function (data) {
    var param = {};
    param.draw = data.draw;
    param.pageSize = data.length; // 页面显示记录条数，在页面显示每页显示多少项的时候
    param.startIndex = data.start;  // 开始的记录序号
    param.pageIndex = (data.start / data.length) + 1; // 当前页码
    // 自定义的参数
    param.type = $("select[name='type']").val();  // 获取要筛选的类别
    param.name = $("input[name='name']").val(); // 获取要筛选的名字
    // 获取排序的参数
    console.log(data.order);
    param.order = data.order;
    param.search = data.search;
    return param;
  };

  // todo 封装返回数据重要
  var returnData = function (data, result){
    var returnData = {};
    // 这里直接自行返回了draw计数器,应该由后台返回
    returnData.draw = data.draw;
    // 返回数据全部记录
    returnData.recordsTotal = result.recordsTotal;
    // 后台不实现过滤功能，每次查询均视作全部结果
    returnData.recordsFiltered = result.recordsFiltered;
    // 返回的数据列表
    returnData.data = result.data;
    // 错误提示信息
    returnData.error = result.error;
    return returnData;
  };

  oTable = $table.dataTable({
    // todo
    dom: '<"row"<"col-sm-8 hidden-xs"<"datatable-search">B><"col-sm-4"<"datatable-menu">>>'
    + 'rt<"row"<"col-sm-6 hidden-xs"<"col-sm-4 datatable_length"l><"col-sm-8 "i>><"col-sm-6 col-xs-12"p>>',
    orderMulti: true,  //启用多列排序
    ordering: true,  //使用排序
    processing: true, // 加载
    autoWidth: true, // 自动宽度
    serverSide: true, // 这个用来指明是通过服务端来取数据
    paginate: true,  // 是否分页
    searching: false,  //禁用原生搜索
    pagingType: "full_numbers", //分页样式
    // pageLength: 40, // 页面默认显示记录数
    renderer: "bootstrap",

    "lengthMenu": [
      [10, 20, 50, 100],
      [10, 20, 50, 100] // 更改每页显示记录数
    ],

    "language": opts,

    "buttons": [],

    "columnDefs": [
      {searchable: false, targets: [0]}, //是否在列上应用查询过滤
      {sortable: false, targets: [0]},  //是否在某一列上关闭排序
      {sDefaultContent: '', aTargets: ['_all']},  //默认值
      {orderable: false, aTargets: [0, -1]},
      {
        aTargets: [0], // 第一列checkbox
        sClass: 'text-center',
        "render": function (data, type, full, meta) {
          return '<input type="checkbox" class="group-checkable" data-set="#favourable .checkboxes"/>';
        }
      }, {
        targets: [-1], // 最后一列
        sClass: 'text-center',
        "render": function (data, type, full, meta) {
          return "<div>" +
              "<button id='editRow' class='btn btn-primary btn-sm' type='button' data-toggle='modal' data-target='#editEmpManagement'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span></button>" +
              "<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
              "<button id='delRow' class='btn btn-warning btn-sm' type='button' data-toggle='modal'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span></button>" +
              "</div>"
        }
      },
    ],

    // 排序
    "order": [
      [1, "asc"]
    ],

    "columns": [
      {},
      {
        data: function parseData(data, type, row, meta) { // 序号
          var startIndex = meta.settings._iDisplayStart;
          return startIndex + meta.row + 1;
        }
      },
      {
        name: 'acctId',
        data: function (row) {
          return row.id;
        }
      },
      {
        name: 'username',
        data: function parseData(row) {
          return row.username;
        }
      },
      {
        name: 'position',
        data: function parseData(row) {
          return row.position;
        }
      },
      {
        name: 'salary',
        data: function parseData(row) {
          return row.salary;
        }
      },
      {
        name: 'start_date',
        data: function parseData(row) {
          return AIot.formatDate(row.startDate)
        }
      },
      {}
    ],
    ajax: function (data, callback) {
      $.ajax({
        url: "/emp/list1?timestamp=" + new Date().getTime(),
        type: "get",
        dataType: "json",     // 预期服务器返回的数据类型
        contentType: "application/json; charset=utf-8",    // 默认值: "application/x-www-form-urlencoded",发送信息至服务器时内容编码类型。
        async: true,
        data: {
          data: JSON.stringify(data),  // 系列化对象,把对象的类型转换为字符串类型
        },
        success: function (result) {
          //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
          //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
          callback(result);
        },
        error: function (err) {
          if (err.responseJSON.status == '404') {
            console.log(err);
            alert(err.responseJSON.message);
          } else {
            alert('服务器错误');
          }
        }
      });
    },

    drawCallback : function () {
      /**
       * 编辑按钮
       */
      // edit_1
      $('#editRow').off('click').on('click', function () {
        rowData = oTable.row($(this).parent().parent()).data();    // 当前行列数据
        console.log(rowData);
        $editEmpManagement.find('input[name*=acctId]').val(rowData.id);
        $editEmpManagement.find('input[name*=username]').val(rowData.username);
        $editEmpManagement.find('input[name*=position]').val(rowData.position);
        $editEmpManagement.find('input[name*=salary]').val(rowData.salary);
        $editEmpManagement.find('input[name*=startDate]').val(AIot.formatDate(rowData.startDate));
      });

      /**
       * 删除
       */
      // del_1 按钮触发模态框
      $('#delRow').off('click').on('click', function () {
        rowData = oTable.row($(this).parent().parent()).data();    // 当前行列数据
        console.log(rowData);
        $delEmpManagement.find('input[name*=acctId]').val(rowData.id);     // hidden数据
        $delEmpManagement.modal();   // 开启模态框
      });

    }
  }).api(true);  // 链式调用

  function Emp(username, position, salary, startDate) {
    this.username = username;
    this.position = position;
    this.salary = salary;
    this.startDate = startDate;
  };

  var username = $("#username").val();
  var position = $("#position").val();
  var salary = $("#salary").val();
  var startDate = $("#startdate").val();
// 表单验证
/*  $addForm.validate({
    onfocusout : function (element) {
          $(element).valid();
        },
    formValidate
  });*/

  /**
   *点击新建按钮
  */
  $createEmpManagement
    .on('shown.bs.modal', function (e) { // 开启模态框
      $('#addForm input').first().focus(); // 聚焦
      $('#addForm input').bind('blur',function () {
        $(this).validate(formValidate);
      });
      $addForm.validate(formValidate);
      $createSubmitBtn.off('click').on('click', function submitEventFunc(e) {
        e.preventDefault();   // 阻止元素发生默认的行为
        if ($addForm.valid()) {   // 表单验证通过
          $.ajax({
            url: "/emp/add?timestamp=" + new Date().getTime(),
            type: "post",
            async: true,
            // todo 增、删、改不要加dataType
            // data: { data : JSON.stringify(obj) },   // 以json格式传递
            // data : new Emp(username, position,salary,startDate),  // 以对象形式传递
            data: $addForm.serialize(),  // 表单序列化形式传递
            success: function (data) {
              $createEmpManagement.modal('hide');  // 提交之后关闭模态框
              AIot.clearForm($addForm);  // 清除表格数据
              console.log(data);
              oTable.draw(false);  // 重新绘制表格,排序和搜索将会重新计算，行在新的位置重新绘制。分页不会被重置，任保持当前页。
            },
            error: function (err) {
              alert(err)
            }
          })
        }
      })
    })
    .on('hidden.bs.modal', function (e) { //关闭模态框
      AIot.clearForm($addForm);  // 清除表单内容
      $addForm.validate().resetForm();  // 重置表单
    });


  // del_2 删除按钮确认键
  $delSubmitBtn.off('click').on('click',function () {
    var data = {
      PARAM : [],
    };

    data.PARAM.push({
      id : $delEmpManagement.find('input[name*=acctId]').val(),
    });

    $.ajax({
      url: "/emp/del",
      type: "POST",
      // dataType: "json",    // todo 增、删、改不要加dataType
      data: {
        data: JSON.stringify(data)
      },
      success: function (data) {
        $delEmpManagement.modal('hide');    // 提交之后关闭模态框
        console.log(data);
        oTable.draw(false);  // 重新绘制表格,排序和搜索将会重新计算，行在新的位置重新绘制。分页不会被重置，任保持当前页。
      },
      error: function (err) {
        console.log(err);
        alert_msg(err);
        $delEmpManagement.modal('hide');
      }
    })
  });

  // edit_2
  $editSubmitBtn.off('click').on('click', function () {
    var data = {
      PARAM : [],
    };

    data.PARAM.push({
      id : $editEmpManagement.find('input[name*=acctId]').val(),
      username : $editEmpManagement.find('input[name*=username]').val(),
      position : $editEmpManagement.find('input[name*=position]').val(),
      salary : $editEmpManagement.find('input[name*=salary]').val(),
      startDate : $editEmpManagement.find('input[name*=startDate]').val(),
    });

    $.ajax({
      url: "/emp/edit",
      type: "POST",
      data: {
        data: JSON.stringify(data)
      },
      success: function (data) {
        $editEmpManagement.modal('hide');    // 提交之后关闭模态框
        console.log(data);
        oTable.draw(false);  // 重新绘制表格,排序和搜索将会重新计算，行在新的位置重新绘制。分页不会被重置，任保持当前页。
      },
      error: function (err) {
        console.log(err);
        alert_msg(err);
        $editEmpManagement.modal('hide');
      }
    })
  });

  var setStatus = function (id) {
    var value="3";
    var ele = $(id).find('option');
    ele.each(function(){
      var temp_value = $(this).val();
      if(temp_value === value){
        $(this).attr("selected","selected");
      }
    });
  };
  setStatus('#select1');

  $('#select1').change(function () {
    alert(this.children('option:').val());
    alert(this.find('option').text);
  });

})