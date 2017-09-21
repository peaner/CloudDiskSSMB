/**
 * 
 */

$(function(){
	//checkInput();
	createGroup();
	isModalClose("createGroupModal");
	isModalClose("groupInfoModal");
	isModalClose("addNewMemModal");
	quitOrDissolveGroup();
});

function createGroup(){
	var groupAjaxData = "";
	$("#btn_new").click(function(){
		$.ajax({
			type : "post",
			url : "../group/queryAllMember.do",
			async : true,
			beforeSend : function() {
				// alert("请求前的处理");
			},
			success : function(data) {
				if (data.result){
					var i = 0;
					var userData = eval(data);
					while (userData.rows[i]){
						innerHtml = '<li style="background: #F2F0B5" class="col-xs-3 list-group-item"><input type="checkbox" value="'
							+ userData.rows[i].userid + '"/><label class="text-center">'
							+ userData.rows[i].username + '</label></li>';
						$("#createGroupModal ul.list-group").append(innerHtml);
						i++;
					}
					groupAjaxData = getAjaxData(data);
					selectAll("crtGrp_selectAll");
				}
				else {
					bootbox.alert({
						title:'Failed',
						size:'small',
						message:'未查询到用户!',
					});
				}
			},
			complete : function(data) {
				// alert("请求完成的处理");
			},
			error : function() {
				bootbox.alert({
					title:'Failed',
					message:"读取用户失败!",
					size:'small',
				});
			}
		});
	});

	$('#createBtn').on('click',function(){
		if ($("#createGroupModal form").find("input[type='checkbox']").size() === 0){
			return false;
		}
		var ajaxUserId = new Array();
		var ajaxIndex = 0;
		$("#createGroupModal form").find("input[type='checkbox']").each(function(){
			if ($(this).is(':checked')){
				for (var i=0;i<groupAjaxData.length;i++){
					if (groupAjaxData[i].userId == $(this).val()){
						ajaxUserId[ajaxIndex++] = groupAjaxData[i].userId;
					}
				}
			}	
		});
		
		var groupObj = new Object();
		groupObj.userID = ajaxUserId;
		groupObj.groupName = $("#inGroupName").val();
		var groupJson = JSON.stringify(groupObj);
		$.ajax({
			data:$.param({
				groupJson : groupJson,
				}),
			type : "post",
			url : "../group/createGroup.do",
			async : true,
			beforeSend : function() {
					// alert("请求前的处理");
			},
			success :function(data) {
				$("#inGroupName").val("");
				if (data.result) {
					bootbox.alert({
						title:'Success',
						size:'small',
						message : "群组创建成功!",
							});	
					$('#createGroupModal').modal('hide');
					refreshTable('tb_groupFileInfo');
				} else {
					bootbox.alert({
						title:'Failed',
						message:'群组创建失败，请重试!',
						size:'small',
					});	
				}
			}
		});
	});
}

function queryAllGroupMember(queryData){
	$.ajax({
		url:'../group/queryGroupAllMember.do',
		type : "post",
		async : true,
		data:{
			groupId: queryData.groupid,
		},
		success:function(data){
			if (!isEmpty(data.result)){
				var i = 0;
				var userData = eval(data);
				$("#groupInfoModal").find(".modal-header label").attr("id",queryData.groupid);
				$("#groupInfoModal").find(".modal-header h4").text(queryData.groupname);
				while (userData.rows[i]){
					innerHtml = '<li style="background: #F9F9CF" class="col-xs-3 list-group-item text-center"><input class="col-xs-2" type="checkbox" value="'
						+ userData.rows[i].userid + '"/><label class="text-center col-xs-10">'
						+ userData.rows[i].username + '</label></li>';
					$("#groupInfoModal ul.list-group").append(innerHtml);
					i++;
				}
				selectAll("grpInfo_selectAll");
			}
		}
	});
}

