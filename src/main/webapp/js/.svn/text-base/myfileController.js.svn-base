$(function() {});

/**
 * 点击上传按钮打开上传文件modal
 */
$("#btn_upload").click(function() {
	clearFileInfo();
	$('#uploadFileModal').modal('show');
});

/**
 * 移除选择文件处理
 */
$("#clearUploadFile").click(function() {
	clearFileInfo();
});

/**
 * 共同处理，清理文件信息
 */
function clearFileInfo() {
	$("#fileToUpload").val("");
	$("#addfileName").html("");
	$("#progressNumber").val("");
	$("#uploadNote").val("");
}

/**
 * 文件上传提交处理
 */
$("#uploadFileSumbit").click(function() {
	var checkFlag = true;
	var daName = $("#fileToUpload").val();
	if (isEmpty(daName)) {
		checkFlag = false;
		$("#addfileName").html('<font color="red">提示：未选择文件，请重新选择待上传文件！<font>');
	}
	if (checkFlag) {
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "../file/upload.do", false);
		xhr.upload.addEventListener("progress", uploadProgress, false);
		xhr.addEventListener("load", uploadComplete, false);
		xhr.addEventListener("error", uploadFailed, false);
		xhr.addEventListener("abort", uploadCanceled, false);
		var fd = new FormData();
		fd.append("fileHandler", document.getElementById('fileToUpload').files[0]);
		fd.append("parentFolderID", $("#currentFolderId").val());
		fd.append("comment", $("#uploadNote").val());
		xhr.send(fd);
	}
});

/**
 * 选择文件事件处理
 */
$("#fileToUpload").change(function() {
	$("#addfileName").html("");
	var file = document.getElementById('fileToUpload').files[0];
	if (file == null) {
		$("#addfileName").html('<font color="red">提示：文件为空, 请重新选择文件！<font>');
		return;
	}
	// 文件大小限制
	if (file.size > 500 * 1024 * 1024) {
		$("#fileToUpload").val("");
		$("#addfileName").html('<font color="red">错误：文件超过500M，禁止上传！<font>');
		return;
	}
	// 文件名称长度限制
	if (file.name.length > 50) {
		$("#fileToUpload").val("");
		$("#addfileName").html('<font color="red">错误：文件名称超过50位，请重新选择文件！<font>');
		return;
	}
	
	// 云盘剩余大小检查
	$.ajax({
		url : "../file/diskSizeCheck.do",
		dataType : "json",
		async : false,
		data : {
			fileSize : file.size
		},
		type : "post",
		success : function(data) {
			if (!data.result) {
				$("#addfileName").html('<font color="red">提示：云盘剩余空间不足, 请清理云盘或者选择其他文件！<font>');
				$("#fileToUpload").val("");
			} else {
				$("#addfileName").html("");
			}
		},
		error : function() {
		}
	});
});

/**
 * 上传文件进度条（TODO）
 * 
 * @param evt
 */
function uploadProgress(evt) {
	if (evt.lengthComputable) {
		var percentComplete = Math.round(evt.loaded * 100 / evt.total);
		$("#progressNumber").html(percentComplete.toString() + '%');
		document.getElementById('progressNumber').innerHTML = percentComplete
				.toString()
				+ '%';
	} else {
		$("#progressNumber").html("unable to compute");
	}
}
/**
 * 上传文件完成回调方法
 * 
 * @param evt
 */
function uploadComplete(evt) {
	$("#addfileName").html("");
	var data = eval('(' + evt.target.responseText + ')');
	if (data.result) {
		$('#uploadFileModal').modal('hide');
		refreshMyFileTable();
	} else {
		$("#addfileName").html('<font color="red">错误：上传文件异常！<font>');
	}
}
/**
 * 文件上传失败
 * 
 * @param evt
 */
function uploadFailed(evt) {
	$("#addfileName").html('<font color="red">错误：上传文件出现错误！<font>');
}
/**
 * 文件上传取消
 * 
 * @param evt
 */
