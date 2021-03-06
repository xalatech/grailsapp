// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-3.3.1.min
//= require bootstrap
//= require popper.min
//= require paper-dashboard/plugins/perfect-scrollbar.jquery.min.js
//= require paper-dashboard/plugins/bootstrap-notify.js
//= require paper-dashboard/paper-dashboard.min.js
//= require datepicker/js/bootstrap-datepicker.min.js
//= require datepicker/locales/bootstrap-datepicker.no.min.js
//= require_self

if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(function() {
            $(this).fadeIn();
        }).ajaxStop(function() {
            $(this).fadeOut();
        });
    })(jQuery);
}

function remoteFunction(controller, action, params, onComplete) {
    $.ajax({
        type: "POST",
        url: controller + "/" + action,
        data: params,
        success: onComplete,
        dataType: 'json'
    });
};



