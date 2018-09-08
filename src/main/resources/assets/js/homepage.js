'use strict';

function showSignUpForm() {
    $('#register-button').hide();
    $('#login').hide();
    $('#register').show(10);
}

function showLogin() {
    $('#register-button').show();
    $('#register').hide();
    $('#login').show();
}

/*$('#register-form').onsubmit = signUp();
$('#login-form').onsubmit = signIn();*/

function register() {
    var lat = 0;
    var lon = 0;
    navigator.geolocation.getCurrentPosition((loc) => {
        navigator.geolocation.getCurrentPosition(showPosition);
        function showPosition(position) {
            x.innerHTML = "Latitude: " + position.coords.latitude +
                "<br>Longitude: " + position.coords.longitude;

            lat = position.coords.latitude
            lon = position.coords.longitude
        }
        //lat = position.coords.latitude
        //lon = position.coords.longitude;
        console.log('The location in lat lon format is: [', loc.coords.latitude, ',', loc.coords.longitude, ']');
    })


    var data = $('#registration-form').append("latitude", lat).append("longitude", lon);
    $.ajax({
        type: "POST",
        url: `${baseUrl}/user/register`,
        data: data.serialize(),
        dataType: "form/url-encoded",
        success: function success(json) {
            console.log("success.");
            console
            if (json != null) {
                console.log(json);
                localStorage.setItem('user', JSON.stringify(json));
                window.location = "/langingpage.html";
            } else {
                $('#register-form-error').html("Something is not working");
            }
        },
        error: function error(json, ajaxOptions, thrownError) {
            $('#register-form-error').html(JSON.parse(json.responseText).message);
            if (json != null) {
                console.log(json);
                localStorage.setItem('user', JSON.stringify(json.responseText));
                window.location = "/langingpage.html";
            } else {
                $('#register-form-error').html("Something is not working");
            }
            console.log('Error in sign up ' + json.responseText);
        }
    });
}

// function signIn() {
//     console.log("sign in");
//     $.ajax({
//         type: "POST",
//         url: baseUrl + '/candidate/signin',
//         data: $('#login-form').serialize(),
//         dataType: "json",
//         success: function success(json) {
//             console.log("success.");
//             if (json != null) {
//                 console.log(json);
//                 localStorage.setItem('user', JSON.stringify(json));
//                 window.location = "/landingpage.html";
//             } else {
//                 $('#login-form-error').html("Something broke.");
//             }
//         },
//         error: function error(xhr, ajaxOptions, thrownError) {
//             $('#login-form-error').html(JSON.parse(xhr.responseText).message);
//             console.log('Error in sign in ' + xhr.responseText);
//         }
//     });
// }