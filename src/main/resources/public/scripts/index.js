<<<<<<< HEAD
var list;
var form;
var customerId = 1;
var billingAddressId = 2;
var shippingAddressId = 1;
$(function() {
	var userId = getCookie("userId");
	if(userId == 0 || userId == null  || userId == 'undefined'){
		window.location = 'login.html';
	}
	customerId = userId;
	list = $('#list');
	form = $('#form');
	$.ajaxSetup({
		contentType : "application/json"
	});
	refreshContactsList();
	$("#product-search").on("keyup", function(e) {
		e.preventDefault;
		if (e.keyCode == 13) {
			var searchTerm = $("#product-search").val();
			var url = "inventory/product?title=" + searchTerm;
			showDialogBlockDialog("Loading Data from Server");
			$.getJSON(url).done(function(data) {
				drawSearchResults(data);
			});
		}
	});
});
function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
function loadTestValues(){
	$("#credit-card-number").val("1234567890123456");
	$("#name-on-card").val("John Jones");
	$("#security-code").val("111");
	$("#expiration-date").val("12/10");
}
function showConfirmDialog(message, callback) {
	$("#dialogMessageText").html(message);
	$(function() {
		$("#dialog-message").dialog({
			modal : true,
			buttons : {
				"Delete Contact" : function() {
					$(this).dialog("close");
					callback();
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});
	});
}
function showDialog(message, callback) {
	$("#dialogMessageText").html(message);
	$(function() {
		$("#dialog-message").dialog({
			modal : true,
			buttons : {
				Ok : function() {
					$(this).dialog("close");
					callback();
				}
			}
		});
	});
}
function showDialogBlockDialog(message) {
	$("#dialogMessageTextLoading").html(message);
	$(function() {
		$("#dialog-loading").dialog({
			modal : true
		});
	});
}
function hideDialogBlockDialog() {
	$("#dialog-loading").dialog("close");

}
function cancelSave() {
	list.show();
	form.hide();
}
function refreshContactsList() {
	list.show();
	form.hide();
	var url = "inventory";
	showDialogBlockDialog("Loading Data from Server");
	$.getJSON(url).done(function(data) {
		drawSearchResults(data);
	});

}
$(function() {
	var availableProducts = [ "FitBit", "Bluetooth Headphones", "Laptop Bag", "The Odyssey" ];
	$("#product-search").autocomplete({
		source : availableProducts
	});
});

function drawSearchResults(data) {
	console.log(data);
	$("#items").empty();
	for ( var item in data) {
		var inventory = data[item];
		drawInventory(inventory, $("#items"), true)
	}
	hideDialogBlockDialog();
}

function drawInventory(inventory, parent, isList) {
	var div = $("<div class='item'>");
	var img = $("<img src = 'http://placebear.com/150/150'>")
	var p0 = $("<p class='product-name'>").html(inventory.product.title);
	var p1 = $("<p>").html('Seller: ' + inventory.partner.name);
	var p2 = $("<p>").html('In stock: ' + inventory.quantity);
	img.appendTo(div);
	p0.appendTo(div);
	p1.appendTo(div);
	p2.appendTo(div);
	var rel = "";
	if (isList) {
		var p3 = $("<p>").html('Price: $' + inventory.price);
		p3.appendTo(div);
		rel = "loadReviewOrder('" + lookup(inventory.links, "rel", "self").href + "','" + lookup(inventory.links, "rel", "save").href + "'," + inventory.inventoryId + ")";
		var button = $('<button class="ui-button ui-widget ui-corner-all" onclick="' + rel + '">').html("Add to Cart");
		p3.appendTo(div);
		button.appendTo(div);
	} else {
		var p3 = $("<p id='price'>").html('Price: $' + inventory.price);
		p3.appendTo(div);
	}
	div.appendTo(parent);
	console.log(inventory);
}

function lookup(array, prop, value) {
	for (var i = 0, len = array.length; i < len; i++)
		if (array[i] && array[i][prop] === value)
			return array[i];
}

$.validate({
	form : '#place-order-form',
	onError : function($form) {
		alert('Validation of form ' + $form.attr('id') + ' failed!');
	},
	onSuccess : function($form) {
		alert('The form ' + $form.attr('id') + ' is valid!');
		return false; // Will stop the submission of the form
	},
});

function loadReviewOrder(selfLink, placeOrderLink, inventoryId) {
	loadInventoryDetailsToReviewOrder(selfLink);
	$('#place-order-form').get(0).reset();
	$(".fields").hide();
	$(function() {
		$("#dialogPlaceOrder").dialog({
			modal : true,
			buttons : {
				"Place Order" : function() {
					$("#place-order-form").submit();
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			},
			width : "60%",
			maxWidth : "500px",
			position : {
				my : 'top',
				at : 'top+50'
			},
		});
		$.validate({
			form : '#place-order-form',
			onError : function($form) {
				alert('Some fields are missing or incorrect. Please fix the errors and try again.');
			},
			onSuccess : function($form) {
				placeOrder(placeOrderLink, inventoryId);
				$("#dialogPlaceOrder").dialog("close");
				return false;
			},
		});
		$("#place-order-form").on('submit', function(e) {
			e.preventDefault();
			placeOrder(placeOrderLink, inventoryId);
			$("#dialogPlaceOrder").dialog("close");
		})
		$("#credit-card-radio").checkboxradio({
			icon : false
		});
		$("#paypal-radio").checkboxradio({
			icon : false
		});
		$("#shipping-radio").checkboxradio({
			icon: false
		});
		$("#pickup-radio").checkboxradio({
			icon: false
		});
		$('#select-quantity').on('change keyup paste', function() {
			var quantity = $(this).val();
			var price = document.querySelector('p#price').innerHTML.replace(/\D/g, '');
			console.log(price);
			document.querySelector('#subtotal').innerHTML = "$" + quantity * price;
		});
		$('input[name="payment-radio"]').click(function() {
			var inputValue = $(this).val();
			var targetFields = $("." + inputValue);
			$(".fields").not(targetFields).hide();
			$(targetFields).show();
			 loadTestValues();
		});

	});
}

function loadInventoryDetailsToReviewOrder(selfLink) {
	// showDialogBlockDialog("Loading Data from Server"); //commented out for
	// now due to jquery ui bug
	$.getJSON(selfLink).done(function(data) {
		$('#inventoryDetails').empty();
		drawInventory(data, $('#inventoryDetails'), false);
		// hideDialogBlockDialog();
	});
}
function loadValuesIntoOrder(inventoryId) {
	var order = {
		"customerId" : 1,
		"billingAddressId" : 2
	}
	var shipmentType = $('input[name="order-type-radio"]:checked').val();
	if (shipmentType === "shipping-radio") {
		order.orderDetails = [ {
			"inventoryId" : inventoryId,
			"quantity" : $('#select-quantity').val(),
			"addressId" : shippingAddressId

		} ];
	} else if (shipmentType === "pickup-radio") {
		order.orderDetails = [ {
				"inventoryId": inventoryId,
				"quantity": $('#select-quantity').val(),			
		} ];
	}
	var paymentType = $('input[name="payment-radio"]:checked').val();
	if (paymentType === "credit-card-radio") {
		order.paymentMethod = [ {
			"subTotal" : document.querySelector("#subtotal").innerHTML.replace(/\D/g, ''),
			"creditCardNumber" : $("#credit-card-number").val(),
			"nameOnCard" : $("#name-on-card").val(),
			"securityCode" : $("#security-code").val(),
			"validDate" : $("#expiration-date").val()
		} ]
	} else if (paymentType === "paypal-radio") {
		order.paymentMethod = [ {
			"subTotal" : document.querySelector("#subtotal").innerHTML.replace(/\D/g, ''),
			"transactionId" : $("#transaction-id").val(),
			"accountEmail" : $("#account-email").val()
		} ]
	}
	return order;
}
function placeOrder(submitURL, inventoryId) {
	var order = loadValuesIntoOrder(inventoryId);
	console.log(order);
	$.post("order", JSON.stringify(order), "json").done(function(data) {
		hideDialogBlockDialog();
		showDialog("Order Placed", function() {
			window.location = 'customers.html'
			//refreshContactsList();
		});

	});
=======
var list;
var form;
var customerId = 1;
var billingAddressId = 2;
var shippingAddressId = 1;
$(function() {
	list = $('#list');
	form = $('#form');
	$.ajaxSetup({
		contentType : "application/json"
	});
	refreshContactsList();
	$("#product-search").on("keyup", function(e) {
		e.preventDefault;
		if (e.keyCode == 13) {
			var searchTerm = $("#product-search").val();
			var url = "inventory/product?title=" + searchTerm;
			showDialogBlockDialog("Loading Data from Server");
			$.getJSON(url).done(function(data) {
				drawSearchResults(data);
			});
		}
	});
});
function loadTestValues(){
	$("#credit-card-number").val("1234567890123456");
	$("#name-on-card").val("John Jones");
	$("#security-code").val("111");
	$("#expiration-date").val("12/10");
}
function showConfirmDialog(message, callback) {
	$("#dialogMessageText").html(message);
	$(function() {
		$("#dialog-message").dialog({
			modal : true,
			buttons : {
				"Delete Contact" : function() {
					$(this).dialog("close");
					callback();
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});
	});
}
function showDialog(message, callback) {
	$("#dialogMessageText").html(message);
	$(function() {
		$("#dialog-message").dialog({
			modal : true,
			buttons : {
				Ok : function() {
					$(this).dialog("close");
					callback();
				}
			}
		});
	});
}
function showDialogBlockDialog(message) {
	$("#dialogMessageTextLoading").html(message);
	$(function() {
		$("#dialog-loading").dialog({
			modal : true
		});
	});
}
function hideDialogBlockDialog() {
	$("#dialog-loading").dialog("close");

}
function cancelSave() {
	list.show();
	form.hide();
}
function refreshContactsList() {
	list.show();
	form.hide();
	var url = "inventory";
	showDialogBlockDialog("Loading Data from Server");
	$.getJSON(url).done(function(data) {
		drawSearchResults(data);
	});

}
$(function() {
	var availableProducts = [ "FitBit", "Bluetooth Headphones", "Laptop Bag", "The Odyssey" ];
	$("#product-search").autocomplete({
		source : availableProducts
	});
});

function drawSearchResults(data) {
	console.log(data);
	$("#items").empty();
	for ( var item in data) {
		var inventory = data[item];
		drawInventory(inventory, $("#items"), true)
	}
	hideDialogBlockDialog();
}

function drawInventory(inventory, parent, isList) {
	var div = $("<div class='item'>");
	var img = $("<img src = 'http://placebear.com/150/150'>")
	var p0 = $("<p class='product-name'>").html(inventory.product.title);
	var p1 = $("<p>").html('Seller: ' + inventory.partner.name);
	var p2 = $("<p>").html('In stock: ' + inventory.quantity);
	img.appendTo(div);
	p0.appendTo(div);
	p1.appendTo(div);
	p2.appendTo(div);
	var rel = "";
	if (isList) {
		var p3 = $("<p>").html('Price: $' + inventory.price);
		p3.appendTo(div);
		rel = "loadReviewOrder('" + lookup(inventory.links, "rel", "self").href + "','" + lookup(inventory.links, "rel", "save").href + "'," + inventory.inventoryId + ")";
		var button = $('<button class="ui-button ui-widget ui-corner-all" onclick="' + rel + '">').html("Add to Cart");
		p3.appendTo(div);
		button.appendTo(div);
	} else {
		var p3 = $("<p id='price'>").html('Price: $' + inventory.price);
		p3.appendTo(div);
	}
	div.appendTo(parent);
	console.log(inventory);
}

function lookup(array, prop, value) {
	for (var i = 0, len = array.length; i < len; i++)
		if (array[i] && array[i][prop] === value)
			return array[i];
}

$.validate({
	form : '#place-order-form',
	onError : function($form) {
		alert('Validation of form ' + $form.attr('id') + ' failed!');
	},
	onSuccess : function($form) {
		alert('The form ' + $form.attr('id') + ' is valid!');
		return false; // Will stop the submission of the form
	},
});

function loadReviewOrder(selfLink, placeOrderLink, inventoryId) {
	loadInventoryDetailsToReviewOrder(selfLink);
	$('#place-order-form').get(0).reset();
	$(".fields").hide();
	$(function() {
		$("#dialogPlaceOrder").dialog({
			modal : true,
			buttons : {
				"Place Order" : function() {
					$("#place-order-form").submit();
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			},
			width : "60%",
			maxWidth : "500px",
			position : {
				my : 'top',
				at : 'top+50'
			},
		});
		$.validate({
			form : '#place-order-form',
			onError : function($form) {
				alert('Some fields are missing or incorrect. Please fix the errors and try again.');
			},
			onSuccess : function($form) {
				placeOrder(placeOrderLink, inventoryId);
				$("#dialogPlaceOrder").dialog("close");
				return false;
			},
		});
		$("#place-order-form").on('submit', function(e) {
			e.preventDefault();
			placeOrder(placeOrderLink, inventoryId);
			$("#dialogPlaceOrder").dialog("close");
		})
		$("#credit-card-radio").checkboxradio({
			icon : false
		});
		$("#paypal-radio").checkboxradio({
			icon : false
		});
		$("#shipping-radio").checkboxradio({
			icon: false
		});
		$("#pickup-radio").checkboxradio({
			icon: false
		});
		$('#select-quantity').on('change keyup paste', function() {
			var quantity = $(this).val();
			var price = document.querySelector('p#price').innerHTML.replace(/\D/g, '');
			console.log(price);
			document.querySelector('#subtotal').innerHTML = "$" + quantity * price;
		});
		$('input[name="payment-radio"]').click(function() {
			var inputValue = $(this).val();
			var targetFields = $("." + inputValue);
			$(".fields").not(targetFields).hide();
			$(targetFields).show();
			 loadTestValues();
		});

	});
}

function loadInventoryDetailsToReviewOrder(selfLink) {
	// showDialogBlockDialog("Loading Data from Server"); //commented out for
	// now due to jquery ui bug
	$.getJSON(selfLink).done(function(data) {
		$('#inventoryDetails').empty();
		drawInventory(data, $('#inventoryDetails'), false);
		// hideDialogBlockDialog();
	});
}
function loadValuesIntoOrder(inventoryId) {
	var order = {
		"customerId" : 1,
		"billingAddressId" : 2
	}
	var shipmentType = $('input[name="order-type-radio"]:checked').val();
	if (shipmentType === "shipping-radio") {
		order.orderDetails = [ {
			"inventoryId" : inventoryId,
			"quantity" : $('#select-quantity').val(),
			"addressId" : shippingAddressId

		} ];
	} else if (shipmentType === "pickup-radio") {
		order.orderDetails = [ {
				"inventoryId": inventoryId,
				"quantity": $('#select-quantity').val(),			
		} ];
	}
	var paymentType = $('input[name="payment-radio"]:checked').val();
	if (paymentType === "credit-card-radio") {
		order.paymentMethod = [ {
			"subTotal" : document.querySelector("#subtotal").innerHTML.replace(/\D/g, ''),
			"creditCardNumber" : $("#credit-card-number").val(),
			"nameOnCard" : $("#name-on-card").val(),
			"securityCode" : $("#security-code").val(),
			"validDate" : $("#expiration-date").val()
		} ]
	} else if (paymentType === "paypal-radio") {
		order.paymentMethod = [ {
			"subTotal" : document.querySelector("#subtotal").innerHTML.replace(/\D/g, ''),
			"transactionId" : $("#transaction-id").val(),
			"accountEmail" : $("#account-email").val()
		} ]
	}
	return order;
}
function placeOrder(submitURL, inventoryId) {
	var order = loadValuesIntoOrder(inventoryId);
	console.log(order);
	$.post("order", JSON.stringify(order), "json").done(function(data) {
		hideDialogBlockDialog();
		showDialog("Order Placed", function() {
			window.location = 'customers.html'
			//refreshContactsList();
		});

	});
>>>>>>> branch 'master' of https://github.com/jcicale/EStore
}