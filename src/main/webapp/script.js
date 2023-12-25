$(document).ready(function() {
    $('#registrationForm').submit(function(e) {
        e.preventDefault();
        // Perform client-side validation if needed
        
        // Send form data to the servlet using AJAX
        $.ajax({
            type: 'POST',
            url: 'Hello',
            data: $(this).serialize(),
            success: function(response) {
                // Handle success response
                console.log(response);
            },
            error: function(error) {
                // Handle error response
                console.error(error);
            }
        });
    });
});
