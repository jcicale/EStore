var partnerId = 1;
$(function() {
	$.ajaxSetup({
		contentType : "application/json"
	});
	refreshOrdersList();
});
function refreshOrdersList() {
	var url = "inventory/partnerId/" + partnerId;
	showDialogBlockDialog("Loading Data from Server");
	$.getJSON(url).done(function(data) {
		drawSearchResults(data);
	});

}
function drawSearchResults(data) {
	console.log(data);
	$("#orders").empty();
	var tr = $("<tr>");
	var th0 = $("<th>").html("Inventory Number");
	var th1 = $("<th>").html("Product");
	var th2 = $("<th>").html("Price");
	var th3 = $("<th>").html("Quantity");
	var th4 = $("<th>").html("Available Actions");
	th0.appendTo(tr);
	th1.appendTo(tr);
	th2.appendTo(tr);
	th3.appendTo(tr);
	th4.appendTo(tr);
	tr.appendTo($("#orders"));
	for ( var item in data) {
		var inventory = data[item];
		var tr = $("<tr>").attr("id", "inventoryId_" + inventory.inventoryId);
		tr.attr("class", "order");
		drawOrder(inventory, tr);
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

function drawOrder(inventory, parent) {
	parent.empty();
	$("<td class='order-item'>").html(inventory.inventoryId).appendTo(parent);
	$("<td class='order-item'>").html(inventory.product.title).appendTo(parent);
	$("<td class='order-item'>").html(inventory.price).appendTo(parent);
	$("<td class='order-item'>").html(inventory.quantity).appendTo(parent);
	var actionButtonCell = $("<td class='order-item action-buttons'>");
	if (typeof inventory.links !== 'undefined' && inventory.links !== null) {
		var icon = $("<span>");
		icon.appendTo(actionButtonCell);
		for ( var i in inventory.links) {
				icon.attr('class', 'show-icon action-required-icon ui-icon ui-icon-notice');
				var link = inventory.links[i];
				var classes = getClassesForAction(link.rel);
				$("<button class='" + classes + "'>").html(link.rel).attr("onclick", "proccesOrder('" + link.rel + "','" + link.href + "','" + link.method + "')").appendTo(actionButtonCell);
			}
	} else {
		if (typeof inventory._links !== 'undefined' && inventory._links !== null) {
			var icon = $("<span>");
			icon.appendTo(actionButtonCell);
			$.each(inventory._links, function(key, value) {
				var link = value;
				var classes = getClassesForAction(key);
				$("<button class ='" + classes + "'>").html(key).attr("onclick", "proccesOrder('" + key + "','" + link.href + "','" + link.method + "')").appendTo(actionButtonCell);
			});
		}
	} 
	actionButtonCell.appendTo(parent);
	console.log(inventory);
}
function proccesOrder(rel, href, method) {
	showConfirmDialog("You want to " + rel + " this Inventory?", function() {
		showDialogBlockDialog("Proccessing");
		$.ajax({
			url : href,
			method : method,
			success : function(result) {
				console.log(result);
				hideDialogBlockDialog();
				showDialog("Request Completed", function() {
					drawOrder(result, $("#inventoryId_" + result.inventoryId));
				});
			},
			error : function(request, msg, error) {
				console.log(error);
			}
		});
	});
}