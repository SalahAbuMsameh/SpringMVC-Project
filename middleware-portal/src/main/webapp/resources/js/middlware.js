
/*
 * init accordion 
 */
$(function() {
	
	/**
	 * payload details dialog
	 */
	var dialog = $("#payload-dialog").dialog({
		autoOpen : false,
		height : 500,
		width : 750,
		modal : true,
		buttons : {
			Cancel : function() {
				dialog.dialog("close");
			}
		},
		close : function() {
			dialog.dialog("close");
		}
	});
	
	/**
	 * show payload details dialog
	 */
	$("#payloads-result-table tr").click(function () {
		
		$('#trx-id-dialog-field span').text($(this).closest('tr').children('td:eq(1)').text());
		$('#service-dialog-field span').text($(this).closest('tr').children('td:eq(2)').text());
		$('#payload-type-dialog-field span').text($(this).closest('tr').children('td:eq(3)').text());
		$('#channel-key-dialog-field span').text($(this).closest('tr').children('td:eq(4)').text());
		$('#date-dialog-field span').text($(this).closest('tr').children('td:eq(5)').text());
		
		
		
		dialog.dialog("open");
	});
	
});