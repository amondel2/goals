// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= require jquery_new
//= require bootstrap
//= require jquery-ui
//= require jstree-min
//= require jquery.contextMenu.min
//= require edittable
//= require_self

$(document).ready(function(){
	function logout(event) {
        event.preventDefault();
        $.ajax({
           url: $("#_logout").attr("href"),
           method: "POST"
        }).done(function(){
            window.location = $("#_afterLogout").attr("href");
        }).fail(function(jqXHR, textStatus, errorThrown){
            alert("Couldn't Logout");
            console.log("Logout error, textStatus: " + textStatus + ", errorThrown: " + errorThrown);
        });
     }

	$("#logout").on('click',logout);
});
