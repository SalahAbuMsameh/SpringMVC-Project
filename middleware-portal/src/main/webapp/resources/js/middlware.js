
/*
 * init accordion 
 */
$(function() {
	
	/**
	 * payload details dialog
	 */
	var dialog = $("#payload-dialog").dialog({
		autoOpen : false,
		height : "auto",
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
		
		//populate audit payload master details
		$('#trx-id-dialog-field span').text($(this).closest('tr').children('td:eq(1)').text());
		$('#service-dialog-field span').text($("#service option[value='" + $(this).closest('tr').children('td:eq(2)').text() + "']").text());
		$('#channel-key-dialog-field span').text($(this).closest('tr').children('td:eq(4)').text());
		$('#date-dialog-field span').text($(this).closest('tr').children('td:eq(5)').text());
		
		//populate payload type
		var payloadType = $(this).closest('tr').children('td:eq(3)').text();
		
		if(payloadType == 1) {
			$('#payload-type-dialog-field span').text('Request');
		}
		else if(payloadType == 2) {
			$('#payload-type-dialog-field span').text('Response');
		}
		else {
			$('#payload-type-dialog-field span').text('Fault');
		}
		
		//get payload content
		$.get('/middleware-portal/audit-finder/getPayload/' + $(this).closest('tr').children('td:eq(0)').text(), function(data) {
			console.log(data);
			$('#payload-content code').text(data);
		});
		
		dialog.dialog("open");
	});
	
});