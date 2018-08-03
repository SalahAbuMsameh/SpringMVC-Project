
/*
 * init accordion 
 */
$(function() {
	
	//check if the search-date radio is check at the page load
	if($('#radio-search-date').is(':checked')) {
		$('#af-date').prop('disabled', false);
        $('#af-from-date').prop('disabled', true);
        $('#af-to-date').prop('disabled', true);
	}
	
	$('input:radio[name=dateType]').change(function() {
        		
		if (this.value == 'search-date') {
			
			$('#af-date').prop('disabled', false);
            $('#af-from-date').prop('disabled', true);
            $('#af-to-date').prop('disabled', true);
        }
        else if (this.value == 'search-interval') {
        	
        	$('#af-date').prop('disabled', true);
            $('#af-from-date').prop('disabled', false);
            $('#af-to-date').prop('disabled', false);
        }
    });
	
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
			$('#payload-content code').text(data);
		});
		
		//highlight code
		$('#payload-content code').each(function(i, block) {
			hljs.highlightBlock(block);
		});
		
		dialog.dialog("open");
	});
	
});