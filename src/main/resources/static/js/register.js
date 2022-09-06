$(function() {

    $.validator.addMethod('customEmail', function(value, element) {
        return this.optional(element)
            || /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(value);
    })

    const $registerForm = $('#registerForm');

    if($registerForm.length) {

        $registerForm.validate({

            rules: {
                name: {
                    required: true,
                    minlength: 3
                },
                email: {
                    required: true,
                    customEmail: true
                },
                password: {
                    required: true,
                    minlength: 5,
                    maxlength: 15
                },
                confirmPassword: {
                    required: true,
                    equalTo: '#password'
                }
            },
            messages: {
                name: {
                    required: 'Please enter name for the user',
                    minlength: 'Name should be at least 3 characters'
                },
                email: {
                    required: 'Please enter the email',
                    customEmail: 'Please enter valid email address'
                },
                password: {
                    required: 'Please enter password',
                    minlength: 'Password should be at least 5 characters',
                    maxlength: 'Password should be less than 15 characters'
                },
                confirmPassword: {
                    required: 'Please enter confirm password',
                    equalTo: 'Password and confirm password should be same'
                }
            }

        })
    }

})
