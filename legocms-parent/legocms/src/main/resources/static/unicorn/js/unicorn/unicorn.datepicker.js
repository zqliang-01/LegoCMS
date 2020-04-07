function Bind_Date(obj, opens, isone) {
    //双控件
    if (isone == 2) {
        var DateOpetion = {
            startDate: moment().subtract('days', 0),
            endDate: moment(),
            opens: opens,
            ranges: {
                '今天': [moment(), moment()],
                '昨天': [moment().subtract('days', 1), moment().subtract('days', 1)],
                '最近30天': [moment().subtract('days', 29), moment()],
                '这个月': [moment().startOf('month'), moment().endOf('month')],
                '上个月': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')],
        		'无限大': [moment(), '9999-12-30']
            }
        };

        obj.daterangepicker(DateOpetion).on('apply.daterangepicker', function(ev, picker) {
            $(this).find('span').html(picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD'));
            $(this).find("input[name*='start']").val(picker.startDate.format('YYYY-MM-DD'));
            $(this).find("input[name*='end']").val(picker.endDate.format('YYYY-MM-DD'));
            $(this).find("input[name*='Start']").val(picker.startDate.format('YYYY-MM-DD'));
            $(this).find("input[name*='End']").val(picker.endDate.format('YYYY-MM-DD'));
        });

    } else {
        //单控件
        obj.daterangepicker({
            startDate: moment(),
            singleDatePicker: true,
            showDropdowns: true,
            opens: opens,
            format: 'YYYY-MM-DD'
        });
    }
}