function uploadCanceled(evt) {
	$("#addfileName").html('<font color="red">错误：由于网络原因上传文件被终止！<font>');
}

/**
 * 删除文件
 */
$("#delete").click(function() {
	//判断是否已经选择了共享文件
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "OK",
					className: "btn-primary",
				}
			},
			message: "请先选择删除文件对象!",
			size: "small"
		});
		return;
	}	
	bootbox.confirm({
		title : "删除文件",
		message : "确认将执行删除操作？",
		buttons : {
			confirm : {
				label : '确认'
			},
			cancel : {
				label : '取消'
			}
		},
		size: "small",
		callback : function(result) {
			// 点击确认的时候继续下面操作
			if (result) {
				for (var i = 0; i < rows.length; i++) {
					$.ajax({
						url : "../file/deleteFile.do",
						dataType : "json",
						async : false,
						data : {
							fileID : rows[i].fileid
						},
						type : "post",
						success : function(data) {					
								if (data.result) {
									//成功之后需要刷新文件列表
									refreshMyFileTable();
								}
								else{
									bootbox.alert("删除文件: "+rows[i].filename+" 失败！");
								}							
						},
						error : function() {
							message = "删除文件"+rows[i].filename+"失败，请稍后再试！";
						}
					});
				}
			}
		}
	});
});

/**
 * 界面文件下载操作
 */
$("#btn_download").click(function() {
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1) {
		bootbox.alert({
		    title: "提示",
			buttons:{
				ok:{
					label:"OK",
					className: "btn-primary",
				}
			},
			message: "未选择待下载的文件对象，请重新选择！",
			size: "small"
		   });
		return;
	}
	//TODO
	if(rows.length > 1){
		bootbox.alert({
		    title: "提示",
			buttons:{
				ok:{
					label:"OK",
					className: "btn-primary",
				}
			},
			message: "目前仅支持单文件下载，多文件同时下载功能待推出！",
			size: "small"
		   });
		return;
	}
	for (var i = 0; i < rows.length; i++) {
		if(isEmpty(rows[i].filegroupname)){
			bootbox.alert({
				title: "提示",
				buttons:{
					ok:{
						label: "OK",
						className: "btn-primary"
					}
				},
				message: "选择对象为文件夹，无法下载!",
				size: "small"
			});
			break;
		}
		//定义一个form表单
		var form = $("<form>");
		form.attr("style", "display:none");
		form.attr("target", "");
		form.attr("method", "post");
		form.attr("action", "../file/download.do");
		//参数准备
		var filegroupName = $("<input>");
		filegroupName.attr("type", "hidden");
		filegroupName.attr("name", "filegroupName");
		filegroupName.attr("value", rows[i].filegroupname);
		var filePath = $("<input>");
		filePath.attr("type", "hidden");
		filePath.attr("name", "filePath");
		filePath.attr("value", rows[i].filepath);
		var fileName = $("<input>");
		fileName.attr("type", "hidden");
		fileName.attr("name", "fileName");
		fileName.attr("value", rows[i].filename);
		$("body").append(form);
		form.append(filegroupName);
		form.append(filePath);
		form.append(fileName);
		//表单提交
		form.submit();
	}
});

/**
 * 界面刷新按钮操作
 */
$("#refreshTable").click(function() {
	refreshMyFileTable();
});

/**
 * 新建文件夹处理窗口打开事件
 */
$("#creatNewFolder").click(function() {
	$("#newFolderName").val("");
	$("#newFolderNameAlert").html("");
	$('#creatNewFolderModal').modal('show');
});

/**
 * 新建文件夹处理窗口
 */
