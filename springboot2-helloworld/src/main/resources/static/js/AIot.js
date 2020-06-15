var AIot = function () {
  // IE mode
  var isRTL = false;
  var isIE8 = false;
  var isIE9 = false;
  var isIE10 = false;

  // 浏览器窗口大小变更时, 需要自行的处理函数
  var resizeHandlers = [];

  // initializes main settings
  var handleInit = function () {

    if ($('body').css('direction') === 'rtl') {
      isRTL = true;
    }

    isIE8 = !!navigator.userAgent.match(/MSIE 8.0/);
    isIE9 = !!navigator.userAgent.match(/MSIE 9.0/);
    isIE10 = !!navigator.userAgent.match(/MSIE 10.0/);

    if (isIE10) {
      $('html').addClass('ie10'); // detect IE10 version
    }

    if (isIE10 || isIE9 || isIE8) {
      $('html').addClass('ie'); // detect IE10 version
    }
  };

  var handleMenuSelected = function () {
    var $this;
    var $menu = $('.page-sidebar-menu');
    var url = location.pathname.toLowerCase();
    $menu.find("li > a").each(function () {
      var path = $(this).attr("href").toLowerCase();
      // url match condition
      if (path.length > 1 && url.substr(1, path.length - 1) == path.substr(1)) {
        $this = $(this);
        return;
      }
    });

    if (!$this || $this.size() === 0 || $this.attr('href').toLowerCase() === 'javascript:;'
      || $this.attr('href').toLowerCase() === '#') {
      return;
    }

    // disable active states
    $menu.find('li.active').removeClass('active');
    $menu.find('li > a > .selected').remove();

    if ($menu.hasClass('page-sidebar-menu-hover-submenu') === false) {
      $menu.find('li.open').each(function () {
        if ($(this).children('.sub-menu').size() === 0) {
          $(this).removeClass('open');
          $(this).find('> a > .arrow.open').removeClass('open');
        }
      });
    } else {
      $menu.find('li.open').removeClass('open');
    }

    $this.parents('li').each(function () {
      $(this).addClass('active');
      $(this).find('> a > span.arrow').addClass('open');

      if ($(this).parent('ul.page-sidebar-menu').size() === 1) {
        $(this).find('> a').append('<span class="selected"></span>');
      }

      if ($(this).children('ul.sub-menu').size() === 1) {
        $(this).addClass('open');
      }
    });

    $this = $menu.find('> li.active.open > a');

    var $sub = $this.next();
    var hasSubMenu = $sub.hasClass('sub-menu');

    if (AIot.getViewPort().width >= AIot.getResponsiveBreakpoint('md') && $this.parents('.page-sidebar-menu-hover-submenu').size() === 1) { // exit of hover sidebar menu
      return;
    }

    if (hasSubMenu === false) {
      if (AIot.getViewPort().width < AIot.getResponsiveBreakpoint('md') && $('.page-sidebar').hasClass("in")) { // close the menu on mobile view while laoding a page
        $('.page-header .responsive-toggler').click();
      }
      return;
    }

    if ($sub.hasClass('sub-menu always-open')) {
      return;
    }


    var autoScroll = $menu.data("auto-scroll");
    var slideSpeed = parseInt($menu.data("slide-speed"));
    var slideOffeset = -200;

    if (hasSubMenu) {
      $sub.slideDown(slideSpeed, function () {
        if (autoScroll === true && $('body').hasClass('page-sidebar-closed') === false) {
          AIot.scrollTo($this, slideOffeset);
        }
        handleSidebarAndContentHeight();
      });
    }
  };

  // 调用 通过Iot.addResponsiveHandler() 添加的函数集合(functions set).
  var _runResizeHandlers = function () {
    // reinitialize other subscribed elements
    for (var i = 0; i < resizeHandlers.length; i++) {
      var each = resizeHandlers[i];
      each.call();
    }
  };

  // 在浏览器窗口(window)大小改变的时候, 处理布局初始化
  var handleOnResize = function () {
    var resize;
    if (isIE8) {
      var currheight;
      $(window).resize(function () {
        if (currheight == document.documentElement.clientHeight) {
          return; //quite event since only body resized not window.
        }
        if (resize) {
          clearTimeout(resize);
        }
        resize = setTimeout(function () {
          _runResizeHandlers();
        }, 50); // wait 50ms until window resize finishes.
        currheight = document.documentElement.clientHeight; // store last body client height
      });
    } else {
      $(window).resize(function () {
        if (resize) {
          clearTimeout(resize);
        }
        resize = setTimeout(function () {
          _runResizeHandlers();
        }, 50); // wait 50ms until window resize finishes.
      });
    }
  };

  // jQuery Uniform 插件, 美化 checkbox, redio
  var handleUniform = function () {
    if (!$().uniform) {
      return;
    }
    var test = $("input[type=checkbox]:not(.toggle, .make-switch, .icheck), input[type=radio]:not(.toggle, .star, .make-switch, .icheck)");
    if (test.size() > 0) {
      test.each(function () {
        if ($(this).parents(".checker").size() === 0) {
          $(this).show();
          $(this).uniform();
        }
      });
    }
  };

  // jQuery iCheck 插件, 美化 checkboxed, radio
  var handleiCheck = function () {
    if (!$().iCheck) {
      return;
    }

    $('.icheck').each(function () {
      var checkboxClass = $(this).attr('data-checkbox') ? $(this).attr('data-checkbox') : 'icheckbox_minimal-grey';
      var radioClass = $(this).attr('data-radio') ? $(this).attr('data-radio') : 'iradio_minimal-grey';

      if (checkboxClass.indexOf('_line') > -1 || radioClass.indexOf('_line') > -1) {
        $(this).iCheck({
          checkboxClass: checkboxClass,
          radioClass: radioClass,
          insert: '<div class="icheck_line-icon"></div>' + $(this).attr("data-label")
        });
      } else {
        $(this).iCheck({
          checkboxClass: checkboxClass,
          radioClass: radioClass
        });
      }
    });
  };

  // Bootstrap Switches, 几乎用不到(quick bar), 暂留
  var handleBootstrapSwitch = function () {
    if (!$().bootstrapSwitch) {
      return;
    }
    $('.make-switch').bootstrapSwitch();
  };

  // jQuery SlimScroll 插件, 美化内容滚动
  var handleScrollers = function () {
    AIot.initSlimScroll('.scroller');
  };

  // jQuery Fancybox 插件, 美化图片预览, 暂留
  var handleFancybox = function () {
    if (!jQuery.fancybox) {
      return;
    }
    if ($(".fancybox-button").size() > 0) {
      $(".fancybox-button").fancybox({
        groupAttr: 'data-rel',
        prevEffect: 'none',
        nextEffect: 'none',
        closeBtn: true,
        helpers: {
          title: {
            type: 'inside'
          }
        }
      });
    }
  };

  // select2 dropdown, 初始化 "select2me" 的为select2样式
  var handleSelect2 = function () {
    if ($().select2) {
      $('.select2me').select2({
        allowClear: true,
        language: "zh-CN",
        placeholder: '请选择',
        allowClear: true,
        width: '100%'
      });
    }
  };

  // protlet 工具栏及其事件, 暂留
  var handlePortletTools = function () {
    // portlet 移除
    $('body').on('click', '.portlet > .portlet-title > .tools > a.remove', function (e) {
      e.preventDefault();
      var portlet = $(this).closest(".portlet");

      if ($('body').hasClass('page-portlet-fullscreen')) {
        $('body').removeClass('page-portlet-fullscreen');
      }

      portlet.find('.portlet-title .fullscreen').tooltip('destroy');
      portlet.find('.portlet-title > .tools > .reload').tooltip('destroy');
      portlet.find('.portlet-title > .tools > .remove').tooltip('destroy');
      portlet.find('.portlet-title > .tools > .config').tooltip('destroy');
      portlet.find('.portlet-title > .tools > .collapse, .portlet > .portlet-title > .tools > .expand').tooltip('destroy');

      portlet.remove();
    });

    // portlet 全屏
    $('body').on('click', '.portlet > .portlet-title .fullscreen', function (e) {
      e.preventDefault();
      var portlet = $(this).closest(".portlet");
      if (portlet.hasClass('portlet-fullscreen')) {
        $(this).removeClass('on');
        portlet.removeClass('portlet-fullscreen');
        $('body').removeClass('page-portlet-fullscreen');
        portlet.children('.portlet-body').css('height', 'auto');
      } else {
        var height = AIot.getViewPort().height -
          portlet.children('.portlet-title').outerHeight() -
          parseInt(portlet.children('.portlet-body').css('padding-top')) -
          parseInt(portlet.children('.portlet-body').css('padding-bottom'));

        $(this).addClass('on');
        portlet.addClass('portlet-fullscreen');
        $('body').addClass('page-portlet-fullscreen');
        portlet.children('.portlet-body').css('height', height);
      }
    });

    // protlet reload 事件处理
    $('body').on('click', '.portlet > .portlet-title > .tools > a.reload', function (e) {
      e.preventDefault();
      var el = $(this).closest(".portlet").children(".portlet-body");
      var url = $(this).attr("data-url");
      var error = $(this).attr("data-error-display");
      if (url) {
        AIot.startPageLoading();
        $.ajax({
          type: "GET",
          cache: false,
          url: url,
          dataType: "html",
          success: function (res) {
            AIot.stopPageLoading();
            el.html(res);
          },
          error: function (xhr, ajaxOptions, thrownError) {
            AIot.stopPageLoading();
            var msg = '重新加载内容出错, 请检查链接并重试!';
            if (error == "toastr" && toastr) { // bootstrap toastr通知
              toastr.error(msg);
            } else { // 删除notific通知
              alert(msg);
            }
          }
        });
      } else {
        // for demo purpose
        AIot.startPageLoading();
        window.setTimeout(function () {
          AIot.stopPageLoading();
        }, 1000);
      }
    });

    // 初始化页面上通过 ajax 获取数据
    $('.portlet .portlet-title a.reload[data-load="true"]').click();

    $('body').on('click', '.portlet > .portlet-title > .tools > .collapse, .portlet .portlet-title > .tools > .expand', function (e) {
      e.preventDefault();
      var el = $(this).closest(".portlet").children(".portlet-body");
      if ($(this).hasClass("collapse")) {
        $(this).removeClass("collapse").addClass("expand");
        el.slideUp(200);
      } else {
        $(this).removeClass("expand").addClass("collapse");
        el.slideDown(200);
      }
    });
  };

  // form 表单中的提示alert 关闭时事件
  var handleAlerts = function () {
    $('body').on('click', '[data-close="alert"]', function (e) {
      $(this).parent('.alert').hide();
      $(this).closest('.note').hide();
      e.preventDefault();
    });

    $('body').on('click', '[data-close="note"]', function (e) {
      $(this).closest('.note').hide();
      e.preventDefault();
    });

    $('body').on('click', '[data-remove="note"]', function (e) {
      $(this).closest('.note').remove();
      e.preventDefault();
    });
  };

  // bootstrap dropdown 样式的下拉菜单
  var handleDropdowns = function () {
    // 组织事件冒泡
    $('body').on('click', '.dropdown-menu.hold-on-click', function (e) {
      e.stopPropagation();
    });
  };

  // bootstrap tabs 美化tab页
  var handleTabs = function () {
    //activate tab if tab id provided in the URL
    if (location.hash) {
      var tabid = location.hash.substr(1);
      $('a[href="#' + tabid + '"]').parents('.tab-pane:hidden').each(function () {
        var tabid = $(this).attr("id");
        $('a[href="#' + tabid + '"]').click();
      });
      $('a[href="#' + tabid + '"]').click();
    }
  };

  // bootstrap tooltips
  var handleTooltips = function () {
    // global tooltips
    $('.tooltips').tooltip();

    // portlet tooltips
    $('.portlet > .portlet-title .fullscreen').tooltip({
      container: 'body',
      title: '全屏'
    });
    $('.portlet > .portlet-title > .tools > .reload').tooltip({
      container: 'body',
      title: '加载'
    });
    $('.portlet > .portlet-title > .tools > .remove').tooltip({
      container: 'body',
      title: '关闭'
    });
    $('.portlet > .portlet-title > .tools > .config').tooltip({
      container: 'body',
      title: '设置'
    });
    $('.portlet > .portlet-title > .tools > .collapse, .portlet > .portlet-title > .tools > .expand').tooltip({
      container: 'body',
      title: '收起/展开'
    });
  };

  // bootstrap popovers

  // last popep popover
  var lastPopedPopover;

  var handlePopovers = function () {
    $('.popovers').popover();

    // close last displayed popover
    $(document).on('click.bs.popover.data-api', function (e) {
      if (lastPopedPopover) {
        lastPopedPopover.popover('hide');
      }
    });
  };

  // bootstrap accordions
  var handleAccordions = function () {
    $('body').on('shown.bs.collapse', '.accordion.scrollable', function (e) {
      AIot.scrollTo($(e.target));
    });
  };

  // bootstrap modal 模态窗口
  var handleModals = function () {
    // fix stackable modal issue: when 2 or more modals opened, closing one of modal will remove .modal-open class.
    $('body').on('hide.bs.modal', function () {
      if ($('.modal:visible').size() > 1 && $('html').hasClass('modal-open') === false) {
        $('html').addClass('modal-open');
      } else if ($('.modal:visible').size() <= 1) {
        $('html').removeClass('modal-open');
      }
    });

    // fix page scrollbars issue
    $('body').on('show.bs.modal', '.modal', function () {
      if ($(this).hasClass("modal-scroll")) {
        $('body').addClass("modal-open-noscroll");
      }
    });

    // fix page scrollbars issue
    $('body').on('hide.bs.modal', '.modal', function () {
      $('body').removeClass("modal-open-noscroll");
    });

    // remove ajax content and remove cache on modal closed
    $('body').on('hidden.bs.modal', '.modal:not(.modal-cached)', function () {
      $(this).removeData('bs.modal');
    });
  };

  // bootstrap confirmations
  var handleBootstrapConfirmation = function () {
    if (!$().confirmation) {
      return;
    }
    $('[data-toggle=confirmation]').confirmation({
      container: 'body',
      btnOkClass: 'btn-xs btn-success',
      btnCancelClass: 'btn-xs btn-danger'
    });
  };

  // Fix input placeholder issue for IE8 and IE9
  var handleFixInputPlaceholderForIE = function () {
    //fix html5 placeholder attribute for ie7 & ie8
    if (isIE8 || isIE9) { // ie8 & ie9
      // this is html5 placeholder fix for inputs, inputs with placeholder-no-fix class will be skipped(e.g: we need this for password fields)
      $('input[placeholder]:not(.placeholder-no-fix), textarea[placeholder]:not(.placeholder-no-fix)').each(function () {
        var input = $(this);

        if (input.val() === '' && input.attr("placeholder") !== '') {
          input.addClass("placeholder").val(input.attr('placeholder'));
        }

        input.focus(function () {
          if (input.val() == input.attr('placeholder')) {
            input.val('');
          }
        });

        input.blur(function () {
          if (input.val() === '' || input.val() == input.attr('placeholder')) {
            input.val(input.attr('placeholder'));
          }
        });
      });
    }
  };

  // 原 layout.js
  // Set proper height for sidebar and content. The content and sidebar height must be synced always.
  var handleSidebarAndContentHeight = function () {
    var content = $('.page-content');
    var sidebar = $('.page-sidebar');
    var body = $('body');
    var height;
    var headerHeight = $('.page-header').outerHeight();
    var footerHeight = $('.page-footer').outerHeight();

    if (AIot.getViewPort().width < AIot.getResponsiveBreakpoint('md')) {
      height = AIot.getViewPort().height - headerHeight - footerHeight;
    } else {
      height = sidebar.height() + 20;
    }

    if ((height + headerHeight + footerHeight) <= AIot.getViewPort().height) {
      height = AIot.getViewPort().height - headerHeight - footerHeight;
    }
    content.attr('style', 'min-height:' + height + 'px');
  };

  // Handle sidebar menu links
  var handleSidebarMenuActiveLink = function (mode, el) {
    // var url = location.hash.toLowerCase();
    var url = location.pathname.toLowerCase();

    var menu = $('.page-sidebar-menu');

    if (mode === 'click' || mode === 'set') {
      el = $(el);
    } else if (mode === 'match') {
      menu.find("li > a").each(function () {
        var path = $(this).attr("href").toLowerCase();
        // url match condition
        if (path.length > 1 && url.substr(1, path.length - 1) == path.substr(1)) {
          el = $(this);
          return;
        }
      });
    }

    if (!el || el.size() == 0) {
      return;
    }

    if (el.attr('href').toLowerCase() === 'javascript:;' || el.attr('href').toLowerCase() === '#') {
      return;
    }

    var slideSpeed = parseInt(menu.data("slide-speed"));
    var keepExpand = menu.data("keep-expanded");

    // disable active states
    menu.find('li.active').removeClass('active');
    menu.find('li > a > .selected').remove();

    if (menu.hasClass('page-sidebar-menu-hover-submenu') === false) {
      menu.find('li.open').each(function () {
        if ($(this).children('.sub-menu').size() === 0) {
          $(this).removeClass('open');
          $(this).find('> a > .arrow.open').removeClass('open');
        }
      });
    } else {
      menu.find('li.open').removeClass('open');
    }

    el.parents('li').each(function () {
      $(this).addClass('active');
      $(this).find('> a > span.arrow').addClass('open');

      if ($(this).parent('ul.page-sidebar-menu').size() === 1) {
        $(this).find('> a').append('<span class="selected"></span>');
      }

      if ($(this).children('ul.sub-menu').size() === 1) {
        $(this).addClass('open');
      }
    });

    if (mode === 'click') {
      if (AIot.getViewPort().width < AIot.getResponsiveBreakpoint('md') && $('.page-sidebar').hasClass("in")) { // close the menu on mobile view while laoding a page
        $('.page-header .responsive-toggler').click();
      }
    }
  };

  // Handle sidebar menu
  var handleSidebarMenu = function () {
    // handle sidebar link click
    $('.page-sidebar').on('click', 'li > a', function (e) {
      handleSidebarMenuActiveLink('click', this);
      var hasSubMenu = $(this).next().hasClass('sub-menu');

      if (AIot.getViewPort().width >= AIot.getResponsiveBreakpoint('md') && $(this).parents('.page-sidebar-menu-hover-submenu').size() === 1) { // exit of hover sidebar menu
        return;
      }

      if (hasSubMenu === false) {
        if (AIot.getViewPort().width < AIot.getResponsiveBreakpoint('md') && $('.page-sidebar').hasClass("in")) { // close the menu on mobile view while laoding a page
          $('.page-header .responsive-toggler').click();
        }
        return;
      }

      if ($(this).next().hasClass('sub-menu always-open')) {
        return;
      }

      var parent = $(this).parent().parent();
      var the = $(this);
      var menu = $('.page-sidebar-menu');
      var sub = $(this).next();

      var autoScroll = menu.data("auto-scroll");
      var slideSpeed = parseInt(menu.data("slide-speed"));
      var keepExpand = menu.data("keep-expanded");

      if (keepExpand !== true) {
        parent.children('li.open').children('a').children('.arrow').removeClass('open');
        parent.children('li.open').children('.sub-menu:not(.always-open)').slideUp(slideSpeed);
        parent.children('li.open').removeClass('open');
      }

      var slideOffeset = -200;

      if (sub.is(":visible")) {
        $('.arrow', $(this)).removeClass("open");
        $(this).parent().removeClass("open");
        sub.slideUp(slideSpeed, function () {
          if (autoScroll === true && $('body').hasClass('page-sidebar-closed') === false) {
            if ($('body').hasClass('page-sidebar-fixed')) {
              menu.slimScroll({
                'scrollTo': (the.position()).top
              });
            } else {
              AIot.scrollTo(the, slideOffeset);
            }
          }
          handleSidebarAndContentHeight();
        });
      } else if (hasSubMenu) {
        $('.arrow', $(this)).addClass("open");
        $(this).parent().addClass("open");
        sub.slideDown(slideSpeed, function () {
          if (autoScroll === true && $('body').hasClass('page-sidebar-closed') === false) {
            if ($('body').hasClass('page-sidebar-fixed')) {
              menu.slimScroll({
                'scrollTo': (the.position()).top
              });
            } else {
              AIot.scrollTo(the, slideOffeset);
            }
          }
          handleSidebarAndContentHeight();
        });
      }

      e.preventDefault();
    });

    // handle scrolling to top on responsive menu toggler click when header is fixed for mobile view
    $(document).on('click', '.page-header-fixed-mobile .page-header .responsive-toggler', function () {
      AIot.scrollTop();
    });

    // handle the search bar close
    $('.page-sidebar').on('click', '.sidebar-search .remove', function (e) {
      e.preventDefault();
      $('.sidebar-search').removeClass("open");
    });

    // handle the search query submit on enter press
    $('.page-sidebar .sidebar-search').on('keypress', 'input.form-control', function (e) {
      if (e.which == 13) {
        $('.sidebar-search').submit();
        return false; //<---- Add this line
      }
    });

    // handle the search submit(for sidebar search and responsive mode of the header search)
    $('.sidebar-search .submit').on('click', function (e) {
      e.preventDefault();
      if ($('body').hasClass("page-sidebar-closed")) {
        if ($('.sidebar-search').hasClass('open') === false) {
          if ($('.page-sidebar-fixed').size() === 1) {
            $('.page-sidebar .sidebar-toggler').click(); //trigger sidebar toggle button
          }
          $('.sidebar-search').addClass("open");
        } else {
          $('.sidebar-search').submit();
        }
      } else {
        $('.sidebar-search').submit();
      }
    });

    // handle close on body click
    if ($('.sidebar-search').size() !== 0) {
      $('.sidebar-search .input-group').on('click', function (e) {
        e.stopPropagation();
      });

      $('body').on('click', function () {
        if ($('.sidebar-search').hasClass('open')) {
          $('.sidebar-search').removeClass("open");
        }
      });
    }
  };

  // Helper function to calculate sidebar height for fixed sidebar layout.
  var _calculateFixedSidebarViewportHeight = function () {
    var sidebarHeight = AIot.getViewPort().height - $('.page-header').outerHeight();
    if ($('body').hasClass("page-footer-fixed")) {
      sidebarHeight = sidebarHeight - $('.page-footer').outerHeight();
    }

    return sidebarHeight;
  };

  // Hanles sidebar toggler
  var handleSidebarToggler = function () {
    var body = $('body');
    var sidebarDiv = $('.sidebar-toggler');
    if ($.cookie && $.cookie('sidebar_closed') === '1'
      && AIot.getViewPort().width >= AIot.getResponsiveBreakpoint('md')) {
      $('body').addClass('page-sidebar-closed');
      $('.page-sidebar-menu').addClass('page-sidebar-menu-closed');
      sidebarDiv.addClass("sidebar-close");
      sidebarDiv.find('.left-toggler').addClass('hidden');
      sidebarDiv.find('.right-toggler').removeClass('hidden');
    }

    // handle sidebar show/hide
    $('body').on('click', '.sidebar-toggler', function (e) {
      var sidebar = $('.page-sidebar');
      var sidebarMenu = $('.page-sidebar-menu');
      var sidebarDiv = $('.sidebar-toggler');
      $(".sidebar-search", sidebar).removeClass("open");

      if (body.hasClass("page-sidebar-closed")) {
        body.removeClass("page-sidebar-closed");
        sidebarDiv.removeClass("sidebar-close");
        sidebarDiv.find('.left-toggler').removeClass('hidden');
        sidebarDiv.find('.right-toggler').addClass('hidden');
        sidebarMenu.removeClass("page-sidebar-menu-closed");
        if ($.cookie) {
          $.cookie('sidebar_closed', '0');
        }
      } else {
        body.addClass("page-sidebar-closed");
        sidebarDiv.addClass("sidebar-close");
        sidebarDiv.find('.left-toggler').addClass('hidden');
        sidebarDiv.find('.right-toggler').removeClass('hidden');
        sidebarMenu.addClass("page-sidebar-menu-closed");
        if (body.hasClass("page-sidebar-fixed")) {
          sidebarMenu.trigger("mouseleave");
        }
        if ($.cookie) {
          $.cookie('sidebar_closed', '1');
        }
      }

      $(window).trigger('resize');
    });
  };

  // Handles Bootstrap Tabs.
  var handleTabs = function () {
    // fix content height on tab click
    $('body').on('shown.bs.tab', 'a[data-toggle="tab"]', function () {
      handleSidebarAndContentHeight();
    });
  };

  // 返回顶部按钮
  var handleGoTop = function () {
    var offset = 300;
    var duration = 500;

    if (navigator.userAgent.match(/iPhone|iPad|iPod/i)) {  // ios
      $(window).bind("touchend touchcancel touchleave", function (e) {
        if ($(this).scrollTop() > offset) {
          $('.scroll-to-top').fadeIn(duration);
        } else {
          $('.scroll-to-top').fadeOut(duration);
        }
      });
    } else {  // general
      $(window).scroll(function () {
        if ($(this).scrollTop() > offset) {
          $('.scroll-to-top').fadeIn(duration);
        } else {
          $('.scroll-to-top').fadeOut(duration);
        }
      });
    }

    $('.scroll-to-top').click(function (e) {
      e.preventDefault();
      $('html, body').animate({scrollTop: 0}, duration);
      return false;
    });
  };

  // 优化高度为 100% 的元素(block, protlet ...)
  var handle100HeightContent = function () {

    var target = $('.full-height-content');
    var height;

    height = AIot.getViewPort().height -
      $('.page-header').outerHeight(true) -
      $('.page-footer').outerHeight(true) -
      $('.page-title').outerHeight(true) -
      $('.page-bar').outerHeight(true);

    if (target.hasClass('portlet')) {
      var portletBody = target.find('.portlet-body');

      if (AIot.getViewPort().width < AIot.getResponsiveBreakpoint('md')) {
        AIot.destroySlimScroll(portletBody.find('.full-height-content-body')); // destroy slimscroll
        return;
      }

      height = height -
        target.find('.portlet-title').outerHeight(true) -
        parseInt(target.find('.portlet-body').css('padding-top')) -
        parseInt(target.find('.portlet-body').css('padding-bottom')) - 2;

      if (target.hasClass("full-height-content-scrollable")) {
        height = height - 35;
        portletBody.find('.full-height-content-body').css('height', height);
        AIot.initSlimScroll(portletBody.find('.full-height-content-body'));
      } else {
        portletBody.css('min-height', height);
      }
    } else {
      if (AIot.getViewPort().width < AIot.getResponsiveBreakpoint('md')) {
        AIot.destroySlimScroll(target.find('.full-height-content-body')); // destroy slimscroll
        return;
      }

      if (target.hasClass("full-height-content-scrollable")) {
        height = height - 35;
        target.find('.full-height-content-body').css('height', height);
        AIot.initSlimScroll(target.find('.full-height-content-body'));
      } else {
        target.css('min-height', height);
      }
    }
  };

  // Hower Dropdowns
  var handleDropdownHover = function () {
    $('[data-hover="dropdown"]').not('.hover-initialized').each(function () {
      $(this).dropdownHover();
      $(this).addClass('hover-initialized');
    });
  };

  var getDataTablesLangConfig = function getDataTablesLangConfig(config) {
    $.ajax({
      url: $('#servletContextPath').val() + "/service/common/getDataTablesLangConfig",
      type: 'GET',
      async: false,
      success: function success(data) {
        config = data;
      }
    });
  };

  function create_mask() {//创建遮罩层的函数
    var mask = document.createElement("div");
    mask.id = "mask";
    mask.style.position = "absolute";
    mask.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=4,opacity=25)";//IE的不透明设置
    mask.style.opacity = 0.4;//Mozilla的不透明设置
    mask.style.background = "black";
    mask.style.top = "0px";
    mask.style.left = "0px";
    mask.style.width = document.body.clientWidth + document.body.scrollLeft;
    mask.style.height = document.body.clientHeight + document.body.scrollTop;
    mask.style.zIndex = 2000;
    document.body.appendChild(mask);
  };

  var saveColumn = function saveColumn(col_content, operate_code) {
    var json = {
      operate_code: operate_code,
      col_content: col_content
    };
    $.ajax({
      type: "POST",
      url: $('#servletContextPath').val() + '/service/common/saveColumn',
      data: '&json=' + JSON.stringify(json),
      error: function (err) {
        alert(err.message);
      },
      success: function (data) {
        if (data.resultCode !== '0000') {
          toastr.error(data.resultException);
        }
      }
    });
  };

  return {

    // 主函数
    init: function () {
      // 注意!!!: 不要修改函数调用顺序.

      // 核心方法
      handleInit(); // 初始化核心变量
      handleOnResize(); // 响应式

      // UI 组件
      handleUniform(); // uniform 插件美化 radio & checkboxes
      handleiCheck(); // icheck 插件 radio & checkboxes
      handleBootstrapSwitch(); // bootstrap switch plugin
      handleScrollers(); // slim scrolling contents
      handleFancybox(); // fancy box
      handleSelect2(); // Select2 dropdowns
      handlePortletTools(); //portlet 工具栏(refresh, configure, toggle, remove)
      handleAlerts(); // closabled alerts
      handleDropdowns(); // dropdowns
      handleTabs(); // tabs
      handleTooltips(); // bootstrap tooltips
      handlePopovers(); // bootstrap popovers
      handleAccordions(); // accordions
      handleModals(); // modals
      handleBootstrapConfirmation(); // bootstrap confirmations

      // Hacks
      handleFixInputPlaceholderForIE(); //IE8 & IE9 input placeholder issue fix

      // Layout
      this.configPageLoading();
      this.initLayout();
    },

    // 主函数(ajax完成之后初始化核心javascript)
    initAjax: function () {
      handleUniform(); // uniform 插件美化 radio & checkboxes
      handleiCheck(); // icheck 插件 radio & checkboxes
      handleBootstrapSwitch(); // bootstrap switch plugin
      handleDropdownHover(); // dropdown hover
      handleScrollers(); // slim scrolling contents
      handleSelect2(); // Select2 dropdowns
      handleFancybox(); // fancy box
      handleDropdowns(); // dropdowns
      handleTooltips(); // bootstrap tooltips
      handlePopovers(); // bootstrap popovers
      handleAccordions(); // accordions
      handleBootstrapConfirmation(); // bootstrap confirmations
    },

    // 初始化 UI 组件
    initComponents: function () {
      this.initAjax();
    },

    initHeader: function () {
    },

    setSidebarMenuActiveLink: function (mode, el) {
      handleSidebarMenuActiveLink(mode, el);
    },

    // 初始化 sidebar
    initSidebar: function () {
      handleSidebarMenu(); // 主菜单
      handleSidebarToggler(); // sidebar 隐藏/显示

      handleMenuSelected();
      // handleSidebarMenuActiveLink('match'); // 通过url匹配实现sidebar切换效果, 暂留
    },

    initContent: function () {
      handle100HeightContent(); // 优化高度为 100% 的元素(block, protlet ...)
      handleTabs(); // bootstrah tabs

      AIot.addResizeHandler(handleSidebarAndContentHeight); // 浏览器窗口大小改变时, 重新计算 sidebar 和 content的高度
      AIot.addResizeHandler(handle100HeightContent); // 浏览器窗口大小改变时, 重画
    },

    initFooter: function () {
      handleGoTop(); // 返回顶部
    },

    initLayout: function () {
      this.initHeader();
      this.initSidebar();
      this.initContent();
      this.initFooter();
      handleSidebarAndContentHeight();
    },

    // (公用方法) 修复sidebar 和 content 高度
    fixContentHeight: function () {
      handleSidebarAndContentHeight();
    },

    // (公用方法) 缓存上次打开, 并需要点击关闭的popover
    setLastPopedPopover: function (el) {
      lastPopedPopover = el;
    },

    // (公用方法) 添加浏览器窗口尺寸改变时的回调函数
    addResizeHandler: function (func) {
      resizeHandlers.push(func);
    },

    //(公用方法) 调用 _runresizeHandlers, 手动出发浏览器窗口改变的回调函数
    runResizeHandlers: function () {
      _runResizeHandlers();
    },

    // (公用方法) 浏览器窗口视图滚动到指定 DOM 元素
    scrollTo: function (el, offeset) {
      var pos = (el && el.size() > 0) ? el.offset().top : 0;

      if (el) {
        if ($('body').hasClass('page-header-fixed')) {
          pos = pos - $('.page-header').height();
        }
        pos = pos + (offeset ? offeset : -1 * el.height());
      }

      $('html,body').animate({
        scrollTop: 0
      }, 'slow');
    },

    addDataTableSearchBtnGroup: function addDataTableSearchBtnGroup(el, options) {
      var opts = $.extend(true, {
        placeholder: '文件名',
        inputclass: 'dt-search-input',
        btnclass: 'dt-search-btn',
        iclass: 'glyphicon glyphicon-search',
        searchClass: 'glyphicon glyphicon-search search',
        defaulti: 'form-control input-sm',
        defaultb: 'btn btn-sm',
        iconClass: 'glyphicon glyphicon-remove-circle page-tools-clear'
      }, options);
      var $tmp;
      var dl = $('<div>').appendTo(el);
      $('<a>', {class: opts.searchClass}).appendTo(dl);
      $('<input>', {
        class: opts.defaulti + ' ' + opts.inputclass,
        required: true,
        placeholder: opts.placeholder
      }).appendTo(dl);
      $('<i>', {class: opts.iconClass, href: 'javascript:;'}).appendTo(dl);

      // $tmp = $('<a>', {
      //   class: opts.defaultb + ' ' + opts.btnclass,
      //   type: 'button'
      // });
      // $('<i>', {class: opts.iclass}).appendTo($tmp);
      // $tmp.appendTo(el);
    },

    moveExportBtnToPageToolsBar: function moveExportBtnToPageToolsBar(btnContainer, el) {
      var _el = el || '.page-tools-sub > li:nth-child(2)';
      btnContainer
        .addClass('hidden')
        .find('a')
        .children('span')
        .each(function eachCbFunc() {
          $(this).parent().html($(this).html());
        });

      btnContainer
        .find('a')
        .removeClass()
        .wrap('<li></li>')
        .parent()
        .insertAfter(_el);
    },

    initSlimScroll: function (el) {
      $(el).each(function () {
        if ($(this).attr("data-initialized")) {
          return; // exit
        }

        var height;

        if ($(this).attr("data-height")) {
          height = $(this).attr("data-height");
        } else {
          height = $(this).css('height');
        }

        $(this).slimScroll({
          allowPageScroll: true, // allow page scroll when the element scroll is ended
          size: '7px',
          color: ($(this).attr("data-handle-color") ? $(this).attr("data-handle-color") : '#bbb'),
          wrapperClass: ($(this).attr("data-wrapper-class") ? $(this).attr("data-wrapper-class") : 'slimScrollDiv'),
          railColor: ($(this).attr("data-rail-color") ? $(this).attr("data-rail-color") : '#eaeaea'),
          position: isRTL ? 'left' : 'right',
          height: height,
          alwaysVisible: ($(this).attr("data-always-visible") == "1" ? true : false),
          railVisible: ($(this).attr("data-rail-visible") == "1" ? true : false),
          disableFadeOut: true
        });

        $(this).attr("data-initialized", "1");
      });
    },

    destroySlimScroll: function (el) {
      $(el).each(function () {
        if ($(this).attr("data-initialized") === "1") { // destroy existing instance before updating the height
          $(this).removeAttr("data-initialized");
          $(this).removeAttr("style");

          var attrList = {};

          // store the custom attribures so later we will reassign.
          if ($(this).attr("data-handle-color")) {
            attrList["data-handle-color"] = $(this).attr("data-handle-color");
          }
          if ($(this).attr("data-wrapper-class")) {
            attrList["data-wrapper-class"] = $(this).attr("data-wrapper-class");
          }
          if ($(this).attr("data-rail-color")) {
            attrList["data-rail-color"] = $(this).attr("data-rail-color");
          }
          if ($(this).attr("data-always-visible")) {
            attrList["data-always-visible"] = $(this).attr("data-always-visible");
          }
          if ($(this).attr("data-rail-visible")) {
            attrList["data-rail-visible"] = $(this).attr("data-rail-visible");
          }

          $(this).slimScroll({
            wrapperClass: ($(this).attr("data-wrapper-class") ? $(this).attr("data-wrapper-class") : 'slimScrollDiv'),
            destroy: true
          });

          var the = $(this);

          // reassign custom attributes
          $.each(attrList, function (key, value) {
            the.attr(key, value);
          });

        }
      });
    },

    // (公用方法) 回到浏览器顶部
    scrollTop: function () {
      AIot.scrollTo();
    },

    // NProgress 进度条初始化
    configPageLoading: function (options) {
      options = options || {parent: '#container'};
      NProgress.configure(options);
    },

    startPageLoading: function () {
      NProgress.start();
    },

    stopPageLoading: function () {
      NProgress.done();
    },

    alert: function (options) {

      options = $.extend(true, {
        container: "", // alert 的容器 container (默认添加在 "page-bar" 或 "page-creadcrumbs" 后)
        place: "append", // "append" or "prepend" in container
        type: 'success', // alert 类型: success, info, warning, danger
        message: "", // alert 消息体
        close: true, // 关闭按钮
        reset: true, // 关闭之前所有的alert
        focus: true, // 在alert显示之后,强制滚动到该alert
        closeInSeconds: 0, // 在alert定义之后 closeInSeconds 秒自动关闭
        icon: "" // 在message之前绘制图标
      }, options);

      var id = AIot.getUniqueID("asiaui_alert");

      var html = '<div id="' + id + '" class="asiaui-alerts alert alert-' + options.type + ' fade in">' + (options.close ? '<button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>' : '') + (options.icon !== "" ? '<i class="fa-lg fa fa-' + options.icon + '"></i>  ' : '') + options.message + '</div>';

      if (options.reset) {
        $('.asiaui-alerts').remove();
      }

      if (!options.container) {
        if ($('body').hasClass("page-container-bg-solid")) {
          $('.page-title').after(html);
        } else {
          if ($('.page-bar').size() > 0) {
            $('.page-bar').after(html);
          } else {
            $('.page-breadcrumb').after(html);
          }
        }
      } else {
        if (options.place == "append") {
          $(options.container).append(html);
        } else {
          $(options.container).prepend(html);
        }
      }

      if (options.focus) {
        AIot.scrollTo($('#' + id));
      }

      if (options.closeInSeconds > 0) {
        setTimeout(function () {
          $('#' + id).remove();
        }, options.closeInSeconds * 1000);
      }

      return id;
    },

    // 初始化 uniform 元素
    initUniform: function (els) {
      if (els) {
        $(els).each(function () {
          if ($(this).parents(".checker").size() === 0) {
            $(this).show();
            $(this).uniform();
          }
        });
      } else {
        handleUniform();
      }
    },

    // 更新/同步 uniform 插件的 checkbox 和radios 元素
    updateUniform: function (els) {
      $.uniform.update(els); // update the uniform checkbox & radios UI after the actual input control state changed
    },

    // (公用方法) 初始化 fancybox 插件
    initFancybox: function () {
      handleFancybox();
    },

    //public helper function to get actual input value(used in IE9 and IE8 due to placeholder attribute not supported)
    getActualVal: function (el) {
      el = $(el);
      if (el.val() === el.attr("placeholder")) {
        return "";
      }
      return el.val();
    },

    //public function to get a paremeter by name from URL
    getURLParameter: function (paramName) {
      var searchString = window.location.search.substring(1),
        i, val, params = searchString.split("&");

      for (i = 0; i < params.length; i++) {
        val = params[i].split("=");
        if (val[0] == paramName) {
          return unescape(val[1]);
        }
      }
      return null;
    },

    // check for device touch support
    isTouchDevice: function () {
      try {
        document.createEvent("TouchEvent");
        return true;
      } catch (e) {
        return false;
      }
    },

    // To get the correct viewport width based on  http://andylangton.co.uk/articles/javascript/get-viewport-size-javascript/
    getViewPort: function () {
      var e = window,
        a = 'inner';
      if (!('innerWidth' in window)) {
        a = 'client';
        e = document.documentElement || document.body;
      }

      return {
        width: e[a + 'Width'],
        height: e[a + 'Height']
      };
    },

    getUniqueID: function (prefix) {
      return 'prefix_' + Math.floor(Math.random() * (new Date()).getTime());
    },

    // check IE8 mode
    isIE8: function () {
      return isIE8;
    },

    // check IE9 mode
    isIE9: function () {
      return isIE9;
    },

    //check RTL mode
    isRTL: function () {
      return isRTL;
    },

    // check IE8 mode
    isAngularJsApp: function () {
      return (typeof angular == 'undefined') ? false : true;
    },

    getResponsiveBreakpoint: function (size) {
      // bootstrap responsive breakpoints
      var sizes = {
        'xs': 480,     // extra small
        'sm': 768,     // small
        'md': 992,     // medium
        'lg': 1200     // large
      };

      return sizes[size] ? sizes[size] : 0;
    },

    getColumn: function getColumn(operate_code, defaultCol) {
      var showCol = [];
      var hideCol = [];
      var col = [];
      var colname = {};
      $('#column_toggler').find('input').each(function (index, ele) {
        var $ele = $(ele);
        colname[$ele.data('name')] = $ele.data('column');
      });
      $.ajax({
        url: $('#servletContextPath').val() + '/service/common/queryColumn',
        async: false,
        data: {
          operate_code: operate_code
        },
        success: function (data) {
          if (data.resultList.length !== 0) {
            col = JSON.parse(data.resultList[0].col_content);
          } else {
            col = defaultCol;
          }
          for (var key in col) {
            if (col[key]) {
              showCol.push(colname[key]);
            } else {
              hideCol.push(colname[key])
            }
          }
        }
      });
      return {showCol: showCol, hideCol: hideCol};
    },
    bindSaveColEvent: function bindColEvent($controller, operateCode, col) {
      $controller.on('hide.bs.dropdown', function bindEventCbFunc() {
        var col_content = {};
        $('input[type="checkbox"]', $controller).each(function (index, e) {
          if ($(this).is(":checked")) {
            col_content[$(this).attr('data-name')] = true;
          } else {
            col_content[$(this).attr('data-name')] = false;
          }
        });
        saveColumn(col_content, operateCode);
      });

      $('input[type="checkbox"]', $controller).uniform();
      //  console.log(col)
      //  checkbox
      $.each(col.showCol, function (index, key) {
        $('[data-column=' + key + ']', $controller).attr('checked', 'true');
        $('[data-column=' + key + ']', $controller).parent().addClass('checked');
      });
      $.each(col.hideCol, function (index, key) {
        $('[data-column=' + key + ']', $controller).removeAttr('checked');
        $('[data-column=' + key + ']', $controller).parent().removeClass('checked');
      });
    },

    /**
     * 设置bootstrapWizard的title
     * @param tab
     * @param navigation
     * @param index
     * @param options 配置
     */
    setWizardHeadTitle: function setWizardHeadTitle(tab, navigation, index, options) {
      var opts = $.extend(true, {
        wizard: '#createAccount',
        back: '.button-previous',
        next: '.button-next',
        submit: '.button-submit',
        title: '.page-title',
        info: '第 _STEP_ 步, 共 _TOTAL_ 步',
        submitVisAll: false
      }, options);
      var $wizard = $(opts.wizard);
      var total = navigation.find('li').length;
      var current = index + 1;
      var liList;
      var i;
      // 设置title
      $('.step-title', $wizard).text(
        opts.info.replace(/_STEP_/, index + 1).replace(/_TOTAL_/, total)
      );
      // set done steps
      $('li', $wizard).removeClass('done');
      liList = navigation.find('li');
      for (i = 0; i < index; i++) {
        $(liList[i]).addClass('done');
      }

      if (current === 1) {
        $wizard.find(opts.back).hide();
      } else {
        $wizard.find(opts.back).show();
      }

      if (current >= total) {
        $wizard.find(opts.next).hide();
        $wizard.find(opts.submit).show();
      } else {
        $wizard.find(opts.next).show();
        if (!opts.submitVisAll) {
          $wizard.find(opts.submit).hide();
        }
      }
      AIot.scrollTo($(opts.title));
    },
    getDataTablesLangConfig: getDataTablesLangConfig,

    /**
     * select2通用方法
     * @param $containr select2所在容器
     * @param url 调用的 url
     * @param select2Conf id 和 value
     * @param option
     */
    initSelect: function ($container, option, width, callback) {
      var widthObj = '100%';

      if (width && width !== '') {
        widthObj = width;
      }

      $container
        .find('select.select-remote')
        .each(function each(i, ele) {
          var $ele = $(ele);
          var conf = option.select2Conf[$ele.data('selectconf')];
          var tbl = conf.collection;
          var id = conf.selectId;
          var text = conf.selectText;
          var conditions = typeof conf.conditions !== 'undefined' ? conf.conditions : {};
          var comparelist = typeof conf.comparelist !== 'undefined' ? conf.comparelist : [];
          var groupBy = typeof conf.groupBy !== 'undefined' ? conf.groupBy : {};
          var startEnd = typeof conf.startEnd !== 'undefined' ? conf.startEnd : 0;
          var tableRef = typeof conf.tableRef !== 'undefined' ? conf.tableRef : {};
          var disseachable = conf.disseachable == true ? -1 : false;

          if (option) {
            id = option.id || id;
            text = option.text || text;
          }
          $ele.select2({
            ajax: {
              url: $('#servletContextPath').val() + '/service/common/getSelect2Data',
              dataType: 'json',
              delay: 250,
              data: function (params) {
                var cond = {};
                if (typeof params.term !== 'undefined') {
                  cond[text] = encodeURI(params.term);
                } else {
                  cond[text] = params.term;
                }

                var postData = {
                  tbl: tbl,
                  projections: [id, text],
                  tableRef: tableRef,
                  search: cond,
                  conditions: conditions,
                  comparelist: comparelist,
                  startEnd: startEnd,
                  groupBy: groupBy,
                  pages: params.page,
                  row: 10
                };
                if (conf.islocale !== undefined) {
                  postData.islocale = conf.islocale;
                }
                if (option.init && typeof(option.init) === "function") {
                  postData = option.init($ele, postData, $container);
                }
                return {
                  json: JSON.stringify(postData)
                };
              },
              processResults: function (data, params) {
                // parse the results into the format expected by Select2
                // since we are using custom formatting functions we do not need to
                // alter the remote JSON data, except to indicate that infinite
                // scrolling can be used
                var result = [];
                params.page = params.page || 1;
                data && data.forEach(function each(obj) {
                  result.push(
                    {
                      id: obj[id],
                      text: obj[text]
                    }
                  )
                });
                if (option.after && typeof(option.after) === "function") {
                  result = option.after($ele, result, data);
                }

                return {
                  results: result,
                  pagination: {
                    more: 10 <= result.length
                  }
                };
              },
              cache: true
            },
            language: "zh-CN",
            placeholder: '请选择',
            allowClear: true,//允许清空
            width: widthObj,
            escapeMarkup: function (markup) {
              return markup;
            }, // 自定义格式化防止xss注入
            minimumResultsForSearch: disseachable || false,
            minimumInputLength: option.minLength || 0 //最少输入多少个字符后开始查询
          });
        });
      $container
        .find('select:not(.select-remote)').not('.no-init')
        .each(function each(i, ele) {

          $(ele).select2({
              width: '100%',
              minimumResultsForSearch: -1
            }
          );
        });
      if (typeof callback === 'function') {
        callback();
      }
    },
    formatDate: function formatDate(t, format) {
      var time;
      var result;
      var yyyy;
      var yy;
      var MM;
      var dd;
      var HH;
      var mm;
      var ss;
      var ms;
      format = format || 'yyyy-MM-dd HH:mm:ss'; //eslint-disable-line
      if (!t) t = new Date(); //eslint-disable-line
      try {
        time = new Date(t);
        result = '';
        yyyy = '' + time.getFullYear();
        yy = yyyy.substr(-2);
        MM = ('000' + (time.getMonth() + 1)).substr(-2);
        dd = ('000' + time.getDate()).substr(-2);
        HH = ('000' + time.getHours()).substr(-2);
        mm = ('000' + time.getMinutes()).substr(-2);
        ss = ('000' + time.getSeconds()).substr(-2);
        ms = ('000' + time.getMilliseconds()).substr(-3);
        result = format.replace('yyyy', yyyy);
        result = result.replace('yy', yy);
        result = result.replace('MM', MM);
        result = result.replace('dd', dd);
        result = result.replace('HH', HH);
        result = result.replace('mm', mm);
        result = result.replace('ss', ss);
        result = result.replace('ms', ms);
        format = null; //eslint-disable-line
        return result;
      } catch (e) {
        throw new Error(e);
      }
    },
    endDate: function () {
      return '2099-12-31 23:59:59'
    },
    getCreateFormValues: function getFormValues(form, select2Conf) {
      var inputs = form.find('*[name]');
      var docs = {};
      var showText = {};
      var result = {};
      var template = {};

      inputs.each(function each(i, ele) {
        var $ele = $(ele);
        var name = $ele.attr('name');
        var value;
        var conf;
        if ($ele.hasClass('date-picker')) {
          value = $ele.datepicker('getDate');
        } else if ($ele.hasClass('template')) {
          if ($ele.is('input[type="checkbox"]')) {
            if ($ele.parent().hasClass("checked")) {
              value = '1';
            } else {
              value = '0';
            }
          } else if ($ele.hasClass('select-remote')) {
            if ($ele.select2('data').length > 0) {
              conf = select2Conf[$ele.data('selectconf')];
              value = $ele.select2('data')[0].id;
              if (
                value !== '' && value !== null &&
                value !== undefined && value !== '[无变化]') {
                showText[conf.selectText] = $ele.select2('data')[0].text;
              }

            } else {
              value = $ele.select2('val');
            }
          }

          template[name] = value;
        } else if ($ele.hasClass('select-remote')) {
          if ($ele.select2('data').length > 0) {
            conf = select2Conf[$ele.data('selectconf')];
            value = $ele.select2('data')[0].id;
            if (
              value !== '' && value !== null &&
              value !== undefined && value !== '[无变化]') {
              showText[conf.selectText] = $ele.select2('data')[0].text;
            }
          } else {
            value = $ele.select2('val');
          }

        } else if ($ele.is('select')) {
          value = $ele.select2('val');

        } else if ($ele.is('input[type="checkbox"]')) {
          if ($ele.parent().hasClass("checked") || $ele.is(':checked')) {
            value = '1';
          } else {
            value = '0';
          }
        } else {
          //console.log($ele)
          value = ($ele.attr('value') + '').trim();
        }
        if (value === 'false') {
          value = false;
        }
        if (value === 'true') {
          value = true;
        }
        if (value == '') {
          value = null;
        }
        docs[name] = value;
      });
      result.postData = docs;
      result.showResult = showText;
      result.template = template;
      return result;
    },
    /**
     * 编辑时获取表单值.
     * @param form
     * @param select2Conf
     * @returns {{}}
     */
    getFormValues: function getFormValues(form, select2Conf) {
      var inputs = form.find('*[name]');
      var docs = {};
      var showText = {};
      var result = {};
      var template = {};

      inputs.each(function each(i, ele) {
        var $ele = $(ele);
        var name = $ele.attr('name');
        var value;
        var conf;
        var isChange = false;
        if ($ele.hasClass('date-picker')) {
          value = $ele.datepicker('getDate');
        } else if ($ele.hasClass('template')) {
          if ($ele.is('input[type="checkbox"]')) {
            if ($ele.parent().hasClass("checked")) {
              value = '1';
            } else {
              value = '0';
            }
            if ($ele.parent().hasClass("checked") !== $ele.get(0).defaultChecked) {
              isChange = true
            }
          } else if ($ele.hasClass('select-remote')) {
            if ($ele.select2('data').length > 0) {
              conf = select2Conf[$ele.data('selectconf')];
              value = $ele.select2('data')[0].id;
              if (
                value !== '' && value !== null &&
                value !== undefined && value !== '[无变化]') {
                showText[conf.selectText] = $ele.select2('data')[0].text;
              }

            } else {
              value = $ele.select2('val');
            }
            for (var i = 0; i < $ele.get(0).options.length; i++) {
              if ($ele.get(0).options[i].selected != $ele.get(0).options[i].defaultSelected) {
                isChange = true;
              }
            }
          }

          template[name] = value;
        } else if ($ele.hasClass('select-remote')) {
          if ($ele.select2('data').length > 0) {
            conf = select2Conf[$ele.data('selectconf')];
            value = $ele.select2('data')[0].id;
            if (
              value !== '' && value !== null &&
              value !== undefined && value !== '[无变化]') {
              showText[conf.selectText] = $ele.select2('data')[0].text;
            }
          } else {
            value = $ele.select2('val');
          }
          for (var i = 0; i < $ele.get(0).options.length; i++) {
            if ($ele.get(0).options[i].selected != $ele.get(0).options[i].defaultSelected) {
              isChange = true;
            }
          }
        } else if ($ele.is('select')) {
          value = $ele.select2('val');
          for (var i = 0; i < $ele.get(0).options.length; i++) {
            if ($ele.get(0).options[i].selected != $ele.get(0).options[i].defaultSelected) {
              isChange = true;
            }
          }
        } else if ($ele.is('input[type="checkbox"]')) {
          if ($ele.parent().hasClass("checked") || $ele.is(':checked')) {
            value = '1';
          } else {
            value = '0';
          }
          if ($ele.parent().hasClass("checked") !== $ele.get(0).defaultChecked) {
            isChange = true
          }
        } else {
          //  console.log($ele.val(), $ele.get(0).defaultValue);
          if ($ele.val() !== $ele.get(0).defaultValue) {
            isChange = true
          }
          value = $ele.val().replace(' ', '');
        }
        if (value === 'false') {
          value = false;
        }
        if (value === 'true') {
          value = true;
        }
        if (value == '') {
          value = null;
        }
        //  console.log(isChange, $ele.attr('name'));
        if (isChange && $ele.parents('div.col-md-6.hidden').length === 0 &&
          (!$ele.parent().parent().hasClass('hidden'))) {
          docs[name] = value;
        }
      });
      result.postData = docs;
      result.showResult = showText;
      result.template = template;
      return result;
    },
    //confirm弹窗
    confirm: function (e) {
      //console.log(e);
      create_mask();
      // confirm 模板
      $.ajax({
        url: $('#servletContextPath').val() + '/page/commonTable.html',
        type: 'POST',
        data: "url=/components/confirm.ftl",
        dataType: 'text',
        success: function successEventCbFunc(content) {
          console.log(content);
          $('#mask').html(content);
        }
      });
    },
    jqueryI18n: function jqueryI18n(func) {
      jQuery.i18n.properties({
        name: 'message', //资源文件名称
        path: $("#resourceRootPath").val() + '/resource/i18n', //资源文件路径
        mode: 'both', //用Map的方式使用资源文件中的值
        language: $.cookie("lang") || 'zh',
        callback: func
      })
    },
    /**
     * 根据
     * @param form
     * @param data
     */
    setFormValues: function setFormValues(form, data, select2Conf, defaultVal) {
      form.find('*[name]').each(function each(i, ele) {
        var $ele = $(ele);
        var name = $ele.attr('name');
        //console.log(name)
        var value = data[name];
        var conf;
        conf = select2Conf[$ele.data('selectconf')]

        if ($ele.hasClass('select-remote')) {
          $ele.val(null).select2({
            width: '100%'
          });
          var text;
          var conditions = {};
          if ('STATE_CODE' == conf.selectId && conf.conditions.LOCALE) {
            //conditions["LOCALE"] = 'zh';
            conditions["LOCALE"] = conf.conditions.LOCALE;
          }
          conditions[conf.selectId] = value;
          if (conf.tableRef) {
            var postData = {
              tbl: conf.collection,
              projections: [conf.selectId, conf.selectText],
              conditions: conditions,
              tableRef: conf.tableRef === undefined ? '' : conf.tableRef,
              row: 10,
              pages: 1
            };
          } else {
            var postData = {
              tbl: conf.collection,
              projections: [conf.selectId, conf.selectText],
              conditions: conditions,
              row: 10,
              pages: 1
            };
          }

          $.ajax({
            url: $('#servletContextPath').val() + '/service/common/getSelect2Data',
            async: false,
            type: 'GET',
            dataType: 'json',
            contentType: "application/json",
            data: {json: JSON.stringify(postData)},
            success: function (res) {
              text = res && res[0] && res[0][conf.selectText];
            }
          });

          if (defaultVal
            && !jQuery.isEmptyObject(defaultVal)
            && defaultVal[$ele.data('selectconf')]
            && defaultVal[$ele.data('selectconf')][value]) {
            text = defaultVal[$ele.data('selectconf')][value];
          } else if (data[conf.selectText] === undefined) {
            $.ajax({
              url: $('#servletContextPath').val() + '/service/common/getSelect2Data',
              async: false,
              type: 'GET',
              dataType: 'json',
              contentType: "application/json",
              data: {json: JSON.stringify(postData)},
              success: function (res) {
                text = res && res[0] && res[0][conf.selectText];
              }
            })
          } else {
            text = data[conf.setText];
          }
          $ele.select2({'data': [{id: value, text: text}]});
          $ele.val(value).select2({
            width: '100%'
          });
        } else if ($ele.hasClass('data-picker')) {

          $ele.datepicker('setDate', null);
          $ele.removeAttr('disabled');
          $ele.datepicker('setDate', new Date(value))

        } else if ($ele.is('select:not(.no-init')) {
          $ele.val(null).select2({
            width: '100%'
          });
          $ele.val(value).select2({
            width: '100%'
          });
        } else if ($ele.hasClass('modify')) {
          $ele.val('2');
        } else if ($ele.is('input[type="checkbox"]')) {
          if (value === '是') {
            $ele.prop("checked", 'true');
          } else {
            $ele.removeAttr("checked")
          }

        } else {
          // console.log($ele)
          $ele.val(null);
          $ele.val(value);
        }
      });
    },
    /**
     *detailForm 是详情带<p>标签的表单，为选择器
     *setForm 是modal要带数据的弹出框
     **/
    setformData: function serformData(detailForm, setForm) {
      var details = detailForm.find('*p[id]');
      var docs = {};
      details.each(function (index, ele) {
        var $ele = $(ele);
        var key = $ele.attr('id');
        var value = $ele.text().trim();
        var editKey = $('#' + setForm + ' [name=' + key + ']');

        docs[key] = $(this).attr('value');
        if (docs[key] === '' || docs[key] === undefined) {
          docs[key] = $(this).text();
        }

        if (editKey.hasClass('select-remote')) {
          if (parseInt($ele.attr('value')) === -1) {
            editKey.val(null).select2({
              width: '100%'
            });
          } else {

            editKey.select2({'data': [{id: $ele.attr('value'), text: value}]});
            editKey.val($ele.attr('value')).select2({
              width: '100%'
            });
          }
        } else if (editKey.is('input[type="checkbox"]')) {
          if ($ele.attr('value') === '1') {
            editKey.prop("checked", 'true');
            editKey.parent().addClass('checked');
          } else {
            editKey.removeAttr("checked");
            editKey.parent().removeClass('checked');
          }

        } else if (editKey.hasClass('data-picker')) {

          editKey.datepicker('setDate', null);
          editKey.removeAttr('disabled');
          editKey.datepicker('setDate', new Date(value))

        } else if (editKey.is('select')) {
          editKey.val(null).select2({
            width: '100%'
          });
          editKey.val($ele.attr('value')).select2({
            width: '100%'
          });

        } else {
          editKey.val(value);
        }
      });
      return docs;
    },
    /**
     * tongxin
     *detailForm 是详情带<p>标签的表单，为选择器
     *setForm 是modal要带数据的弹出框
     **/
    setSelfformData: function serformData(detailForm, setForm) {
      var details = detailForm.find('*p[id]');
      var docs = {};
      details.each(function (index, ele) {
        var $ele = $(ele);
        var key = $ele.attr('id');
        var value = $ele.text().trim();
        var editKey = $('#' + setForm + ' [name=' + key + ']');

        docs[key] = $(this).attr('value');
        if (docs[key] === '' || docs[key] === undefined) {
          docs[key] = $(this).text();
        }

        if (editKey.hasClass('select-remote')) {
          editKey.select2({'data': [{id: $ele.attr('value'), text: value}]});
          editKey.val($ele.attr('value')).select2({
            width: '100%'
          });
        } else if (editKey.is('input[type="checkbox"]')) {
          if ($ele.attr('value') === '1') {
            editKey.prop("checked", 'true');
            editKey.parent().addClass('checked');
          } else {
            editKey.removeAttr("checked");
            editKey.parent().removeClass('checked');
          }

        } else if (editKey.hasClass('data-picker')) {

          editKey.datepicker('setDate', null);
          editKey.removeAttr('disabled');
          editKey.datepicker('setDate', new Date(value))

        } else if (editKey.is('select')) {
          editKey.val(null).select2({
            width: '100%'
          });
          editKey.val($ele.attr('value')).select2({
            width: '100%'
          });

        } else {
          editKey.val(value);
        }
      });
      return docs;
    },
    /**
     * 设备表单编辑参数获取（包含变更记录参数封装）
     * @param paramsMap
     * @returns {{}}
     */
    getDevFormValue: function getDevFormValue(paramsMap) {
      var EXT = {};
      var json = [];
      EXT.CHANGELOG = {
        'operateCode': paramsMap.operateCode,
        'BUSINESS_ID': $('#' + paramsMap.formId).data('busid'),
      };
      EXT.CHANGELOG.TABLEINFO = [];
      var tableList = [];
      var inputs = $('#' + paramsMap.formId).find('*[name]');

      inputs.each(function each(i, ele) {
        var $ele = $(ele);
        var name = $ele.attr('name');
        var oldValue = $ele.attr('data-oldvalue');
        var newValue = $ele.val();
        //修改参数封装
        if (((oldValue == newValue && name.indexOf('PK_') >= 0) || oldValue != newValue)
          && typeof $ele.attr('data-tabname') != 'undefined') {
          if (oldValue == '-1' && (typeof newValue == 'undefined' || $.trim(newValue) == '')) {
            return true;
          }
          var tableName = $ele.attr('data-tabname');
          tableName = transformTabName(tableName + '');
          //获取修改字段的tableName
          if (oldValue != newValue && name.indexOf('PK_') < 0 && tableList.length === 0) {
            tableList.push(tableName);
          } else if (oldValue != newValue && name.indexOf('PK_') < 0) {
            if (!isExitInArr(tableList, tableName)) {
              tableList.push(tableName);
            }
          }

          if (json.length === 0) {
            var obj = {};
            var objChild = {};
            objChild[name] = newValue;
            objChild['MODIFY_TAG'] = '2';
            obj[tableName] = [objChild];
            json.push(obj);
          } else {
            var operateCode = isExitInOperate(json, tableName);
            if (operateCode.flag) {
              json[operateCode.idx][tableName][0][name] = newValue;
            } else {
              var obj = {};
              var objChild = {};
              objChild[name] = newValue;
              objChild['MODIFY_TAG'] = '2';
              obj[tableName] = [objChild];
              json.push(obj);
            }

          }
        }

        var acctId = '';
        if (typeof $('#hidEditAcctId').val() != 'undefined') {
          acctId = $('#hidEditAcctId').val();
        } else if (typeof $('#hidAcctId').val() != 'undefined') {
          acctId = $('#hidAcctId').val();
        } else {
          acctId = $('#acId').val();
        }
        if (oldValue != newValue) {
          if (typeof $ele.attr('data-tabname') != 'undefined') {
            var tableName = $ele.attr('data-tabname');
            //变更记录参数封装
            if ($ele.hasClass('changeLog')) {
              if (name.indexOf('PK_') >= 0) { //去除主键的前缀
                name = name.substring(3);
              }
              //select形式的自定义字段直接存其字面量
              if ($ele.hasClass('custom') && $ele.is('select')) {
                if (-1 !== parseInt(oldValue)) {
                  oldValue = $ele.find('option[value="' + oldValue + '"]').text();
                }
                newValue = $ele.find('option[value="' + newValue + '"]').text();
              }
              if (!isExitInLog(EXT.CHANGELOG.TABLEINFO, tableName)) {
                (EXT.CHANGELOG.TABLEINFO).push({
                  'TABLE_NAME': tableName,
                  'ACCT_ID': acctId,
                  'FIELDINFO': [{
                    'FIELD_NAME': name,
                    'OLD_VALUE': oldValue,
                    'NEW_VALUE': newValue
                  }]
                });
              } else {
                $.each(EXT.CHANGELOG.TABLEINFO, function (index, cur) {
                  if (cur['TABLE_NAME'] == tableName) {
                    cur['FIELDINFO'].push({
                      'FIELD_NAME': name,
                      'OLD_VALUE': oldValue,
                      'NEW_VALUE': newValue
                    });
                  }
                })
              }
            }
          }
        }
      });

      var result = {};

      if ((EXT.CHANGELOG.TABLEINFO).length > 0) {
        result.ext = EXT;
      }
      if (tableList.length > 0) {
        for (var j = 0; j < json.length; j++) {
          for (var obj in json[j]) {
            var flag = isExitInArr(tableList, obj);
            if (!flag) {
              json.remove(json[j]);
            }
          }
        }
        result.json = json;
      }
      return result;
    },

    /**
     * websocket 订阅信息
     * @param bayeuxPara
     * @param key
     * @param row
     */
    subscribeWebSocket: function (key, callback) {
      console.log(key);

      try {
        var bayeux = new Faye.Client('http://' + $('#fayeHost').val(), {timeout: 120});
      } catch (e) {
        alert("webSocket异常，请检查webSocket服务是否启动！");
        return;
      }
      var sub = bayeux.subscribe('/SystemMessage/' + key, function (message) {
        console.log(message);
        //var content = '异常类型：'+'<font color=red>'+ message.type+'</font>'+'</br>';
        if (typeof callback === 'function') {
          callback(message, key);
        }
      });
      return sub;
    },
    /**
     * dataTable 带数据
     * @param settings dataTable参数
     * @param inputCol 简单搜索输入框值
     * @param select2Conf 配置文件
     * @param controller 选择器
     */
    drawCallback: function (settings, inputCol, select2Conf, controller) {

      controller = controller == undefined ? '' : controller;
      var columns = JSON.parse(settings.oAjaxData.json).columns;
      var data = [];
      var vle = {};
      var numVal = {};
      for (var i = 0; i < columns.length; i++) {
        var name = columns[i]['name'];
        name = name.split('|')[0];
        var num = i;
        var value = decodeURI(columns[i].search.value.replace('coded`', ''));
        var tmp = {num: num, name: name, value: value};
        vle[name] = value;
        numVal[num] = value;
        data.push(tmp);
      }
      $.each(data, function (index, col) {
        var $ele = $(controller + ' .advance-search [data-column=' + col.num + ']');

        if ($ele.hasClass('select-remote')) {
          var selConf = select2Conf[$ele.attr('data-selectconf')];
          var id = vle[selConf['selectId']];
          if (id == undefined) {
            id = vle[selConf['setId']]
          }
          var text = '';
          var conditions = selConf.con == undefined ? {} : selConf.con;
          conditions[selConf.selectId] = id;

          var postData = {
            tbl: selConf.collection,
            projections: [selConf.selectId, selConf.selectText],
            conditions: conditions,
            row: 10,
            pages: 1
          };
          if (id !== '' && id !== undefined) {
            $.ajax({
              url: $('#servletContextPath').val() + '/service/common/getSelect2Data',
              async: false,
              type: 'GET',
              dataType: 'json',
              contentType: "application/json",
              data: {json: JSON.stringify(postData)},
              success: function (res) {
                text = res && res[0] && res[0][selConf.selectText];
                if (selConf.collection === 'tf_f_acct' && id === '-1') {
                  text = '共享';
                }
                var newOpen = new Option(text, id, true, true);
                $ele.append(newOpen).trigger('change');
              }
            });
          }
        } else {
          $ele.val(col.value).trigger('change');
        }
      });
      if (inputCol !== undefined) {
        inputCol = parseInt(inputCol);
        $(controller + '.dt-search-input').val(numVal[inputCol]);
      } else {
        var searchVal = JSON.parse(settings.oAjaxData.json).search;
        $(controller + '.dt-search-input').val(searchVal.value);
      }
    },

    /**
     * json对象转数组.
     * @param obj
     */
    getArrayData: function (json) {
      var arr = [];
      for (var obj in json) {
        arr.push(json[obj])
      }
      var result = {
        'arr': arr,
        'len': arr.length
      };
      return result;
    },

    /**
     * 控制 dataTable CheckBox 点击事件
     *
     * @param $table table
     * @param $dataTable datatable
     * @param callback 回调函数
     * @returns {*} 选中行数据
     */
    checkBoxCtrl: function ($table, $dataTable, callback) {
      $table.on('click', 'tr', function (event) {
        var $target = $(event.target);
        if (!$target.is('a')) {
          $(this)
            .find('input:checkbox')
            .each(function () {
              $(this)
                .parents('tbody')
                .find('input:checkbox')
                .not($target.find('input:checkbox'))
                .prop('checked', false);

              $(this).prop('checked', true);
              $(this).trigger('change');
            });

        }
        callback($dataTable.row(this).data());
      });
    },
      // checkACtrl: function ($table, $dataTable, callback) {
      //     $table.on('click', 'tr', function (event) {
      //         var $target = $(event.target);
      //         if (!$target.is('a')) {
      //             // $(this)
      //             //     .find('input:checkbox')
      //             //     .each(function () {
      //                     $(this)
      //                         .parents('tbody')
      //                         .find('input:checkbox')
      //                         .not($target.find('input:checkbox'))
      //                         .prop('checked', false);
      //
      //                     $(this).prop('checked', true);
      //                     $(this).trigger('change');
      //                 // });
      //
      //         }
      //         callback($dataTable.row(this).data());
      //     });
      // },

    /**
     * 清空表单
     * @param container
     */
    clearForm: function (container) {
      $(':input', container)
        .not(':button, :submit, :reset, :hidden')
        .val('')
        .attr('checked', false)
        .removeAttr('selected')
        .trigger('change');
      $(':input[type="checkbox"]', container).each(function () {
        if ($(this).prop('checked')) {
          $(this).parent().addClass('checked');
        } else {
          $(this).parent().removeClass('checked');
        }

      });
    },

    /**
     * 判断是否有行被选中
     *
     * @param $table
     * @returns {boolean}
     */
    noLineSeleced: function ($table) {
      var checkboxOn = $table.find('input:checkbox:checked');
      if (checkboxOn.length !== 1) {
        toastr.error("请选择一条记录!");
        return true;
      }
    },

    /**
     * 只有选中一条数据时才能编辑删除
     * @param $table
     * @param $btns 数组或单个
     * @param callback
     */
    checkIf1LineSelected: function ($table, $btns, callback) {
      if (Array.isArray($btns)) {
        $btns.forEach(function (ele) {
          ele.css('pointer-events', 'none');
        });
      } else {
        $btns.css('pointer-events', 'none');
      }

      var childCheck = $table.find('tr').find('td:first-child input[type="checkbox"]');
      childCheck
        .off('change').on('change', function () {
        var len = 0;
        childCheck.each(function () {
          if ($(this).attr('checked')) {
            len++;
          }
        });
        if (len === 1) {
          if (Array.isArray($btns)) {
            $btns.forEach(function (ele) {
              ele.css('pointer-events', '');
            });
          } else {
            $btns.css('pointer-events', '');
          }
        } else {
          if (Array.isArray($btns)) {
            $btns.forEach(function (ele) {
              ele.css('pointer-events', 'none');
            });
          } else {
            $btns.css('pointer-events', 'none');
          }
        }
      });

      if (typeof(callback) === 'function') {
        callback();
      }
    },

    /**
     * 控制 dataTable CheckBox 点击事件
     *
     * @param $table table
     * @param $dataTable datatable
     * @param callback 回调函数
     * @returns {*} 选中行数据
     */
    checkBoxCtrl: function ($table, $dataTable, callback) {
      $table.on('click', 'tr', function (event) {
        var $target = $(event.target);
        if (!$target.is('input:checkbox')) {
          $(this)
            .find('input:checkbox')
            .each(function () {
              $(this)
                .parents('tbody')
                .find('input:checkbox')
                .not($target.find('input:checkbox'))
                .prop('checked', false);
              $(this).prop('checked', true);
              $(this).trigger('change');
            });

        }
        callback($dataTable.row(this).data());
      });
    },
    /**
     * 初始化下拉菜单
     * @param modalid 所在模态框的ID
     * @param rowData
     * @param columnNames 列名数组
     */
    autoInitSelect: function (modalid,rowData,columnNames){
    var modalID = "#"+modalid;
    var $selects = $(modalID).find("select.select-remote");
    var dataArray = new Array();
    for (var i = 0; i <columnNames.length ; i+=2) {
      var ele = new Object();
      ele.id = rowData[columnNames[i]];
      ele.text = rowData[columnNames[i+1]];
      dataArray.push(ele);
    };
    $selects.each(function (i,obj) {
      var $obj = $(obj);
      $obj.children().remove();
      $obj.select2({'data': [dataArray[i]]});
      $obj.val(dataArray[i].text?dataArray[i].id:"").select2({
        width: '100%'
      });
    })
  },

  }
}();