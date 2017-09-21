<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<!-- modal框 -->
	<div class="container">
		<div id="createGroupModal" class="modal fade in"
			style="display: none;">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">						 
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="createGroupModalLabel">新建群组</h4>						 
					</div>
					<div class="modal-body">
						<div style="margin-bottom: 20px">
							群组名:&nbsp;&nbsp;<input id="inGroupName" type="text" placeholder="输入群组名" />
						</div> 
						<form style="background: white;padding: 5px 0;
								height :220px;border: 2px solid #ccc;max-height: 220px;overflow-y: auto;">
							<h5 class="text-center" style="margin: 0;font-weight: 600;margin-top: 0">所有成员</h5>
							<div id="crtGrp_selectAll" style="text-align: left;width: 100%;">
								<input style="text-align: left;" class="col-xs-1" type="checkbox"/>
								<label>&nbsp;全选</label>
							</div>
							<ul  class="list-group">
							</ul>
						</form>
					</div>					 
					<div class="modal-footer">						         
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>						   
						<button id="createBtn" type="button" class="btn btn-primary">创建</button>						         
					</div>					     
				</div>
			</div>
		</div>
	</div>
</body>
</html>