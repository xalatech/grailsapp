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
//= require_self

var Ajax;
if (Ajax && (Ajax != null)) {
    Ajax.Responders.register({
        onCreate: function() {
            if($('spinner') && Ajax.activeRequestCount>0)
                Effect.Appear('spinner',{duration:0.5,queue:'end'});
        },
        onComplete: function() {
            if($('spinner') && Ajax.activeRequestCount==0)
                Effect.Fade('spinner',{duration:0.5,queue:'end'});
        }
    });
}