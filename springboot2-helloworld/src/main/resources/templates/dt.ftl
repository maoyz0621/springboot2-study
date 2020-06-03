<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/dataTables.bootstrap.css">

<!-- jQuery -->
<script type="text/javascript" charset="utf8" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript" charset="utf8" src="js/jquery-validation/dist/jquery.validate.js"></script>
<script type="text/javascript" charset="utf8" src="js/jquery-validation/dist/localization/messages_zh.js"></script>
<script type="text/javascript" charset="utf8" src="js/AIot.js"></script>
<script type="text/javascript" charset="utf8" src="js/application.js"></script>

<!-- DataTables -->
<script type="text/javascript" charset="utf8" src="js/bootstrap.js"></script>
<script type="text/javascript" charset="utf8" src="js/datatables.js"></script>
<script type="text/javascript" charset="utf8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf8" src="js/dataTables.bootstrap.js"></script>

<!--第二步：添加如下 HTML 代码-->

<div>
  <h3>${title}</h3>
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
        <#-- 触发模态框 -->
        <a href="#createEmpManagement" data-toggle="modal">
          <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
          新建
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
      <th width="6%">序&nbsp;&nbsp;号</th>
      <th width="10%">编&nbsp;&nbsp;号</th>
      <th width="10%">姓&nbsp;&nbsp;名</th>
      <th width="20%">位&nbsp;&nbsp;置</th>
      <th width="15%">工&nbsp;&nbsp;资</th>
      <th width="20%">起始时间</th>
      <th width="15%">操&nbsp;&nbsp;作</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
  </table>
</div>

<!--  BEGIN CREATE empInfo  务必为 .modal 添加role="dialog" 和 aria-labelledby="..."属性，用于指向模态框的标题栏；为 .modal-dialog 添加 aria-hidden="true" 属性。-->
<div class="modal fade" id="createEmpManagement" tabindex="-1" role="dialog" aria-labelledby="createEmpModalLabel" data-backdrop="static">
  <div class="modal-dialog" role="document" aria-hidden="true">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="createEmpModalLabel">新建用户信息</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col-md-12">
            <div>
              <form action="#" class="form-horizontal form-padding"  role="form" id="addForm" method="POST">
                <div class="form-body">

                  <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">
                      <p class="text-left">姓&nbsp;&nbsp;名<span style="color: #c7254e">*</span></p>
                    </label>
                    <div class="col-sm-6">
                      <input type="text" name="username" class="form-control" id="username" placeholder="请输入用户名" required>
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="position" class="col-sm-2 control-label">
                      <p class="text-left">位&nbsp;&nbsp;置<span style="color: #c7254e">*</span></p>
                    </label>
                    <div class="col-sm-6">
                      <input type="text" name="position" class="form-control" id="position" placeholder="请输入位置信息">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="salary" class="col-sm-2 control-label">
                      <p class="text-left">工&nbsp;&nbsp;资<span style="color: #c7254e">*</span></p>
                    </label>
                    <div class="col-sm-6">
                      <input type="number" name="salary" class="form-control" id="salary" placeholder="请输入工资" required>
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="startdate" class="col-sm-2 control-label">
                      <p class="text-left">起始时间<span style="color: #c7254e">*</span></p>
                    </label>
                    <div class="col-sm-6">
                      <input type="text" name="startDate" class="form-control" id="startdate">
                    </div>
                  </div>

                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" id="createSubmitBtn">提交</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="createCancelBtn">取消</button>
      </div>
    </div>
  </div>
</div>
<!-- EDN CREATE empInfo -->
<!--  BEGIN edit empInfo -->
<div class="modal fade" id="editEmpManagement" tabindex="-1" role="dialog" aria-labelledby="editEmpModalLabel" data-backdrop="static">
  <div class="modal-dialog" role="document" aria-hidden="true">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="editEmpModalLabel">编辑用户信息</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col-md-12">
            <div>
              <form action="#" class="form-horizontal form-padding"  role="form" id="editForm" method="POST">
                <div class="form-body">

                  <div class="form-group">
                    <label for="id" class="col-sm-2 control-label">
                      <p class="text-left">编&nbsp;&nbsp;号<span style="color: #c7254e">*</span></p>
                    </label>
                    <div class="col-sm-6">
                      <input type="text" name = "acctId" class="form-control" disabled>
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">
                      <p class="text-left">姓&nbsp;&nbsp;名<span style="color: #c7254e">*</span></p>
                    </label>
                    <div class="col-sm-6">
                      <input type="text" name="username" class="form-control" id="username" placeholder="请输入用户名" required>
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="position" class="col-sm-2 control-label">
                      <p class="text-left">位&nbsp;&nbsp;置<span style="color: #c7254e">*</span></p>
                    </label>
                    <div class="col-sm-6">
                      <input type="text" name="position" class="form-control" id="position" placeholder="请输入位置信息">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="salary" class="col-sm-2 control-label">
                      <p class="text-left">工&nbsp;&nbsp;资<span style="color: #c7254e">*</span></p>
                    </label>
                    <div class="col-sm-6">
                      <input type="number" name="salary" class="form-control" id="salary" placeholder="请输入工资" required>
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="startdate" class="col-sm-2 control-label">
                      <p class="text-left">起始时间<span style="color: #c7254e">*</span></p>
                    </label>
                    <div class="col-sm-6">
                      <input type="text" name="startDate" class="form-control" id="startdate" disabled>
                    </div>
                  </div>

                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" id="editSubmitBtn">提交</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="editCancelBtn">取消</button>
      </div>
    </div>
  </div>
</div>
<!-- EDN edit empInfo -->
<!--  BEGIN delete empInfo -->
<div class="modal fade" id="delEmpManagement" tabindex="-1" role="dialog" aria-labelledby="delEmpModalLabel" data-backdrop="static">
  <div class="modal-dialog" role="document" aria-hidden="true">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <input name="acctId" type="hidden">
        <h4 class="modal-title" id="delEmpModalLabel">删除用户信息</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col-md-12">
            <div id="deleteDealerContent">
              您确定要删除<span id="spanDelAccount"></span>该用户信息吗？
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" id="delSubmitBtn">确认</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="delCancelBtn">取消</button>
      </div>
    </div>
  </div>
</div>
<!-- EDN delete empInfo -->

<div id="select1">
  <label for="select1">
    <select>
      <option value="1">1111</option>
      <option value="2">2222</option>
      <option value="3">3333</option>
      <option value="4">4444</option>
    </select>
  </label>
</div>

你好${name};
遍历：
<#list userList as l>
${l},
</#list>
<script type="text/javascript" charset="utf8" src="/js/dt.js"></script>
