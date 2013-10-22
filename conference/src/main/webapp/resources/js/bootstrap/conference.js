
$(function(){
	// disabling dates
	var nowTemp = new Date();
	var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

	var dFrom = $('#dateFrom').datepicker({
		format: 'yyyy-mm-dd',
		onRender: function(date) {
	    	return date.valueOf() < now.valueOf() ? 'disabled' : '';
	    }
	}).on('changeDate', function(ev) {
		if (ev.date.valueOf() > dTill.date.valueOf()) {
	    	var newDate = new Date(ev.date)
	        newDate.setDate(newDate.getDate() + 1);
	        dTill.setValue(newDate);
	    }
	     
	    dFrom.hide();
	    $('#dateTill')[0].focus();
	}).data('datepicker');


	var dTill = $('#dateTill').datepicker({
		format: 'yyyy-mm-dd',
		onRender: function(date) {
	    	return date.valueOf() <= dFrom.date.valueOf() ? 'disabled' : '';
	    }
	}).on('changeDate', function(ev) {
		dTill.hide();
	}).data('datepicker');

	var closeLoginDialog = function () {
		alert('closing...');
	}
});
