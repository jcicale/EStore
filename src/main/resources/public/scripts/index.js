var list;
var form;
var customerId=1;
var billingAddressId = 2;
var shippingAddressId = 1;
$(function() {
	list = $('#list');
	form = $('#form');
	$.ajaxSetup({
		contentType : "application/json"
	});
	refreshContactsList();
});
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
function previewFile(event) {
	var profileImage = document.getElementById('profileImage');
	profileImage.src = URL.createObjectURL(event.target.files[0]);
};
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
function milisToDate(time){
	var time = new Date().getTime();
	var date = new Date(time);
	return date.toString();
}

function loadDataToForm(varUrl) {
	list.hide();
	form.show();
	showDialogBlockDialog("Loading Data from Server");
	if (varUrl == 0) {
		document.getElementById('contactId').value = "0";
		document.getElementById('name').value = "";
		document.getElementById('company').value = "";
		document.getElementById('profileImage').src = "img/testProfile.jpg";
		document.getElementById('email').value = "";
		document.getElementById('birthdate').value = "";
		document.getElementById('personalPhoneNumber').value = "";
		document.getElementById('workPhoneNumber').value = "";
		document.getElementById('addressId').value = "0";
		document.getElementById('street').value = "";
		document.getElementById('unit').value = "";
		document.getElementById('city').value = "";
		document.getElementById('state').value = "";
		document.getElementById('zip').value = "";
		$("#save").attr("onclick", "saveContact(0)");
		hideDialogBlockDialog();
	} else {
		console.log(varUrl);
		varUrl = simplifyURL(varUrl);
		console.log(varUrl);

		$.getJSON(varUrl).done(function(data) {
			document.getElementById('contactId').value = data.contactId;
			document.getElementById('name').value = data.name;
			document.getElementById('company').value = data.company;
			document.getElementById('profileImage').src = data._links['view'].href;
			document.getElementById('email').value = data.email;
			document.getElementById('birthdate').value = formatDate(milisToDate(data.birthdate));
			document.getElementById('personalPhoneNumber').value = data.personalPhoneNumber;
			document.getElementById('workPhoneNumber').value = data.workPhoneNumber;
			document.getElementById('addressId').value = data.address.addressId;
			document.getElementById('street').value = data.address.street;
			document.getElementById('unit').value = data.address.unit;
			document.getElementById('city').value = data.address.city;
			document.getElementById('state').value = data.address.state;
			document.getElementById('zip').value = data.address.zip;
			$("#save").attr("onclick", "saveContact('" + data._links['save'].href + "')");
			hideDialogBlockDialog();
		});
	}
}
$( function() {
    var availableProducts = [
      "FitBit",
      "Bluetooth Headphones",
      "Laptop Bag",
      "The Odyssey"
    ];
    $( "#product-search" ).autocomplete({
      source: availableProducts
    });
  } );


function drawSearchResults(data) {
	console.log(data);
	$("#items").empty();
	for ( var item in data) {
		var inventory = data[item];
		drawInventory(inventory,$("#items"),true)
	}
	hideDialogBlockDialog();
}

function drawInventory(inventory, parent, isList){
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
	if(isList){
		var p3 = $("<p>").html('Price: $' + inventory.price);
		p3.appendTo(div);
		rel = "loadReviewOrder('" + lookup(inventory.links,"rel","self").href +"','" + lookup(inventory.links,"rel","save").href +"'," + inventory.inventoryId + ")";
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
        if (array[i] && array[i][prop] === value) return array[i];
}

$.validate({
    form : '#place-order-form',
    onError : function($form) {
      alert('Validation of form '+$form.attr('id')+' failed!');
    },
    onSuccess : function($form) {
      alert('The form '+$form.attr('id')+' is valid!');
      return false; // Will stop the submission of the form
    },
  });

function loadReviewOrder(selfLink, placeOrderLink, inventoryId){
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
			width: "60%",
			maxWidth: "500px",
			position: { my: 'top', at: 'top+50' },
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
		$( "#place-order-form").on('submit', function(e) {
			e.preventDefault();
			placeOrder(placeOrderLink, inventoryId);
			$("#dialogPlaceOrder").dialog("close");
		})
	    $( "#credit-card-radio" ).checkboxradio({
	      icon: false
	    });
	    $( "#paypal-radio" ).checkboxradio({
		      icon: false
		});
	    $('#select-quantity').on('change keyup paste', function() {
	        var quantity = $(this).val();
	        var price = document.querySelector('p#price').innerHTML.replace(/\D/g,'');
	        console.log(price);
	        document.querySelector('#subtotal').innerHTML = "$" + quantity * price;
	    });
	    $('input[name="payment-radio"]').click(function(){
	        var inputValue = $(this).val();
	        var targetFields = $("." + inputValue);
	        $(".fields").not(targetFields).hide();
	        $(targetFields).show();
	    });
	    
	});
}

