$(function() {

});

/**
 * 取消文件共享
 */
$("#btn_cancelMyShared").click(function() {
	bootbox.confirm({
		title : "取消共享",
		message : "确认取消共享文件？",
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
			//alert("ok");
			if (result) {
				var rows = $("#tb_sharedFileInfo").bootstrapTable('getSelections');
				var info="";
				for (var i = 0; i < rows.length; i++) {
					//alert(rows[i].fileName);
					var shareObj = new Object();
					shareObj.fileID=rows[i].fileid;
					shareObj.operator=rows[i].operator;
					shareObj.shareObjectID=rows[i].shareobjectid;
					shareObj.createDate=rows[i].createdate;
					// 将JSON对象转化为JSON字符
					var sharedFileInfoJson = JSON.stringify(shareObj);
					$.ajax({
						url : "../myShared/cancelMyShared.do",
						dataType : "json",
						async : false,
						data : {
							mySharedFileInfo : sharedFileInfoJson
						},
						type : "post",
						success : function(data) {
							if (data.isShareObject==true) {
								if (data.result) {
									//bootbox.alert("取消共享文件: "+rows[i].fileName+" 成功！");
									//成功之后需要刷新文件列表
								}
								else{
									bootbox.alert("取消共享文件: "+rows[i].fileName+" 失败！");
								}							
							}else{
								//bootbox.alert("你不是共享者，不能取消共享文件: "+rows[i].fileName+" !");
								info=info+" "+rows[i].fileName;
							}
						},
						error : function() {
							message = "取消共享文件"+rows[i].fileName+"失败，请稍后再试！";
						}
					});
				}
				if(!isEmpty(info)){
					bootbox.alert("你不是共享者，不能取消共享文件: "+info+" !");
				}
				var opt = { 
						url: "../myShared/queryList.do",
						silent: true, 
						query:{} 
				}; 
				$("#tb_sharedFileInfo").bootstrapTable('refresh', opt); 
			}
		}
	});
});
