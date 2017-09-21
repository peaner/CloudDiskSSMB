<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div id="collectionContent" style="display: none" class="row">
		<div class="col-lg-12">
			<div class="row">
				<div class="col-lg-12">
					<ol class="breadcrumb">
						<li><a href="#"><i class="fa fa-home"></i>主页</a></li>
						<li class="active"><span>收藏</span></li>
					</ol>
					<h2>收藏</h2>
				</div>
			</div>
			<!-- 收藏夹表 -->
			<div id="collectionTab" class="row">
				<div class="col-lg-12">
					<div class="main-box clearfix">
						<header class="main-box-header clearfix">							
							<div class="col-xs-1 btnClass">
								<button id="collectionBtn_download" type="button"
									class="btn btn-primary">下&nbsp;&nbsp;载</button>
							</div>
							<div class="col-xs-1 btnClass">
								<button id="collectionBtn_cancel" type="button"
									class="btn btn-primary">取&nbsp;消&nbsp;收&nbsp;藏</button>
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
							<div class="table-responsive clearfix">
								<table class="table table-striped table-hover"
									id="tb_collectfileInfo"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>