$("#kickMemBtn").on('click',function() {
	if ($("#groupInfoModal ul.list-group").find("input[type='checkbox']").size() === 0) {
		return false;
	}
	var ajaxUserId = new Array();
	var tipInfoUserName = new Array();
	var ajaxIndex = 0;
	var isSelect = false;
	$("#groupInfoModal ul.list-group").find("input[type='checkbox']").each(function() {
		if ($(this).is(':checked')) {
			isSelect = true;
			ajaxUserId[ajaxIndex++] = $(this).val();
			tipInfoUserName[ajaxIndex-1] = $(this).parent().find("label").text();
		}
	});
	if (isSelect == false){
		bootbox.alert({
			title : 'Tip',
			size : 'small',
			message : "请选择需要移除的用户!",
		});
		return false;
	}
	var tipInfo = "";
	if (tipInfoUserName.length <= 10){
		for (var i=0;i<tipInfoUserName.length;i++){
			if (i<=tipInfoUserName.length-2){
				tipInfo += tipInfoUserName[i] + "、";
			}else {
				tipInfo += tipInfoUserName[i] + " 吗?";
			}
		}
	}else {
		for (var i=0;i<=10;i++){
			if (i<=9){
				tipInfo += tipInfoUserName[i] + "、";
			}else {
				tipInfo += tipInfoUserName[i] + " 等用户吗?";
			}
		}
	}
	
	bootbox.confirm({
		title:'移除成员',
		size:'small',
		message : '确定要移除 ' + tipInfo,
		buttons : {
			confirm : {
				label : '确认'
			},
			cancel : {
				label : '取消'
			}
		},
		callback : function(result){
			if (result){
				var groupObj = new Object();
				groupObj.userID = ajaxUserId;;
				groupObj.groupID = $("#groupInfoModal").find(".modal-header label").attr("id");
				var groupJson = JSON.stringify(groupObj);
				$.ajax({
					data : $.param({
							groupJson : groupJson,
							}),
					type : "post",
					url : "../group/kickGroupMember.do",
					async : true,
					beforeSend : function() {
						// alert("请求前的处理");
					},
					success : function(data) {
						if (data.result) {
							$("#groupInfoModal ul.list-group").find("input[type='checkbox']").each(function(index) {
								if ($(this).is(':checked')) {
									$(this).parent().remove();
								}
							});
							bootbox.alert({
								title : 'Success',
								size : 'small',
								message : "移除成功!",
							});
						} else {
							bootbox.alert({
								title : 'Failed',
								size : 'small',
								message : '移除失败，请重试!',
							});
						}
					}
				});
			}
		}
	});
	}
);

$("#addMemBtn").on("click",function(){
	queryRestMember();
});

$("#add_Btn").on("click",function(){
	if ($("#addNewMemModal ul.list-group").find("input[type='checkbox']").size() === 0) {
		return false;
	}
	var ajaxUserId = new Array();
	var tipInfoUserName = new Array();
	var ajaxIndex = 0;
	var isSelect = false;
	$("#addNewMemModal ul.list-group").find("input[type='checkbox']").each(function() {
		if ($(this).is(':checked')) {
			isSelect = true;
			ajaxUserId[ajaxIndex++] = $(this).val();
			tipInfoUserName[ajaxIndex-1] = $(this).parent().find("label").text();
		}
	});
	if (isSelect == false){
		bootbox.alert({
			title : 'Tip',
			size : 'small',
			message : "请选择需要加入群组的用户!",
		});
		return false;
	}
	var tipInfo = "";
	if (tipInfoUserName.length <= 10){
		for (var i=0;i<tipInfoUserName.length;i++){
			if (i<=tipInfoUserName.length-2){
				tipInfo += tipInfoUserName[i] + "、";
			}else {
				tipInfo += tipInfoUserName[i] + " 吗?";
			}
		}
	}else {
		for (var i=0;i<=10;i++){
			if (i<=9){
				tipInfo += tipInfoUserName[i] + "、";
			}else {
				tipInfo += tipInfoUserName[i] + " 等用户吗?";
			}
		}
	}
	
	bootbox.confirm({
		title:'添加成员',
		size:'small',
		message : '确定要添加 ' + tipInfo,
		buttons : {
			confirm : {
				label : '确认'
			},
			cancel : {
				label : '取消'
			}
		},
		callback : function(result){
			if (result){
				var groupObj = new Object();
				groupObj.userID = ajaxUserId;;
				groupObj.groupID = $("#groupInfoModal").find(".modal-header label").attr("id");
				var groupJson = JSON.stringify(groupObj);
				$.ajax({
					data : $.param({
							groupJson : groupJson,
							}),
					type : "post",
					url : "../group/addGroupMember.do",
					async : true,
					beforeSend : function() {
						// alert("请求前的处理");
					},
					success : function(data) {
						if (!isEmpty(data)){
							$("#addNewMemModal ul.list-group").find("input[type='checkbox']").each(function() {
								if ($(this).is(':checked')) {
									innerHtml = '<li style="background: #F9F9CF" class="col-xs-3 list-group-item text-center"><input class="col-xs-2" type="checkbox" value="'
										+ $(this).val() + '"/><label class="text-center col-xs-10">'
										+ $(this).siblings("label").text() + '</label></li>';
									$("#groupInfoModal ul.list-group").append(innerHtml);
								}
							});
							$("#addNewMemModal").modal("hide");
							bootbox.alert({
								title : 'Success',
								size : 'small',
								message : '添加成功!',
							});
							
						}
					}
				});
			}
		}
	});
});

