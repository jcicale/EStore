var partnerId = 1;
$(function() {
	$.ajaxSetup({
		contentType : "application/json"
	});
	refreshOrdersList();
});
function refreshOrdersList() {
	var url = "product";
	showDialogBlockDialog("Loading Data from Server");
	$.getJSON(url).done(function(data) {
		drawSearchResults(data);
	});

}
function drawSearchResults(data) {
	console.log(data);
	$("#orders").empty();
	var tr = $("<tr>");
	var th0 = $("<th>").html("Number");
	var th1 = $("<th>").html("Product");
	var th2 = $("<th>").html("Description");
	var th3 = $("<th>").html("Available Actions");
	th0.appendTo(tr);
	th1.appendTo(tr);
	th2.appendTo(tr);
	th3.appendTo(tr);
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
			return "link blue";
		case "fulfill":
			return "link green";
		default:
			return "link";
	}
}

function drawOrder(product, parent) {
	parent.empty();
	$("<td class='order-item'>").html(product.productId).appendTo(parent);
	$("<td class='order-item'>").html(product.title).appendTo(parent);
	$("<td class='order-item'>").html(product.description).appendTo(parent);
	var actionButtonCell = $("<td class='order-item action-buttons'>");
	if (typeof product.links !== 'undefined' && product.links !== null) {
		var icon = $("<span>");
		icon.appendTo(actionButtonCell);
		for ( var i in product.links) {
				icon.attr('class', 'show-icon action-required-icon ui-icon ui-icon-notice');
				var link = product.links[i];
				var classes = getClassesForAction(link.rel);
				$("<button class='" + classes + "'>").html(link.rel).attr("onclick", "proccesOrder('" + link.rel + "','" + link.href + "','" + link.method + "')").appendTo(actionButtonCell);
			}
	} else {
		if (typeof product._links !== 'undefined' && product._links !== null) {
			var icon = $("<span>");
			icon.appendTo(actionButtonCell);
			$.each(product._links, function(key, value) {
				var link = value;
				var classes = getClassesForAction(key);
				$("<button class ='" + classes + "'>").html(key).attr("onclick", "proccesOrder('" + key + "','" + link.href + "','" + link.method + "')").appendTo(actionButtonCell);
			});
		}
	} 
	actionButtonCell.appendTo(parent);
	console.log(product);
}
function proccesOrder(rel, href, method) {
	showConfirmDialog("You want to " + rel + " this Product?", function() {
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