$("#creatNewFolderSumbit").click(function() {
	var message = newFolderInfoCheck();
	if(!isEmpty(message)){
		$("#newFolderNameAlert").html('<font color="red">' + message + '<font>');
		return;
	}	
	$.ajax({
		url : "../file/createFolder.do",
		dataType : "json",
		async : false,
		data : {
			currentFolderId : $("#currentFolderId").val(), //获取当前目录的ID
			newFolderName : $("#newFolderName").val()
		},
		type : "post",
		success : function(data) {
			if (data.result) {
				/*bootbox.alert({
					title: "提示",
					buttons:{
						ok:{
							label: "OK",
							className: "btn-primary"
						}
					},
					message: "文件夹创建成功！",
					size: "small"
				});*/
				$('#creatNewFolderModal').modal('hide');
				refreshMyFileTable();
			}
		},
		error : function() {
			message = "创建文件夹发生未知错误，请稍后再试！";
		}
	});
});

/**
 * 创建文件夹对应check
 */
function newFolderInfoCheck() {
	$("#newFolderNameAlert").html("");
	var newFolderName = $("#newFolderName").val();
	if(isEmpty(newFolderName)){
		return "文件夹名称为空，请重新输入！";
	}
	if(newFolderName.length > 50){
		return "文件夹名称超过50位，请重新输入！";
	}	
	var rows = $("#tb_fileInfo").bootstrapTable('getData');
	for (var i = 0; i < rows.length; i++) {
		if(isEmpty(rows[i].filesize) && newFolderName == rows[i].filename){
			return "当前目录下该文件夹名称已经存在，请重新命名！";
		}
	}	
	
	return null;
}

/**
 * 文件夹或者文件收藏功能
 */
$("#collect").click(function() {
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title: "提示",
			message: "请选择需要收藏的文件！",
			size: "small"
		});
		return;
	}
	bootbox.confirm({
		title : "收藏操作",
		message : "确认将执行收藏操作？",
		buttons : {
			confirm : {
				label : '确认'
			},
			cancel : {
				label : '取消'
			}
		},
		callback : function(result) {
			// 点击确认的时候继续下面操作
			if (result) {
				var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
				var jsonRows = JSON.stringify(rows);
				$.ajax({
					url : "../file/collectObject.do",
					dataType : "json",
					async : true,
					data : {
						rows : jsonRows
					},
					type : "post",
					success : function(data) {
						if (data.result) {
							bootbox.alert("收藏文件操作成功");
							$('#creatNewFolderModal').modal('hide');
						}
					},
					error : function() {
						message = "收藏文件操作发生未知错误，请稍后再试！";
					}
				});
			}
		}
	});
});

/**
 * 复制文件夹操作
 */
$("#copyTo").click(function() {
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title: "提示",
			buttons:{
				ok:{
					label: "OK",
					className: "btn-primary",
				}
			},
			message: "请选择需要复制的文件对象!",
			size: "small"
		});
		return;
	}
	initTreeValue();
	$("#controlType").val("copy");	
	$("#copyOrMoveToModalTitle").html("复制文件目录选择");
	$("#copyOrMoveToModal").modal("show");
});

/**
 * 移动文件夹操作
 */
$("#moveTo").click(function() {
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "OK",
					className: "btn-primary",
				}
			},
			message: "请选择需要移动的文件对象!",
			size: "small"
		});
		return;
	}
	initTreeValue();
	$("#controlType").val("move");	
	$("#copyOrMoveToModalTitle").html("移动文件目录选择");
	$("#copyOrMoveToModal").modal("show");
});

/**
 * 设置目录的初始化值
 */
function initTreeValue(){
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	var jsonRows = JSON.stringify(rows);
	$.ajax({
		type : "post",
		url : "../file/getFolderIds.do",
		async : true,
		data : {
			rows : jsonRows
		},
		success : function(result) {			
			if(result != null){					
				initTree(eval(result));	
			}
		}
	});
}


function initTree(result){
	//设置目录的初始化值
	$('#folderTree').treeview({
        data: result,
        multiSelect: false,
        levels: 2       
    });
}

