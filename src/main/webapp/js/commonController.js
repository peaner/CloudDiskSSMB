$(function() {
});

/**
 * 判断对象是否为空的方法
 * 
 * @param data
 * @returns {Boolean}
 */
function isEmpty(data) {
	if (data != null && data != undefined && data != "") {
		return false;
	}

	return true;
}

/**
 * 请求模拟休眠方法
 * 
 * @param numberMillis
 */
function sleep(numberMillis) {
	var now = new Date();
	var exitTime = now.getTime() + numberMillis;
	while (true) {
		now = new Date();
		if (now.getTime() > exitTime)
			return;
	}
}

/**
 * 数据库文件大小显示到页面的转换 如果文件大小大于1024字节X1024字节，则显示文件大小单位为MB，否则为KB
 * 
 * @param file
 */
function getFormatFileSize(oldFileSize) {
	var fileSize = "";
	if (!isEmpty(oldFileSize)) {
		if (oldFileSize > 1024 * 1024) {
			fileSize = Math.round(oldFileSize * 100 / (1024 * 1024)) / 100;
			if (fileSize > 500) {
				bootbox.alert('错误，文件超过500M，禁止上传！');
				return;
			}
			fileSize = fileSize.toString() + 'MB';
		} else {
			fileSize = (Math.round(oldFileSize * 100 / 1024) / 100).toString()
					+ 'KB';
		}
	}

	return fileSize;
}

/**
 * 表格数据中文件名称格式化
 * @param value
 * @param formatCondition
 * @returns {String}
 */
function formatTableFileName(value, formatCondition) {
	if (!isEmpty(formatCondition)) {
		return '<span class="fa fa-file" style="color: rgb(255, 180, 60)"></span>'
				+ '<font size="2" style="margin-left:5px" >'
				+ value + "</font>";
	} else {
		return '<span class="fa fa-folder" style="color: rgb(255, 180, 60)"></span>' 
				+ '<a href="#" title="双击打开目录"><font size="2" style="margin-left:5px">'
				+ value + "</font></a>";
	}
}

/**
 * 表格数据中文件名称格式化
 * @param value
 * @param formatCondition
 * @returns {String}
 */
function formatTableGroupName(value){
	return '<span style="color: rgb(255, 180, 60)"></span>' 
	+ '<a href="#" title="双击查看群组信息"><font size="2" style="margin-left:5px">'
	+ value + "</font></a>";
}

/**
 * 表格数据中文件名称格式化
 * @param value
 * @param formatCondition
 * @returns {String}
 */
function refreshTable(tableId) {
	$("#"+tableId).bootstrapTable('refresh');
}
