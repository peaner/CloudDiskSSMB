<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div id="groupManageContent" style="display: none" class="row">
		<div class="col-lg-12">
			<div class="row">
				<div class="col-lg-12">
					<ol class="breadcrumb">
						<li><a href="#"><i class="fa fa-home"></i>主页</a></li>
						<li class="active"><span>群组管理</span></li>
					</ol>
					<h2>群组管理</h2>
				</div>
			</div>
			<!-- 群组管理表 -->
			<div id="groupManageTab" class="row">
				<div class="col-lg-12">
					<div class="main-box clearfix">
						<header class="main-box-header clearfix">
							<div class="col-xs-1 btnClass">
								<button id="btn_new" type="button" class="btn btn-primary"
									data-toggle="modal" data-target="#createGroupModal">新建群组
								</button>
							</div>
							<div class="dropdown col-xs-1 btnClass" style="margin-left:30px;">
								<button id="btn_delete" type="button" class="btn btn-danger"
									data-toggle="" data-target="">删除
								</button>
							</div>
							<div class="filter-block pull-right">
								<div class="form-group pull-left">
									<input type="text" class="form-control" placeholder="搜索...">
									<i class="fa fa-search search-icon"></i>
								</div>
								<a href="#" id="refreshTable" class="btn btn-primary pull-right">
									<i class="fa fa-refresh fa-lg"></i> 刷新
								</a>
							</div>
						</header>
						<div class="main-box-body clearfix" style="margin-top: -10px">
							<div id="toolbarGroup">
								<ol class="breadcrumb">
									<li><a href="#">示例文件夹1</a></li>
									<li><a href="#">示例文件夹2</a></li>
									<li class="active"><a href="#">示例文件夹3</a></li>
								</ol>
							</div>
							<div class="table-responsive clearfix">
								<table class="table table-striped table-hover"
									id="tb_groupFileInfo"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>