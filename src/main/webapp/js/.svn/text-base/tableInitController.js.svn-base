$(function() {
	getMyFileInfo();
	$("#myFileContent").show();
});

$('#myFile').bind('click',function(){
	getMyFileInfo();
	liClickCommon("tb_fileInfo", "myFileContent");
});

$('#myShare').bind('click',function(){
	getSharedFileInfo();
	liClickCommon("tb_sharedFileInfo", "myShareContent");
});

$('#commonShare').bind('click',function(){
	getPublicSharedFileInfo();
	liClickCommon("tb_publicSharedFileInfo", "publicShareContent");
});

$('#groupManage').bind('click',function(){
	getGroupFileInfo();
	liClickCommon("tb_groupFileInfo", "groupManageContent");
});

$('#collection').bind('click',function(){
	getCollectfileInfo();
	liClickCommon("tb_collectfileInfo", "collectionContent");
});

$('#recycle').bind('click',function(){
	getRecyclefileInfo();
	liClickCommon("tb_recyclefileInfo", "recycleContent");
});

function liClickCommon(tableId, contentId){
	$("#"+tableId).bootstrapTable('refresh');
	$("#content-wrapper>div").hide();
	$("#"+contentId).show();
}

/**
 * 我的文件表初始化以及数据获取
 */
function getMyFileInfo() {
	/**清空目录集合数据*/
	myFileFolderIds = new Array();
	myFileFolderNames = new Array();
	$("#currentFolderId").val("");
	getLiInfo("myFileContentOL", "");
	$('#tb_fileInfo').bootstrapTable({
		url : '../file/myfileTbInit.do', // 请求后台的URL（*）			
		method : 'get', // 请求方式（*）
		toolbar : '#toolbarFile', // 工具按钮用哪个容器
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : true, // 是否启用排序
		sortOrder : "asc", // 排序方式
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : false, // 是否显示分页（*）
		onlyInfoPagination:false, //设置为 true 只显示总数据数，而不显示分页
		search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		strictSearch : true,
		showColumns : false, // 是否显示所有的列
		showRefresh : false, // 是否显示刷新按钮
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		uniqueId : "fileid", // 每一行的唯一标识，一般为主键列
		showToggle : false, // 是否显示详细视图和列表视图的切换按钮
		cardView : false, // 是否显示详细视图
		detailView : false, // 是否显示父子表
		queryParams : function getParams(params) {
			var queryParams = params;
			queryParams.fileObjId = $("#currentFolderId").val();			
			return queryParams;
		},
		columns : [ {
			align : 'middle',
			checkbox : true
		},{
			field : 'filename',
			title : '名称',
			align : 'left',
			sortable : true,
			formatter : function(value, row, index) {
				return formatTableFileName(value, row.filesize);
			}
		}, {
			field : 'filesize',
			title : '大小',
			align : 'center',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				return getFormatFileSize(value);
			}
		}, {
			field : 'filetype',
			title : '类型',
			align : 'center',
			valign : 'middle',
			sortable : true
		}, {
			field : 'fileuploadertime',
			title : '上传/创建日期',
			align : 'center',
			valign : 'middle',
			sortable : true
		}, {
			field : 'comment',
			title : '备注',
			align : 'center',
			valign : 'middle',
			sortable : true
		} ],
		silent : true, // 刷新事件必须设置
		onClickRow : function(row, tr, field) {
			//进行你的操作，如弹出新窗口
		},
		onDblClickRow: function(row, tr, field) {
			//进行你的操作，如弹出新窗口
			if(isEmpty(row.filesize)){
				//目录集合数据增加
				myFileFolderIds[myFileFolderIds.length] = row.fileid;
				myFileFolderNames[myFileFolderNames.length] = row.filename;
				getLiInfo(myFileFolderIds, myFileFolderNames);
				$("#currentFolderId").val(row.fileid);
				//查询数据更新
				refreshMyFileTable();
			}
		},
		formatLoadingMessage : function() {
			return "";
		},
		formatNoMatches : function() { // 没有匹配的结果
			return '该目录下无其他文件夹或者文件';
		},
		formatShowingRows: function (pageFrom, pageTo, totalRows) {
			return '总共 ' + totalRows + ' 条记录';
		},
		formatDetailPagination : function (totalRows) {
			return '总共 ' + totalRows + ' 条记录';
		},
		onLoadError : function(data) {
			$('#tb_fileInfo').bootstrapTable('removeAll');
		}
	});
}

