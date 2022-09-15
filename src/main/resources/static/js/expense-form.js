let validNumber = new RegExp(/^(?!0)\d*\.?\d{0,2}$/)
let lastValid = $("#amount").val();
const validateNumber = function(element) {
    if(validNumber.test(element.value)) {
        lastValid = element.value;
    } else {
        element.value = lastValid;
    }
}

let validDate = new RegExp(/^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[13-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$/)
let lastValidDate = $("#createdAt").val();
const validateDate = function(element) {
    if(validDate.test(element.value)) {
        lastValidDate = element.value;
    } else {
        element.value = lastValidDate;
    }
}



$(function() {
    $('#createdAt').datepicker({
        dateFormat: "dd/mm/yy",
        changeMonth: true,
        changeYear: true,
        maxDate: new Date()
    });

    const $expenseForm = $("#expenseform");

    if($expenseForm.length) {
        $expenseForm.validate({

            rules: {
                name: {
                    required: true,
                    minlength: 3
                },
                amount: {
                    required: true,
                },
                dateString: {
                    required: true
                }
            },

            messages : {
                name:{
                    required: 'Please enter the expense name',
                    minlength: 'Expense name should have minimum 3 characters'
                },
                amount:{
                    required: 'Please enter the expense amount',
                },
                dateString:{
                    required: 'Please enter the expense date',
                }
            },
            errorElement: 'em',
            errorPlacement: function (error,element) {
                error.addClass('help-back');
                error.insertAfter(element);
            }
        })
    }

})
