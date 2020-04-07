$(function(){
	initDatepicker($('.selectdate'), 2);
    initDatepicker($('.singledate'), 1);
    function initDatepicker(obj, isone) {
        if (isone == 2) {
            var options = {};
            options.timePicker = true;
            options.ranges = {
                'Today': [moment(), moment()],
                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(
                    1, 'month').endOf('month')]
            };
            options.linkedCalendars = false;
            options.autoUpdateInput = false;
            options.timePicker = false;
            options.opens = 'right';
            options.drops = 'down';

            obj.find('span').html(obj.attr('title'));
            var datepicker = obj.daterangepicker(options);
            datepicker.on('apply.daterangepicker', function(ev, picker) {
                $(this).find('span').html(picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD'));
                $(this).find("input[name*='start']").val(picker.startDate.format('YYYY-MM-DD'));
                $(this).find("input[name*='end']").val(picker.endDate.format('YYYY-MM-DD'));
                $(this).find("input[name*='Start']").val(picker.startDate.format('YYYY-MM-DD'));
                $(this).find("input[name*='End']").val(picker.endDate.format('YYYY-MM-DD'));
            });
            datepicker.on('cancel.daterangepicker', function(ev, picker) {
            	$(this).find('span').html(obj.attr('title'));
                $(this).find("input[name*='start']").val("");
                $(this).find("input[name*='end']").val("");
                $(this).find("input[name*='Start']").val("");
                $(this).find("input[name*='End']").val("");
            })
        }
        else {
            obj.daterangepicker({
                singleDatePicker: true,
                startDate: moment(),
                locale:{
                    format: 'YYYY-MM-DD'
                }
            });
        }
    }
})

function changePassword(title) {
	var modal = $('#change-password-modal');
	console.log(modal);
	showFormDialog(title, modal)
}