/**
 * 我的共享表初始化以及数据获取
 */
function getSharedFileInfo() {
	$('#tb_sharedFileInfo').bootstrapTable({
		url : '../myShared/queryList.do', // 请求后台的URL（*）			
		method : 'get', // 请求方式（*）
		toolbar : '#toolbarShare', // 工具按钮用哪个容器
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : true, // 是否启用排序
		sortOrder : "asc", // 排序方式
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : false, // 是否显示分页（*）
		onlyInfoPagination:false, //设置为 true 只显示总数据数，而不显示分页
		search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		strictSearch : true,
		showColumns : false, // 是否显示所有的列
		showRefresh : false, // 是否显示刷新按钮
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		uniqueId : "index", // 每一行的唯一标识，一般为主键列
		showToggle : false, // 是否显示详细视图和列表视图的切换按钮
		cardView : false, // 是否显示详细视图
		detailView : false, // 是否显示父子表
		columns : [ {
			align : 'middle',
			checkbox : true
		},{
			field : 'fileid',
			title : '文件ID',
			align : 'left',
			valign : 'middle',
			visible : false
		}, {
			field : 'fileName',
			title : '文件名称',
			align : 'left',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				return formatTableFileName(value, row.fileSize);
		    }
		}, {
			field : 'fileSize',
			title : '文件大小',
			align : 'center',
			valign : 'middle',
			sortable : true
		}, {
			field : 'fileType',
			title : '文件类型',
			align : 'center',
			valign : 'middle',
			sortable : true
		}, {
			field : 'operator',
			title : '共享者',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			field : 'shareObjectName',
			title : '共享对象名称',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			field : 'shareobjectid',
			title : '共享对象ID',
			align : 'left',
			valign : 'middle',
			visible : false
		},{
			field : 'createdate',
			title : '共享日期',
			align : 'center',
			valign : 'middle',
			sortable : true
		}],
		silent : true, // 刷新事件必须设置
		onClickRow : function(row, tr, field) {
			//进行你的操作，如弹出新窗口
		},
		onDblClickRow: function(row, tr, field) {
			//进行你的操作，如弹出新窗口
			if(isEmpty(row.fileSize)){
				//alert("打开文件夹!");
				var shareObj = new Object();
				shareObj.operator=row.operator;
				shareObj.shareObjectName=row.shareObjectName;
				shareObj.createdate=row.createdate;
				// 将JSON对象转化为JSON字符
				var sharedFileInfoJson = JSON.stringify(shareObj);			
				var opt = { 
						url: "../myShared/queryNextPageSharedFileInfoByFolderID.do",
						silent: true, 
						query:{ "mySharedFileInfo" : sharedFileInfoJson,
							"fileid" : row.fileid 
						} 
				}; 
				$("#tb_sharedFileInfo").bootstrapTable('refresh', opt); 				
			}
		},
		formatLoadingMessage : function() {
			return "";
		},
		formatNoMatches : function() { // 没有匹配的结果
			return '该目录下无其他文件夹或者文件';
		},
		formatShowingRows: function (pageFrom, pageTo, totalRows) {
			return '总共 ' + totalRows + ' 条记录';
		},
		formatDetailPagination : function (totalRows) {
			return '总共 ' + totalRows + ' 条记录';
		},
		onLoadError : function(data) {
			$('#tb_sharedFileInfo').bootstrapTable('removeAll');
		}
		
	});
}

/**
 * 公共共享表初始化以及数据获取
 */
