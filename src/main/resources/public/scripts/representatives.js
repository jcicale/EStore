$(function() {
	$.ajaxSetup({
		contentType : "application/json"
	});
	refreshOrdersList();
});
function refreshOrdersList() {
	var url = "order";
	showDialogBlockDialog("Loading Data from Server");
	$.getJSON(url).done(function(data) {
		drawSearchResults(data);
	});

}
function drawSearchResults(data) {
	console.log(data);
	$("#orders").empty();
	for ( var item in data) {
		var order = data[item];
		var div = $("<div>").attr("id", "orderId_" + order.orderId);
		drawOrder(order, div);
		div.appendTo($("#orders"));
	}
	hideDialogBlockDialog();
}

function drawOrder(order, parent) {
	parent.empty();
	$("<span>").html(order.orderDetails[0].inventory.product.title).appendTo(parent);
	$("<span>").html(order.orderDetails[0].inventory.product.description).appendTo(parent);
	$("<span>").html(order.orderState).appendTo(parent);
	if (typeof order.links !== 'undefined' && order.links !== null) {
		for ( var i in order.links) {
			var link = order.links[i];
			$("<button>").html(link.rel).attr("onclick", "proccesOrder('" + link.rel + "','" + link.href + "','" + link.method + "')").appendTo(parent);
		}
	} else {
		if (typeof order._links !== 'undefined' && order._links !== null) {
			$.each(order._links, function(key, value) {
				var link = value;
				$("<button>").html(key).attr("onclick", "proccesOrder('" + key + "','" + link.href + "','" + link.method + "')").appendTo(parent);
			});
		}
	}
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