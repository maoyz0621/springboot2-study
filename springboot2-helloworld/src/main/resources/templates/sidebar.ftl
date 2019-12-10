<div class="page-sidebar-wrapper">
  <div class="page-sidebar navbar-collapse collapse" aria-expanded="false" style="height: 0px;">


    <ul class="page-sidebar-menu" data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
      <li class="start">
        <a href="${servletContextPath}/page/sys/index.html">
          <i class="icon-home"></i>
          <span class="title">首页</span>
        </a>
      </li>
    <#if menuList??>
        <#list menuList as menulist>
            <#if menulist.PARENT_MENU_ID == "0">
              <li class="tooltips" data-container="body" data-placement="right" data-html="true">
                <a href="javascript:void(0);">
                  <i class="${menulist.MODE_ATTR}"></i>
                  <span class="title"><@spring.messageText "${menulist.MENU_TITLE}" "${menulist.MENU_EXPLAIN}"/></span>
                  <span class="arrow"></span>
                </a>
                <ul class="sub-menu" style="display: none;">
                    <#list menuList as menulist1>
                        <#if menulist.MENU_ID == menulist1.PARENT_MENU_ID>
                          <li class="tooltips" data-container="body" data-placement="right" data-html="true">
                            <a href="${servletContextPath}${menulist1.MODE_PATH}" data-pjax='#content'>
                                <@spring.messageText "${menulist1.MENU_TITLE}" "${menulist1.MENU_EXPLAIN}"/>
                            </a>
                          </li>
                        </#if>
                    </#list>
                </ul>
              </li>
            </#if>
        </#list>
    </#if>
    </ul>
  </div>
</div>
<script type="text/javascript">
  $(function jQueryDocReady() {
    $('.sidebar-toggler').show();
    var url = $('#servletContextPath').val() + '/service/um/isMirror';
    $.ajax({
      url: url,
      type: 'get',
      dataType: 'json',
      success: function successCbFunc(result) {
        if (result === 1) {
          $('#isMirror').removeClass("hidden");
          $('li[name="isMirror"]').addClass("hidden");
          $("a[href='/vehicle/page/um/authManage/agentSet.html']").parent().addClass('hidden');
          return false;
        } else {
          $('#isMirror').addClass("hidden");
          $('li[name="isMirror"]').removeClass("hidden");
          $("a[href='/vehicle/page/um/authManage/agentSet.html']").parent().removeClass('hidden');
        }
      }
    });
    var height = document.documentElement.clientHeight;
    $('.sidebar-toggler').attr("style", "top:" + height * 0.4 + "px;");
    $(window).resize(function () {
      var height = document.documentElement.clientHeight;
      $('.sidebar-toggler').attr("style", "top:" + height * 0.4 + "px;");
    });
    $('.icon-magnifier').on('click',function () {
      var word = $("input[placeholder='搜索']").val();
      var out = false;
      $('.page-sidebar-menu>li').each(function () {
        var li = $(this);
        $(this).find("a").each(function () {
          var a = $(this);
          var text = a.text();
          if (text.match(word) != null){
            if (a.siblings().length > 0){
              //移除所有效果
              clearMenu();
              a.parent().addClass("active open");
              a.next().css('display','block');
              return out;
            }else {
              //移除所有效果
              clearMenu();
              li.addClass("active open");//选中一级菜单
              li.find("ul").css('display','block');//展开一级菜单
              a.parent().addClass("active");//选中二级菜单
              return out;
            }
          }
        });

      });
      function clearMenu() {
        $('.page-sidebar-menu>li').removeClass("active");
        $('.page-sidebar-menu>li').removeClass("open");
        $('.page-sidebar-menu>li').find("ul").css("display","none");
        $('.page-sidebar-menu>li').find("li").removeClass("active");
      }
    });

  })
</script>