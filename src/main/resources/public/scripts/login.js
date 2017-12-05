$(function(){
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
		//add login stuff
	});
	$("#submit-sign-up").on("click", function(event) {
		event.preventDefault();
		//add sign up stuff
	});
});