function getPublicSharedFileInfo() {
	$('#tb_publicSharedFileInfo').bootstrapTable({
		url : '../publicShared/queryList.do', // 请求后台的URL（*）			
		method : 'get', // 请求方式（*）
		toolbar : '#toolbarShare2', // 工具按钮用哪个容器
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : true, // 是否启用排序
		sortOrder : "asc", // 排序方式
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : false, // 是否显示分页（*）
		onlyInfoPagination:false, //设置为 true 只显示总数据数，而不显示分页
		search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		strictSearch : true,
		showColumns : false, // 是否显示所有的列
		showRefresh : false, // 是否显示刷新按钮
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		uniqueId : "index", // 每一行的唯一标识，一般为主键列
		showToggle : false, // 是否显示详细视图和列表视图的切换按钮
		cardView : false, // 是否显示详细视图
		detailView : false, // 是否显示父子表
		columns : [ {
			align : 'middle',
			checkbox : true
		},{
			field : 'fileid',
			title : '文件ID',
			align : 'left',
			valign : 'middle',
			visible : false
		},  {
			field : 'fileName',
			title : '文件名称',
			align : 'left',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				return formatTableFileName(value, row.fileSize);
			}
		}, {
			field : 'fileSize',
			title : '文件大小',
			align : 'center',
			valign : 'middle',
			sortable : true
		}, {
			field : 'fileType',
			title : '文件类型',
			align : 'center',
			valign : 'middle',
			sortable : true
		}, {
			field : 'operator',
			title : '共享者',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			field : 'shareObjectName',
			title : '共享对象名称',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			field : 'createdate',
			title : '共享日期',
			align : 'center',
			valign : 'middle',
			sortable : true
		}],
		silent : true, // 刷新事件必须设置
		onClickRow : function(row, tr, field) {
			//进行你的操作，如弹出新窗口
			if(!isEmpty(row.fileSize)){
				//只有文件才能进行下载和预览，点击文件时显示文件下载和预览按钮
				$("#div_download").show();
				$("#div_preview").show();				
			}else{
				$("#div_download").hide();
				$("#div_preview").hide();	
			};
			$.post('../publicShared/checkIsSharedFile.do', {
				"fileid" : row.fileid
			}, function(e) {
				if (!isEmpty(e.result)) {
					$("#div_cancelShare").show();
				} else {
					$("#div_cancelShare").hide();
				}
			});
		},
		onDblClickRow: function(row, tr, field) {
			//进行你的操作，如弹出新窗口
			if(isEmpty(row.fileSize)){
				//alert("打开文件夹!");
				var shareObj = new Object();
				shareObj.operator=row.operator;
				shareObj.shareObjectName=row.shareObjectName;
				shareObj.createdate=row.createdate;
				// 将JSON对象转化为JSON字符
				var sharedFileInfoJson = JSON.stringify(shareObj);			
				var opt = { 
						url: "../publicShared/queryNextPageSharedFileInfoByFolderID.do",
						silent: true, 
						query:{ "mySharedFileInfo" : sharedFileInfoJson,
							"fileid" : row.fileid 
						} 
				}; 
				$("#tb_publicSharedFileInfo").bootstrapTable('refresh', opt); 				
			}
		},
		formatLoadingMessage : function() {
			return "";
		},
		formatNoMatches : function() { // 没有匹配的结果
			return '该目录下无其他文件夹或者文件';
		},
		formatDetailPagination : function (totalRows) {
			return '总共 ' + totalRows + ' 条记录';
		},
		onLoadError : function(data) {
			$('#tb_publicSharedFileInfo').bootstrapTable('removeAll');
		}
		
	});
}
/**
 * 群组表初始化以及数据获取
 */
function getGroupFileInfo() {
	$('#tb_groupFileInfo').bootstrapTable({
		url : '../group/queryGroup.do', // 请求后台的URL（*）			
		method : 'get', // 请求方式（*）
		toolbar : '#toolbarGroup', // 工具按钮用哪个容器
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : true, // 是否启用排序
		sortOrder : "asc", // 排序方式
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : false, // 是否显示分页（*）
		search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		strictSearch : true,
		showColumns : false, // 是否显示所有的列
		showRefresh : false, // 是否显示刷新按钮
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		uniqueId : "index", // 每一行的唯一标识，一般为主键列
		showToggle : false, // 是否显示详细视图和列表视图的切换按钮
		cardView : false, // 是否显示详细视图
		detailView : false, // 是否显示父子表
		columns : [ {
			align : 'middle',
			checkbox : true
		}, {
			field : 'groupname',
			title : '名称',
			align : 'left',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				return formatTableGroupName(value);
			}
		}, {
			field : 'operator',
			title : '创建者',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			field : 'createdate',
			title : '创建时间',
			align : 'center',
			valign : 'middle',
			sortable : true
		}],
		silent : true, // 刷新事件必须设置
		onClickRow : function(row, tr, field) {
			//进行你的操作，如弹出新窗口
		},
		onDblClickRow: function(row, tr, field) {
			//进行你的操作，如弹出新窗口
			getOperator(row.operator);
			$("#groupInfoModal").modal('show');
			queryAllGroupMember(row);
		},
		formatLoadingMessage : function() {
			return "";
		},
		formatNoMatches : function() { // 没有匹配的结果
			return '无符合条件的记录';
		},
		formatShowingRows: function (pageFrom, pageTo, totalRows) {
			return '总共 ' + totalRows + ' 条记录';
		},
		onLoadError : function(data) {
			$('#tb_groupFileInfo').bootstrapTable('removeAll');
		}
	});
}

