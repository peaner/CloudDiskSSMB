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
		<div id="shareFileModal" class="modal fade in"
			style="display: none;">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">						 
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="shareFileModalLabel">共享对象列表：</h4>						 
					</div>
					<div class="modal-body">
						<form id="shareFileForm" style="background: white;padding: 5px;height :220px;border: 2px solid #ccc;max-height: 220px;overflow-y: auto;">
							<ul>
								
							</ul>
						</form>
					</div>					 
					<div class="modal-footer">						         
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>						   
						<button id="shareFileBtn" type="button" class="btn btn-primary">共享</button>						         
					</div>					     
				</div>
			</div>
		</div>
	</div>
</body>
</html>