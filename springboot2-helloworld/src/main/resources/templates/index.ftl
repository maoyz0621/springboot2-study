<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/dataTables.bootstrap.css">

<!-- jQuery -->
<script type="text/javascript" charset="utf8" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript" charset="utf8" src="js/AIot.js"></script>
<script type="text/javascript" charset="utf8" src="js/application.js"></script>

<!-- DataTables -->
<script type="text/javascript" charset="utf8" src="js/bootstrap.js"></script>
<script type="text/javascript" charset="utf8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf8" src="js/dataTables.bootstrap.js"></script>

<!--第二步：添加如下 HTML 代码-->
<div>
  <h3>静态数据展示</h3>
  <div>
    <button id="table_button">获取选中的行</button>
  </div>
  <table id="table1" class="table table-hover" width="100%">
    <thead>
    <tr class="info">
      <th>姓名</th>
      <th>位置</th>
      <th>工资</th>
      <th>起始时间</th>
      <th>办公</th>
      <th>其它信息</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
  </table>
</div>

<hr>

<div>
  <h3>动态数据展示</h3>
  <div class="row-fluid">
    <div class="pull-right">
      <div class="btn-group">
        <button type="button" class="btn btn-primary btn-sm" id="btn-add">
          <i class="fa fa-plus"></i>添加
        </button>
        <button type="button" class="btn btn-primary btn-sm" id="btn-delAll">
          <i class="fa fa-remove"></i>删除
        </button>
        <button type="button" class="btn btn-primary btn-sm" id="btn-export">
          <i class="fa fa-sign-in"></i>导出
        </button>
        <button type="button" class="btn btn-primary btn-sm" id="btn-re">
          <i class="fa fa-refresh"></i>刷新
        </button>
      </div>
    </div>
<#-- 查询按钮 start-->
    <div class="row">
      <form id="queryForm" action="" method="post">
        <div class="col-xs-2">
          <input type="text" id="keyword" name="keyword" class="form-control input-sm" placeholder="此处输入查询字段">
        </div>
        <button type="button" class="btn btn-primary btn-sm" id="btn-query">
          <i class="fa fa-search"></i>查询
        </button>
      </form>
    </div>
  </div>
<#-- 查询按钮 end -->

<#---->
  <div class="page-tools-wrapper">
    <ul class="page-tools clearfloat">
      <li class="float-left">
        <a href="" data-toggle="modal">
          <i class="fa fa-plus"></i>新建
        </a>
      </li>
      <ul class="page-tools-sub hidden-xs">
        <li>
          <a href="#" class="page-tools-refresh">
            <i class="icon-reload" style="font-weight: bold"></i>刷新
          </a>
        </li>
        <li class="dropdown dropdown-rtr dropdown-dark" id="superSearch">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-close-others="true" id="advance-search-toggle">
            <i class="fa fa-angle-right" style="font-weight: bold"></i>高级搜索
          </a>
          <ul class="dropdown-menu advance-search" id="advance-search">
            <li class="external">
              <h3>其它筛选器</h3>
            </li>
            <li class="advance-search-item clearfloat advance-select" id="advance-search0">
              <label class="search-item-name">字典类型</label>
              <select class="search-item-input advance-select select-remote" data-column="2"
                      data-selectconf="dataType" id="search-item-type">
                <option value=""></option>
              </select>
            </li>
            <li class="advance-search-item clearfloat advance-select">
              <label class="search-item-name">有效性</label>
              <select class="search-item-input advance-select select-remote" data-column="6"
                      data-selectconf="category" >
                <option value=""></option>
              </select>
            </li>
            <#--<li class="tool-seach">-->
              <#--<a href="javascript:;" class="advance-search-btn advance-search-button" id="advanceSearch">-->
                <#--<i class="glyphicon glyphi"></i>查询-->
              <#--</a>-->
            <#--</li>-->
            <li class="end"></li>
          </ul>
        </li>
        <li>
          <a href="#" class="page-tools-clear" id="advanceClear">
            <i class="glyphicon glyphicon-remove-circle"></i>清除
          </a>
        </li>
        <li class="dropdown dropdown-rtr dropdown-dark">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
            <i class="fa icon-list" style="font-weight: bold;"></i>显示列
          </a>
          <ul class="dropdown-menu" id="column_toggler">
            <li class="external">
              <h3>其它筛选器</h3>
            </li>
            <li>
              <label>
                <input type="checkbox" disabled checked data-column="0">序号</label>
            </li>
            <li class="end"></li>
          </ul>
        </li>
      </ul>
    </ul>
  </div>
  <table id="table" class="table table-hover" width="100%">
    <thead>
    <tr class="info">
      <th class="table-checkbox">
        <input type="checkbox" class="group-checkable" data-set="#favourable .checkboxes" id="checkAll"/>
      </th>
      <th>序&nbsp;&nbsp;号</th>
      <th>编&nbsp;&nbsp;号</th>
      <th>姓&nbsp;&nbsp;名</th>
      <th>位&nbsp;&nbsp;置</th>
      <th>工&nbsp;&nbsp;资</th>
      <th>起始时间</th>
      <th>操&nbsp;&nbsp;作</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
  </table>
</div>

你好${name};
性别：
<#if sex==1>
男
<#elseif sex == 2>
女
<#else>
未知
</#if>
遍历：
<#list userList as l>
${l},
</#list>
<script type="text/javascript" charset="utf8" src="/js/my.js"></script>