/**
 * 收藏文件表初始化以及数据获取
 */
function getCollectfileInfo() {
	$('#tb_collectfileInfo').bootstrapTable({
		url : '../collect/collectTbInit.do', // 请求后台的URL（*）			
		method : 'get', // 请求方式（*）
		toolbar : '#toolbarCollection', // 工具按钮用哪个容器
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : true, // 是否启用排序
		sortOrder : "asc", // 排序方式
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : false, // 是否显示分页（*）
		search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		strictSearch : true,
		showColumns : false, // 是否显示所有的列
		showRefresh : false, // 是否显示刷新按钮
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		uniqueId : "index", // 每一行的唯一标识，一般为主键列
		showToggle : false, // 是否显示详细视图和列表视图的切换按钮
		cardView : false, // 是否显示详细视图
		detailView : false, // 是否显示父子表
		columns : [ {
			align : 'middle',
			checkbox : true
		}, {
			field : 'filename',
			title : '名称',
			align : 'left',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				return formatTableFileName(value, row.filesize);
			}
		}, {
			field : 'filesize',
			title : '大小',
			align : 'center',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				return getFormatFileSize(value);
			}
		}, {
			field : 'filetype',
			title : '类型',
			align : 'center',
			valign : 'middle',
			sortable : true
		}, {
			field : 'fileuploadertime',
			title : '收藏日期',
			align : 'center',
			valign : 'middle',
			sortable : true
		} ],
		silent : true, // 刷新事件必须设置
		onClickRow : function(row, tr, field) {
			//进行你的操作，如弹出新窗口
		},
		onDblClickRow: function(row, tr, field) {
			//进行你的操作，如弹出新窗口
			if(isEmpty(row.filesize)){	
				var opt = { 
						url: "../file/myfileTbInit.do",
						silent: true, 
						query:{ 
							fileObjId : row.fileid
						} 
				}; 
				$("#tb_collectfileInfo").bootstrapTable('refresh', opt); 				
			}
		},
		formatLoadingMessage : function() {
			return "";
		},
		formatNoMatches : function() { // 没有匹配的结果
			return '无符合条件的记录';
		},
		formatShowingRows: function (pageFrom, pageTo, totalRows) {
			return '总共 ' + totalRows + ' 条记录';
		},
		onLoadError : function(data) {
			$('#tb_collectfileInfo').bootstrapTable('removeAll');
		}
	});
}

/**
 * 回收文件表初始化以及数据获取
 */
function getRecyclefileInfo() {
	$('#tb_recyclefileInfo').bootstrapTable({
		url : '../recycle/recycleTbInit.do', // 请求后台的URL（*）			
		method : 'get', // 请求方式（*）
		toolbar : '#toolbarRecycle', // 工具按钮用哪个容器
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : true, // 是否启用排序
		sortOrder : "asc", // 排序方式
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : false, // 是否显示分页（*）
		search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		strictSearch : true,
		showColumns : false, // 是否显示所有的列
		showRefresh : false, // 是否显示刷新按钮
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		uniqueId : "index", // 每一行的唯一标识，一般为主键列
		showToggle : false, // 是否显示详细视图和列表视图的切换按钮
		cardView : false, // 是否显示详细视图
		detailView : false, // 是否显示父子表
		columns : [ {
			align : 'middle',
			checkbox : true
		}, {
			field : 'filename',
			title : '名称',
			align : 'left',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				return formatTableFileName(value, row.filesize);
			}
		}, {
			field : 'filesize',
			title : '大小',
			align : 'center',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				return getFormatFileSize(value);
			}
		}, {
			field : 'filetype',
			title : '类型',
			align : 'center',
			valign : 'middle',
			sortable : true
		}, {
			field : 'fileuploadertime',
			title : '删除日期',
			align : 'center',
			valign : 'middle',
			sortable : true
		}],
		silent : true, // 刷新事件必须设置
		onClickRow : function(row, tr, field) {
			//进行你的操作，如弹出新窗口
		},
		formatLoadingMessage : function() {
			return "";
		},
		formatNoMatches : function() { // 没有匹配的结果
			return '无符合条件的记录';
		},
		formatShowingRows: function (pageFrom, pageTo, totalRows) {
			return '总共 ' + totalRows + ' 条记录';
		},
		onLoadError : function(data) {
			$('#tb_recyclefileInfo').bootstrapTable('removeAll');
		}
	});
}
