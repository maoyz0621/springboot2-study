$(function () {

  // 把dataTable公共的设置项都放在这里，就不需要每个页面都设置一遍了，放在jQuery对象上是为了避免污染全局变量
  window.dataTablesSettings = {
    aria: {
      sortAscending: ': 升序排列',
      sortDescending: ': 降序排列'
    },
    paginate: {
      first: '首页',
      last: '末页',
      next: '>',
      previous: '<'
    },
    info: '第 _START_ 到 _END_ 条记录，共 _TOTAL_ 条',
    zeroRecords: '没有相关记录',
    lengthMenu: "每页显示 _MENU_记录",
    emptyTable: '没有相关记录',
    infoEmpty: '第 0 到 0 条记录，共 0 条',
    search: "搜索",
    infoFiltered: "(从 _MAX_条记录中查找)",
  };

  window.validateOpts = {
    ignore: ":hidden",
    doNotHideMessage: true, // this option enables to show the error/success messages on tab switch.
    errorElement: 'span', // default input error message container
    errorClass: 'help-block help-block-error', // default input error message class
    focusInvalid: false, // do not focus the last invalid input
    highlight: function highlightCbFunc(element) { // hightlight error inputs
      $(element)
          .closest('.form-group')
          .removeClass('has-success')
          .addClass('has-error');
      // set error class to the control group
    },

    unhighlight: function unhighlightCbFunc(element) { // revert the change done by hightlight
      $(element)
          .closest('.form-group')
          .removeClass('has-error'); // set error class to the control group
    },

    success: function successCbFunc(label) {
      if (label.attr('for') === 'gender' || label.attr('for') === 'payment[]') {
        // for checkboxes and radio buttons, no need to show OK icon
        label
            .closest('.form-group').removeClass('has-error').addClass('has-success');
        label.remove(); // remove error label here
      } else { // display success icon for other inputs
        label
            .addClass('valid') // mark the current input as valid and display OK icon
            .closest('.form-group').removeClass('has-error').addClass('has-success');
        // set success class to the control group
      }
    },

    errorPlacement: function (error, element) {
      if (element.hasClass('next-line')) {
        element.closest('div').after(error);
      } else {
        error.appendTo(element.parent());
      }
    }
  };

  window.alertMsg = function alertMsg(data) {
    if (data.resultCode === '0000' && data.resultDesc) {
      toastr.success(data.resultDesc);
    } else if (data.resultException) {
      toastr.error(data.resultException);
    } else {
      data.resultException = "系统内部异常";
      toastr.error(data.resultException);
    }
  };

  window.alert_msg = function alertMsg(err) {
   if (err.status === 200){
     alert("响应成功，处理结果错误")
   } else if(err.status === 404 ){
      alert("404")
   } else if (err.status === 500){
      alert("服务器出错")
   } else {
     alert("未知错误")
   }
  };

});