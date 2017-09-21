$(function() {

});

/**
 * 恢复文件功能
 */
$("#recycleBtn_restore").click(function() {
	var rows = $("#tb_recyclefileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert("请选择需要恢复的对象");
		return;
	}
	bootbox.confirm({
		title : "恢复文件",
		message : "确认将执行恢复操作？",
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
				var rows = $("#tb_recyclefileInfo").bootstrapTable('getSelections');
				var jsonRows = JSON.stringify(rows);
				$.ajax({
					url : "../recycle/restoreFile.do",
					dataType : "json",
					async : true,
					data : {
						rows : jsonRows
					},
					type : "post",
					success : function(data) {
						if (data.result) {
							bootbox.alert("恢复文件操作成功");
						}
					},
					error : function() {
						message = "恢复文件操作发生未知错误，请稍后再试！";
					}
				});
			}
		}
	});
});

/**
 * 彻底删除文件
 */
$("#recycleBtn_destory").click(function() {
	//判断是否已经选择了共享文件
	var rows = $("#tb_recyclefileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "OK",
					className: "btn-primary",
				}
			},
			message: "请先选择彻底删除对象!",
			size: "small"
		});
		return;
	}	
	bootbox.confirm({
		title : "删除文件",
		message : "确认将执行彻底删除操作？",
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
				var info="";
				for (var i = 0; i < rows.length; i++) {
					$.ajax({
						url : "../recycle/absolutelyDeleteFile.do",
						dataType : "json",
						async : false,
						data : {
							fileID : rows[i].fileid
						},
						type : "post",
						success : function(data) {					
							if (data.result) {
								//删除文件成功
							}
							else{
								//删除文件失败
								info=info+" "+rows[i].fileName;
							}							
						},
						error : function() {
							message = "删除文件"+rows[i].filename+"失败，请稍后再试！";
						}
					});
				}
				if(!isEmpty(info)){
					bootbox.alert("删除文件: "+info+" 失败!");
				}else{
					bootbox.alert("删除文件成功!");
				}
				var opt = { 
						url: "../recycle/absolutelyDeleteFile.do",
						silent: true, 
						query:{} 
				}; 
				$("#tb_recyclefileInfo").bootstrapTable('refresh', opt); 
			}
		}
	});
});