$(function() {

});

/**
 * 取消收藏功能
 */
$("#collectionBtn_cancel").click(function() {
	var rows = $("#tb_collectfileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert("请选择需要取消收藏的对象");
		return;
	}
	bootbox.confirm({
		title : "取消收藏",
		message : "确认将执行取消收藏操作？",
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
				var rows = $("#tb_collectfileInfo").bootstrapTable('getSelections');
				var jsonRows = JSON.stringify(rows);
				$.ajax({
					url : "../collect/cancelMyCollection.do",
					dataType : "json",
					async : true,
					data : {
						rows : jsonRows
					},
					type : "post",
					success : function(data) {
						if (data.result) {
							bootbox.alert("取消收藏操作成功");
						}
					},
					error : function() {
						message = "取消收藏操作发生未知错误，请稍后再试！";
					}
				});
			}
		}
	});
});