function queryRestMember(){
	$.ajax({
		data:{
			groupID: $("#groupInfoModal").find(".modal-header label").attr("id"),
		},
		type : "post",
		url : "../group/queryRestGroupMember.do",
		async : true,
		success :function(data) {
			//alert(JSON.stringify(data));
			if (isEmpty(data.result)){
				bootbox.alert({
					title: 'Failed',
					size: 'small',
					message: '查询失败!',
				});
			}else {
				
				var userData = eval(data);
				var i = 0;
				while (userData.rows[i]){
					innerHtml = '<li style="background: #F9F9CF" class="col-xs-3 list-group-item text-center"><input class="col-xs-2" type="checkbox" value="'
						+ userData.rows[i].userid + '"/><label class="text-center col-xs-10">'
						+ userData.rows[i].username + '</label></li>';
					$("#addNewMemModal ul.list-group").append(innerHtml);
					i++;
				}
				$("#addNewMemModal").modal('show');
				selectAll("grpAdd_selectAll");
			}
			
		},
		error :function(data){
			
		}
	});
}



$("#btn_delete").on("click",function(){
	validPermission();
});

function validPermission(){
	var allSlectLineData = $("#tb_groupFileInfo").bootstrapTable('getSelections');
	$.ajax({
		url : '../group/getPresentUserName.do',
		type : 'post',
		async : true,
		success :function(data){
			if (!isEmpty(data)){
				var isAllCanDel = true;
				var tipInfo = "";
				var displayGrpNum = 0;
				var ajaxGroupId = new Array();
				for (var i = 0;i < allSlectLineData.length;i++){
					if (displayGrpNum <= 5){
						if (allSlectLineData[i].operator === data.rows){
							ajaxGroupId[displayGrpNum++] = allSlectLineData[i].groupid;
							tipInfo += allSlectLineData[i].groupname +"、";
						}else {
							isAllCanDel = false;
						}
						if (i == allSlectLineData.length - 1){
							tipInfo = tipInfo.substring(0, tipInfo.length-1);
							tipInfo += " 吗?";
						}
					}else {
						tipInfo = tipInfo.substring(0, tipInfo.length-1);
						tipInfo += " 等群组吗?";
						break;
					}
				}
				
				var messageStr;
				if (isAllCanDel == false){
					messageStr = '只能删除已选项中 ' + data.rows +' 为创建者的群组,是否继续执行删除?';
				}else {
					messageStr =  '确定解散 ' + tipInfo;
				}
				bootbox.confirm({
					title:'删除群组',
					size:'small',
					message : messageStr,
					buttons : {
						confirm : {
							label : '确认'
						},
						cancel : {
							label : '取消'
						}
					},
					callback :function(result){
						if (result){
							deleteGroup(ajaxGroupId);
						}
					}
				});
			}else {
				bootbox.alert({
					title: 'Failed',
					size: 'small',
					message: '删除失败!',
				});
			}
		}
	});
}