/**
 * 移动复制modal点击提交处理
 */

$("#copyOrMoveTo").click(function() {
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	var jsonRows = JSON.stringify(rows);
	var nodes = $('#folderTree').treeview('getSelected');
	if(isEmpty(nodes) || nodes.length < 1){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "OK",
					className: "btn-primary",
				}
			},
			message: "请选择需要操作的目标目录!",
			size: "small"
		});
		return;
	}
	$.ajax({
		type : "post",
		url : "../file/copyOrMoveTo.do",
		async : true,
		data : {
			parentFolderID : nodes[0].id,
			controlType : $("#controlType").val(),
			rows : jsonRows
		},
		success : function(data) {
			if(isEmpty(data.failed) || data.failed.length == 0){
				$("#copyOrMoveToModal").modal("hide");
				refreshMyFileTable();
			}else{
				var message = "";
				for (var i = 0; i < data.failed.length; i++) {
					message = message + "[" + data.failed[i] + "]";
				}
				bootbox.alert("操作处理提示:</br>文件对象{<font style='color:red'>" + message + "</font>}</br>在所选目录中已经存在，处理失败，其他文件对象处理成功！");
			}
		}
	});
}); 

/**
 * 点击共享按钮，弹出共享对象列表对话框
 */
//显示共享对象列表信息
var shareObjectData="";
$("#btn_share").click(function(){	
	//判断是否已经选择了共享文件
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "OK",
					className: "btn-primary",
				}
			},
			message: "请先选择共享文件!",
			size: "small"
		});
		return;
	}	
	$.ajax({
		type : "post",
		url : "../file/queryShareObjectList.do",
		async : false,
		beforeSend : function() {
			// alert("请求前的处理");
		},
		success : function(data) {
			if(!isEmpty(data)){
			var i=0;
			for(;i<data.result.length;i++){
				innerHtml ='<label><input type="checkbox" value="'+data.result[i].shareObjectID+'"/>'+ data.result[i].shareObjectName+'</label></br>';
				$("#shareFileModal form").append(innerHtml);
			}
			shareObjectData = data;
			}else{
				bootbox.alert("没有可供选择的共享对象!");
			}		
		},
		error : function() {
			message = "共享操作发生未知错误，请稍后再试！";
		}
	});
	$("#shareFileModal").modal("show");
	$("#shareFileModal").on('hidden.bs.modal',function(){
		$("#shareFileModal form").empty();
	});
});

//点击共享按钮，处理共享文件操作
$('#shareFileBtn').click(function(){
	var shareObjectIds = new Array();
	i = 0;
	$("#shareFileModal form").find("input[type='checkbox']").each(function(index){
		if ($(this).is(':checked')){
			var object=new Object();
			object.shareObjectID=shareObjectData.result[index].shareObjectID.toString();
			object.shareObjectName=shareObjectData.result[index].shareObjectName.toString();
			shareObjectIds[i] = JSON.stringify(object);
			i++;
		}	
	}); 
	//判断已经勾选了共享对象
	if(isEmpty(shareObjectIds)){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "OK",
					className: "btn-primary",
				}
			},
			message: "请选择共享对象!",
			size: "small"
		});
		return;
	}
	$('#shareFileModal').modal('hide');
	//封装共享文件ids
	var rows=$("#tb_fileInfo").bootstrapTable('getSelections');
    var fileIDs=new Array();
    i=0;
    while(i<rows.length){
    	var object=new Object();
    	object.fileID=rows[i].fileid;
    	object.fileName=rows[i].filename;
    	fileIDs[i]=JSON.stringify(object);
    	i++;
    }
    var shareObjectInfo=new Object();
    shareObjectInfo.shareObjectIDs=shareObjectIds;
    shareObjectInfo.fileIDs=fileIDs;
    var shareObjectJson = JSON.stringify(shareObjectInfo);
	$.ajax({
		url : "../file/shareFile.do",
		dataType : "json",
		data : {
			shareObjectJson : shareObjectJson
		},
		type : "post",		
		async : false,
		beforeSend : function() {
				// alert("请求前的处理");
		},
		success :function(data) {
			if (data.isException) {
				if(!isEmpty(data.success)){
				bootbox.alert("只有以下: "+data.success.toString()+" 成功！");
				}
				else{
					bootbox.alert("文件共享发生异常,共享失败！");
				}
			}
			else{
				if(!isEmpty(data.failue)){
				bootbox.alert(" "+data.failue.toString()+" 失败！");
				}
				else{
					bootbox.alert("文件共享成功！");
				}
			}	
		},
		error : function() {
			message = "文件共享发生未知错误，请重新尝试共享！";
		}
	});
});

