var partnerId = 1;
$(function() {
	$.ajaxSetup({
		contentType : "application/json"
	});
	refreshOrdersList();
});
function refreshOrdersList() {
	var url = "order/partnerId/" + partnerId;
	showDialogBlockDialog("Loading Data from Server");
	$.getJSON(url).done(function(data) {
		drawSearchResults(data);
	});

}
function drawSearchResults(data) {
	console.log(data);
	$("#orders").empty();
	var tr = $("<tr>");
	var th0 = $("<th>").html("Order Number");
	var th1 = $("<th>").html("Partner Information");
	var th2 = $("<th>").html("Product Information");
	var th3 = $("<th>").html("Total Price");
	var th4 = $("<th>").html("Customer Information");
	var th5 = $("<th>").html("Order Status");
	var th6 = $("<th>").html("Available Actions");
	th0.appendTo(tr);
	th1.appendTo(tr);
	th2.appendTo(tr);
	th3.appendTo(tr);
	th4.appendTo(tr);
	th5.appendTo(tr);
	th6.appendTo(tr);
	tr.appendTo($("#orders"));
	for ( var item in data) {
		var order = data[item];
		var tr = $("<tr>").attr("id", "orderId_" + order.orderId);
		tr.attr("class", "order");
		drawOrder(order, tr);
		tr.appendTo($("#orders"));
	}
	hideDialogBlockDialog();
}

function getClassesForOrderState(orderState) {
	switch(orderState) {
		case "Canceled":
			return "order-item red";
		case "Fulfilled":
			return "order-item green";
		case "Ready to Pickup":
			return "order-item green";
		case "Delivered":
			return "order-item green";
		case "Pending":
			return "order-item teal";
		case "Ready to Ship":
			return "order-item blue";
		default:
			return "order-item";
	}
}

function getClassesForAction(action) {
	switch(action) {
		case "cancel":
			return "link red";
		case "accept":
			return "link";
		case "fulfill":
			return "link green";
		default:
			return "link";
	}
}

function drawOrder(order, parent) {
	parent.empty();
	$("<td class='order-item'>").html(order.orderId).appendTo(parent);
	$("<td class='order-item'>").html("Name: " + order.orderDetails[0].inventory.partner.name).appendTo(parent);
	$("<td class='order-item'>").html(order.orderDetails[0].inventory.product.title  + "\n" + order.orderDetails[0].inventory.product.description + "\nQuantity: " + order.orderDetails[0].quantity).appendTo(parent);
	$("<td class='order-item'>").html("$" + order.orderDetails[0].subTotal).appendTo(parent);
	$("<td class='order-item'>").html("Name: " + order.customer.firstName + " " + order.customer.lastName + "\nUsername: " + order.customer.userName).appendTo(parent);
	var orderStateClasses = getClassesForOrderState(order.orderDetails[0].orderState);
	$("<td class='" + orderStateClasses + "'>").html(order.orderDetails[0].orderState).appendTo(parent);
	var actionButtonCell = $("<td class='order-item action-buttons'>");
	if (typeof order.links !== 'undefined' && order.links !== null) {
		var icon = $("<span>");
		icon.appendTo(actionButtonCell);
		for ( var i in order.links) {
				icon.attr('class', 'show-icon action-required-icon ui-icon ui-icon-notice');
				var link = order.links[i];
				var classes = getClassesForAction(link.rel);
				$("<button class='" + classes + "'>").html(link.rel).attr("onclick", "proccesOrder('" + link.rel + "','" + link.href + "','" + link.method + "')").appendTo(actionButtonCell);
			}
	} else {
		if (typeof order._links !== 'undefined' && order._links !== null) {
			var icon = $("<span>");
			icon.appendTo(actionButtonCell);
			$.each(order._links, function(key, value) {
				var link = value;
				var classes = getClassesForAction(key);
				$("<button class ='" + classes + "'>").html(key).attr("onclick", "proccesOrder('" + key + "','" + link.href + "','" + link.method + "')").appendTo(actionButtonCell);
			});
		}
	} 
	actionButtonCell.appendTo(parent);
	console.log(order);
}
function proccesOrder(rel, href, method) {
	showConfirmDialog("You want to " + rel + " this Order?", function() {
		showDialogBlockDialog("Proccessing");
		$.ajax({
			url : href,
			method : method,
			success : function(result) {
				console.log(result);
				hideDialogBlockDialog();
				showDialog("Request Completed", function() {
					drawOrder(result, $("#orderId_" + result.orderId));
				});
			},
			error : function(request, msg, error) {
				console.log(error);
			}
		});
	});
}