function deleteGroup(ajaxGroupId){
	var groupObj = new Object();
	groupObj.groupID = ajaxGroupId;
	var groupJson = JSON.stringify(groupObj);
	$.ajax({
		url:'../group/deleteGroup.do',
		data: {
			groupID: groupJson,
			},
		type : 'post',
		async : true,
		success :function (data){
			if (data.result == true){
				bootbox.alert({
					title: 'Success',
					size: 'small',
					message: '操作成功!',
				});
				refreshTable('tb_groupFileInfo');
			}else{
				bootbox.alert({
					title: 'Success',
					size: 'small',
					message: '操作失败!',
				});
			}
		}
	});
}

function checkInput(){
	$("#inGroupName").blur(function(){
		var inputVal = $("#inGroupName").val();
		if (inputVal.length === 0){
			bootbox.alert({
				title: 'Tip',
				size: 'small',
				message: '请输入组名!',
			});
		}
	});
	
	$.ajax({
		data: {
		},
		//type : "post",
		//url : "../group/createGroup.do",
		async : true,
		beforeSend : function() {
				// alert("请求前的处理");
		},
		success :function(data) {
			
		}
	});
}

function getOperator(operator){
	$.ajax({
		url : '../group/getPresentUserName.do',
		type : 'post',
		async : true,
		success :function(data){
			if (!isEmpty(data.result)){
				if (data.rows == operator){
					$("#dissolveGrpBtn").text("解散群组");
					$("#kickMemBtn").css("display","inline-block");
					$("#addMemBtn").css("display","inline-block");
				}else{
					$("#kickMemBtn").css("display","none");
					$("#addMemBtn").css("display","none");
					$("#dissolveGrpBtn").text("退出群组");
				}
			}else{
				bootbox.alert({
					title : 'Failed',
					message : '群组获取失败,请重试!',
					size : 'small',
				});
			}
		}
	});
}

function quitOrDissolveGroup(){
	$("#dissolveGrpBtn").bind('click',function(){
		var order = $("#dissolveGrpBtn").text().substr(0,2);
		bootbox.confirm({
			title: order + '群组',
			size:'small',
			message : '确定'+ order +'群组吗?',
			buttons : {
				confirm : {
					label : '确认'
				},
				cancel : {
					label : '取消'
				}
			},
			callback : function(result) {
				if (result) {				
					$.ajax({
						url:'../group/quitOrDissolveGroup.do',
						data: {
							groupID:$("#groupInfoModal").find(".modal-header label").attr("id"),
							},
						type : 'post',
						async : true,
						success :function (data){
							if (data.result == true){
								bootbox.alert({
									title: 'Success',
									size: 'small',
									message: '操作成功!',
								});
								$('#groupInfoModal').modal('hide');
								refreshTable('tb_groupFileInfo');
							}else{
								bootbox.alert({
									title: 'Success',
									size: 'small',
									message: '操作失败!',
								});
							}
						}
					});
				}
			}
		});
		
	});
}

function isModalClose(id){
	$("#"+id).on('hidden.bs.modal',function(){
		$(this).find("li").remove();
		$("#"+id).find(".col-xs-1").prop("checked",false);
	});
}

function getAjaxData(data){
	var array = new Array();
	for (var i=0;i<data.rows.length;i++){
		array[i] = new Object();
		array[i].userId = data.rows[i].userid;
		array[i].userName = data.rows[i].username;
	}
	return array;
}

function selectAll(id){
	$("#"+id).find("input:checkbox").on("change",function(){
		if ($(this).is(':checked')) {
			$(this).parent().siblings("ul.list-group").find("input:checkbox").prop("checked",true);
		}else{
			$(this).parent().siblings("ul.list-group").find("input:checkbox").prop("checked",false);
		}
	});
	$("#"+id).siblings("ul.list-group").find("input:checkbox").on("change",function(){
		if (!$(this).is(':checked')) {
			$("#"+id).find("input:checkbox").prop("checked",false);
		}else {
			var flag = true;
			$("#"+id).parent().find("ul.list-group li").each(function(index){
				if (!$(this).find("input:checkbox").is(':checked')){
					flag = false;
					return false;
				}
			});
			if (flag === true){
				$("#"+id).find("input:checkbox").prop("checked",true);
			}
		}
	});
}