/**
 * id和name必须同步化，同时清空或者同时删除
 * 目录点击全部文件和选择我的文件两种情况将清空该集合
 * table的onDblClickRow事件中增加数据
 * 点击目录中其他目录时候两个集合将减少数据或者不变
 */
var myFileFolderIds = new Array();
var myFileFolderNames = new Array();

/**
 * 生成目录结构共通js
 * @param folderIds 传入对应的目录ID
 * @param folderNames 目录显示名称
 */
function getLiInfo(folderIds, folderNames) {
	var levelOne = '<li><a href="#" id ="all" class="active"><b>全部文件</b></a></li>';
	$("#myFileContentOL").html("");
	if (!isEmpty(folderNames) && folderNames.length > 0) {
		for (var i = 0; i < folderNames.length; i++) {
			levelOne = levelOne + '<li><a id="' + folderIds[i]
					+ '" href="#" class="active"><b>' + folderNames[i] + '</b></a></li>';
		}
	}
	// 动态生成
	$("#myFileContentOL").append(levelOne);

	// 绑定事件 (点击目录时候删除目录后面的数据)
	$('#myFileContentOL li').on('click', function(data) {
		var liId = $(this).find("a").attr("id");
		//var liText = $(this).find("a").eq(0).text();
		var filterFolderIds = new Array();
		var filterFolderNames = new Array();		
		if (liId != "all") {			
			for (var int = 0; int < myFileFolderIds.length; int++) {
				filterFolderIds[int] = folderIds[int];
				filterFolderNames[int] = folderNames[int];
				if (liId == filterFolderIds[int]) {
					$("#currentFolderId").val(folderIds[int]);
					break;
				}
			}
			myFileFolderIds = filterFolderIds;
			myFileFolderNames = filterFolderNames;			
			//更新列表信息 
			refreshMyFileTable();
		}else{
			//选择全部文件的情况
			myFileFolderIds = new Array();
			myFileFolderNames  = new Array();
			$("#currentFolderId").val("");
			refreshMyFileTable();
		}
		
		//更新目录结构
		getLiInfo(filterFolderIds, filterFolderNames);
	});
}

/**
 * 更新我的文件列表
 */
function refreshMyFileTable(){
	var currentFolderId = $("#currentFolderId").val();
	if(isEmpty(currentFolderId)){
		$("#tb_fileInfo").bootstrapTable('refresh');
	}else{
		var opt = { 
				url: "../file/myfileTbInit.do",
				silent: true, //刷新事件必须设置
				query:{ 
					fileObjId : currentFolderId
				} 
		}; 
		$("#tb_fileInfo").bootstrapTable('refresh', opt);	
	}
}

/**
 * 点击更新按钮
 */
$('#myFileRefreshTable').click(function(){
	refreshMyFileTable();
});

/**
 * 搜索功能
 */
$("#myFileSearch").bind("input propertychange", function(){
	var opt = { 
			url: "../file/querySearchInfo.do",
			silent: true, //刷新事件必须设置
			query:{ 
				search : $("#myFileSearch").val()
			} 
	};
	
	$("#tb_fileInfo").bootstrapTable('refresh', opt);
});