function loadInventoryDetailsToReviewOrder(selfLink){
//	showDialogBlockDialog("Loading Data from Server"); //commented out for now due to jquery ui bug
	$.getJSON(selfLink).done(function(data) {
		$('#inventoryDetails').empty();
		drawInventory(data,$('#inventoryDetails'),false);
//		hideDialogBlockDialog();
	});
}

function simplifyURL(varUrl) {
	var pathArray = varUrl.split('/');
	if(pathArray[pathArray.length - 2].includes(":")){
		varUrl = pathArray[pathArray.length - 1];
	}else{
		varUrl = pathArray[pathArray.length - 2] + '/' + pathArray[pathArray.length - 1];
	}
	return varUrl;
}

function deleteContact(varUrl) {
	varUrl = simplifyURL(varUrl);
	console.log(varUrl);
	showConfirmDialog("Are you sure?", function() {
		showDialogBlockDialog("Deleting");
		$.ajax({
			url : varUrl,
			method : 'DELETE',
			type : 'DELETE',
			success : function(result) {
				console.log(result);
				hideDialogBlockDialog();
				showDialog("Contact Deleted", function() {
					refreshContactsList();
				});
			},
			error : function(request, msg, error) {
				console.log(error);
			}
		});
	});
}

function getBase64Image(img) {
	var canvas = document.createElement("canvas");
	canvas.width = img.width;
	canvas.height = img.height;
	var ctx = canvas.getContext("2d");
	ctx.drawImage(img, 0, 0, img.width, img.height);
	var dataURL = canvas.toDataURL("image/png");
	return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
}
function loadValuesIntoOrder(inventoryId) {
	var order = {
			"customerId": 1,
			"billingAddressId": 2,
			"orderDetails": [
				{
					
				}
				
			],
			"paymentMethod": [
				{
					
				}
			]
		}
	order.orderDetails.inventoryId = inventoryId;
	order.orderDetails.quantity = parseInt($('#select-quantity').val());
	order.orderDetails.addressId = 1;
	
	var paymentType = $('input[name="payment-radio"]:checked').val();
	if (paymentType === "credit-card-radio") {
		order.paymentMethod.subTotal = parseInt(document.querySelector("#subtotal").innerHTML.replace(/\D/g,''));
		order.paymentMethod.creditCardNumber = $("#credit-card-number").val();
		order.paymentMethod.nameOnCard = $("#name-on-card").val();
		order.paymentMethod.securityCode = $("#security-code").val();
		order.paymentMethod.validDate = $("#expiration-date").val();
	} else if (paymentType === "paypal-radio") {
		order.paymentMethod.subTotal = parseInt(document.querySelector("#subtotal").innerHTML.replace(/\D/g,''));
		order.paymentMethod.transactionId = $("#transaction-id").val();
		order.paymentMethod.accountEmail = $("#account-email").val();
	}
	return order;
}
function placeOrder(submitURL, inventoryId){
	var order = loadValuesIntoOrder(inventoryId);
	console.log(order);
	//submit
}
function saveContact(varUrl) {
	showDialogBlockDialog("Saving");
	if (varUrl == 0) {

		$.post("contact", JSON.stringify(loadFormIntoContact()), "json").done(function(data) {
			hideDialogBlockDialog();
			showDialog("Contact Saved", function() {
				refreshContactsList();
			});

		});
	} else {
		varUrl = simplifyURL(varUrl);
		$.ajax({
			url : varUrl,
			method : "PUT",
			type : "PUT",
			data : JSON.stringify(loadFormIntoContact()),
			success : function(result) {
				console.log(result);
				hideDialogBlockDialog();
				showDialog("Contact Saved", function() {
					refreshContactsList();
				});
			},
			error : function(request, msg, error) {
				console.log(error);
			}
		});
	}
}
function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}