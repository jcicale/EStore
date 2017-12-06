$(function(){
	setCookie("userId", 0, 1);
	$.ajaxSetup({
		contentType : "application/json"
	});
	$.widget( "custom.iconselectmenu", $.ui.selectmenu, {
	      _renderItem: function( ul, item ) {
	        var li = $( "<li>" ),
	          wrapper = $( "<div>", { text: item.label } );
	 
	        if ( item.disabled ) {
	          li.addClass( "ui-state-disabled" );
	        }
	 
	        $( "<span>", {
	          style: item.element.attr( "data-style" ),
	          "class": "ui-icon " + item.element.attr( "data-class" )
	        })
	          .appendTo( wrapper );
	 
	        return li.append( wrapper ).appendTo( ul );
	      }
	});
	
	$( "#account-type" )
    .iconselectmenu()
    .iconselectmenu( "menuWidget" )
      .addClass( "ui-menu-icons" );
	
	$("#sign-up-text").on("click", function() {
		$("#login-container").hide();
		$("#sign-up-container").show();
	});
	$("#login-text").on("click", function() {
		$("#sign-up-container").hide();
		$("#login-container").show();
	});
	$("#submit-login").on("click", function(event) {
		event.preventDefault();
		verifyLogin();
	});
	$("#submit-sign-up").on("click", function(event) {
		event.preventDefault();
		//add sign up stuff
	});
});
function verifyLogin(){
	var loginRepresentation = {
			"userName" : $('#user-name').val(),
			"password" : $('#password').val()
	}
	console.log(loginRepresentation);
	showDialogBlockDialog("Proccessing");
	$.post("customer/userLogin", JSON.stringify(loginRepresentation), "json").done(function(data) {
		hideDialogBlockDialog();
		setCookie("userId", data.userId, 1);
		var userId = getCookie("userId");
		console.log(userId);
		if(data.rol == "customer"){
			window.location = 'index.html';
		}else{
			if(data.rol == "representative"){
				window.location = 'representatives.html';
			}else{
				if(data.rol == "partner"){
					window.location = 'partners.html';
				}else{
					showDialog("Bad Login", function() {
						
					});
				}
			}
		}
		console.log(data);
		

	});
}