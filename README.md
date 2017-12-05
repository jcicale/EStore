
# COMP 388/433: Project 2
## Team Members  
- Julia Cicale  
- Percy Soliz 
## Description
Use the case study material as a requirement. We will concentrate improving services that were built in
project 3 by adding the following:  
1. Server side:  
- Start with an Entry Point for the service using the Search any Resource functionality  
- The response to searched resource will contain Hypermedia links for subsequent  
services.
- All subsequent resource state will provide Hypermedia for the resource representation
and subsequent application protocols.  
- All your Links should have – Action/Method, REL, URI, and Media Type  
- Authenticate/Authorize customer  
- ERROR and Exception handling  
2. Consumer Side:  
- A consumer application that will run in browser (or stand-alone).  
- It should start with an entry point.  
- All subsequent application protocols shown as links to be manipulated on the Consumer
application.  
3. The following will be the full service offering from the service side:  
- Authenticate/Authorize customer - Adding username/Password
- Search for a resource (such as Customer, Order, Product, Partner)  
- Buy a product  
- Get Order Status Information  
- Cancel Orders  
- List resources (such as Customer, Order, Product, Partner)   
## Use Cases
### The Buying Experience: Customer, eStore Representative, Partner
![Customer Registration](https://raw.githubusercontent.com/jcicale/EStore/master/src/main/resources/public/img/UseCaseDiagram.png)

## Class Diagram
![EStore_CD](https://i.imgur.com/xyUaF1D.gif)
## Entity Relationship Diagram
![EStore_ERD](https://i.imgur.com/7TRWs6v.png)

## Database Connection Details  
Host: 18.220.231.8:3306
Database: estore
User: estore
Password: estore



## Description
Use the case study material as a requirement. Your approach is to provide web service APIs for all functionalities that will provide access to your items that you will sell and to provide your partners to be able to add their inventories to be sold on your site using APIs. We will concentrate developing services for the following:  
### 1. Your web service functionalities to sell items would include functionalities such as:
#### a. Search item database by product
##### Description: 
This method performs a search over the title column of product table joining it with inventory and verifying the quantity is bigger than 0  
##### Path:
inventory/product?title={title}
##### Example URI: 
http://18.220.231.8:8080/inventory/product?title=FitBit 
##### Parameters
**-title:** Name of the product to perform the search over the inventory  
##### Method: 
GET  
##### Headers:   
```
Accept:application/json  
```
##### Body:  
```
none
```
##### Response:  
```
[
    {
        "inventoryId": 1,
        "partner": {
            "partnerId": 1,
            "name": "Amazon",
            "userName": "amazon",
            "links": []
        },
        "product": {
            "productId": 1,
            "title": "FitBit Alta",
            "description": "Activity Tracker",
            "review": [],
            "links": []
        },
        "price": 150,
        "quantity": 13,
        "links": [
            {
                "rel": "self",
                "href": "http://18.220.231.8:8080/inventory/1"
            },
            {
                "rel": "save",
                "href": "http://18.220.231.8:8080/order",
                "method": "POST"
            }
        ]
    },
    {
        "inventoryId": 5,
        "partner": {
            "partnerId": 2,
            "name": "Ebay",
            "userName": "ebay",
            "links": []
        },
        "product": {
            "productId": 1,
            "title": "FitBit Alta",
            "description": "Activity Tracker",
            "review": [],
            "links": []
        },
        "price": 160,
        "quantity": 14,
        "links": [
            {
                "rel": "self",
                "href": "http://18.220.231.8:8080/inventory/5"
            },
            {
                "rel": "save",
                "href": "http://18.220.231.8:8080/order",
                "method": "POST"
            }
        ]
    }
]
```
#### b. Create Order for Customer
##### Description: 
This method allows us to create a customerOrder from a customer. Used when you are a customer and would like to place an order of an item you are interested 
##### Path:
order
##### Example URI: 
http://18.220.231.8:8080/order
##### Parameters
none  
##### Method: 
POST  
##### Headers:   
```
Accept:application/json  
Content-Type:application/json  
```
##### Body:  
```
{
	"customerId": 1,
	"billingAddressId": 2,
	"orderDetails": [
		{
			"inventoryId": 1,
			"quantity": 1,
			"addressId": 1
		}
	],
	"paymentMethod": [
		{
			"subTotal": 150,
			"creditCardNumber": "5216740121470216",
			"nameOnCard": "Julia Cicale",
			"securityCode": "000",
			"validDate": "12/21"
		}
	]
}
```
##### Response:  
```
{
    "orderId": 5,
    "orderDetails": [
        {
            "orderDetailId": 5,
            "inventory": {
                "inventoryId": 1,
                "partner": {
                    "partnerId": 1,
                    "name": "Amazon",
                    "userName": "amazon"
                },
                "product": {
                    "productId": 1,
                    "title": "FitBit Alta",
                    "description": "Activity Tracker",
                    "review": []
                },
                "price": 150,
                "quantity": 13
            },
            "quantity": 1,
            "subTotal": 150,
            "orderState": "Pending",
            "shippingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657"
            },
            "estimatedDelivery": null,
            "trackingNumber": null,
            "delivered": null
        }
    ],
    "paymentStatus": "Pending",
    "customer": {
        "customerId": 1,
        "lastName": "Cicale",
        "firstName": "Julia",
        "userName": "julia.cicale",
        "billingAddress": {
            "addressId": 1,
            "street": "123 Home St.",
            "unit": "Unit 2",
            "city": "Chicago",
            "state": "IL",
            "zip": "60657"
        },
        "shippingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601"
        }
    },
    "orderState": "Pending",
    "billingAddress": {
        "addressId": 2,
        "street": "123 Business Rd.",
        "unit": "Ste 500",
        "city": "Chicago",
        "state": "IL",
        "zip": "60601"
    },
    "total": 150,
    "paymentMethod": [
        {
            "paymentId": 5,
            "paymentStatus": "Pending",
            "subTotal": 150,
            "creditCardNumber": "5216740121470216",
            "nameOnCard": "Julia Cicale",
            "securityCode": "000",
            "validDate": "12/21"
        }
    ]
}
```
#### c. Accept Credit Card payment  
##### Description: 
This method allows us to validate and accept the payments of an order
##### Path:
order/{orderId}/accept
##### Example URI: 
http://18.220.231.8:8080/order/5/accept
##### Parameters
none  
##### Method: 
PUT 
##### Headers:   
```
Accept:application/json  
Content-Type:application/json  
```
##### Body:  
none
##### Response:  
```
{
    "orderId": 5,
    "orderDetails": [
        {
            "orderDetailId": 5,
            "inventory": {
                "inventoryId": 1,
                "partner": {
                    "partnerId": 1,
                    "name": "Amazon",
                    "userName": "amazon"
                },
                "product": {
                    "productId": 1,
                    "title": "FitBit Alta",
                    "description": "Activity Tracker",
                    "review": []
                },
                "price": 150,
                "quantity": 13
            },
            "quantity": 1,
            "subTotal": 150,
            "orderState": "Pending",
            "shippingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657"
            },
            "estimatedDelivery": null,
            "trackingNumber": null,
            "delivered": null
        }
    ],
    "paymentStatus": "Verified",
    "customer": {
        "customerId": 1,
        "lastName": "Cicale",
        "firstName": "Julia",
        "userName": "julia.cicale",
        "billingAddress": {
            "addressId": 1,
            "street": "123 Home St.",
            "unit": "Unit 2",
            "city": "Chicago",
            "state": "IL",
            "zip": "60657"
        },
        "shippingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601"
        }
    },
    "orderState": "Verified",
    "billingAddress": {
        "addressId": 2,
        "street": "123 Business Rd.",
        "unit": "Ste 500",
        "city": "Chicago",
        "state": "IL",
        "zip": "60601"
    },
    "total": 150,
    "paymentMethod": [
        {
            "paymentId": 5,
            "paymentStatus": "Verified",
            "subTotal": 150,
            "creditCardNumber": "5216740121470216",
            "nameOnCard": "Julia Cicale",
            "securityCode": "000",
            "validDate": "12/21"
        }
    ],
    "_links": {
        "fulfill": {
            "href": "http://18.220.231.8:8080/order/5/fulfill",
            "method": "PUT"
        }
    }
}
```
#### d. Accept buy order  
##### Description: 
This method allows us to validate the order after the payment has been verified. It is now fulfilled and Partners can ship.
##### Path:  
order/{orderId}/fulfill
##### Example URI: 
http://18.220.231.8:8080/order/5/fulfill
##### Parameters
none  
##### Method: 
PUT 
##### Headers:   
```
Accept:application/json  
Content-Type:application/json  
```
##### Body:  
none
##### Response:  
```
{
    "orderId": 5,
    "orderDetails": [
        {
            "orderDetailId": 5,
            "inventory": {
                "inventoryId": 1,
                "partner": {
                    "partnerId": 1,
                    "name": "Amazon",
                    "userName": "amazon"
                },
                "product": {
                    "productId": 1,
                    "title": "FitBit Alta",
                    "description": "Activity Tracker",
                    "review": []
                },
                "price": 150,
                "quantity": 12
            },
            "quantity": 1,
            "subTotal": 150,
            "orderState": "Ready to Ship",
            "shippingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657"
            },
            "estimatedDelivery": null,
            "trackingNumber": null,
            "delivered": null
        }
    ],
    "paymentStatus": "Verified",
    "customer": {
        "customerId": 1,
        "lastName": "Cicale",
        "firstName": "Julia",
        "userName": "julia.cicale",
        "billingAddress": {
            "addressId": 1,
            "street": "123 Home St.",
            "unit": "Unit 2",
            "city": "Chicago",
            "state": "IL",
            "zip": "60657"
        },
        "shippingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601"
        }
    },
    "orderState": "Completed",
    "billingAddress": {
        "addressId": 2,
        "street": "123 Business Rd.",
        "unit": "Ste 500",
        "city": "Chicago",
        "state": "IL",
        "zip": "60601"
    },
    "total": 150,
    "paymentMethod": [
        {
            "paymentId": 5,
            "paymentStatus": "Paid",
            "subTotal": 150,
            "creditCardNumber": "5216740121470216",
            "nameOnCard": "Julia Cicale",
            "securityCode": "000",
            "validDate": "12/21"
        }
    ]
}
```
#### e. Ship orders  
##### Description: 
This method allows us to ship an Order once it has been fulfilled  
##### Path:
order/{orderId}/orderDetail/{orderDetailId}/ship/{trackingNumber}
##### Example URI: 
http://18.220.231.8:8080/order/5/orderDetail/5/ship/1234567898765432123456
##### Parameters
none  
##### Method: 
PUT 
##### Headers:   
```
Accept:application/json  
Content-Type:application/json  
```
##### Body:  
none
##### Response:  
```
{
    "orderId": 5,
    "orderDetails": [
        {
            "orderDetailId": 5,
            "inventory": {
                "inventoryId": 1,
                "partner": {
                    "partnerId": 1,
                    "name": "Amazon",
                    "userName": "amazon"
                },
                "product": {
                    "productId": 1,
                    "title": "FitBit Alta",
                    "description": "Activity Tracker",
                    "review": []
                },
                "price": 150,
                "quantity": 11
            },
            "quantity": 1,
            "subTotal": 150,
            "orderState": "Shipped",
            "shippingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657"
            },
            "estimatedDelivery": 1512773580083,
            "trackingNumber": "1234567898765432123456",
            "delivered": null
        }
    ],
    "paymentStatus": "Verified",
    "customer": {
        "customerId": 1,
        "lastName": "Cicale",
        "firstName": "Julia",
        "userName": "julia.cicale",
        "billingAddress": {
            "addressId": 1,
            "street": "123 Home St.",
            "unit": "Unit 2",
            "city": "Chicago",
            "state": "IL",
            "zip": "60657"
        },
        "shippingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601"
        }
    },
    "orderState": "Completed",
    "billingAddress": {
        "addressId": 2,
        "street": "123 Business Rd.",
        "unit": "Ste 500",
        "city": "Chicago",
        "state": "IL",
        "zip": "60601"
    },
    "total": 150,
    "paymentMethod": [
        {
            "paymentId": 5,
            "paymentStatus": "Paid",
            "subTotal": 150,
            "creditCardNumber": "5216740121470216",
            "nameOnCard": "Julia Cicale",
            "securityCode": "000",
            "validDate": "12/21"
        }
    ],
    "_links": {
        "delivered": {
            "href": "http://18.220.231.8:8080/order/5/orderDetail/5/delivered",
            "method": "PUT"
        }
    }
}
```
#### f. Provide order status; Provide status of orders in progress
##### Description: 
This method performs a search for orders that match a certain customerId (used to display the customer's orders) 
##### Path:
order/customerId/{customerId}
##### Example URI: 
http://18.220.231.8:8080/order/customerId/1
##### Parameters
none
##### Method: 
GET  
##### Headers:   
```
Accept:application/json  
```
##### Body:  
```
none
```
##### Response:  
```
[
    {
        "orderId": 1,
        "orderDetails": [
            {
                "orderDetailId": 2,
                "inventory": {
                    "inventoryId": 1,
                    "partner": {
                        "partnerId": 1,
                        "name": "Amazon",
                        "userName": "amazon",
                        "links": []
                    },
                    "product": {
                        "productId": 1,
                        "title": "FitBit Alta",
                        "description": "Activity Tracker",
                        "review": [],
                        "links": []
                    },
                    "price": 150,
                    "quantity": 11,
                    "links": []
                },
                "quantity": 1,
                "subTotal": 150,
                "orderState": "Pending",
                "shippingAddress": {
                    "addressId": 1,
                    "street": "123 Home St.",
                    "unit": "Unit 2",
                    "city": "Chicago",
                    "state": "IL",
                    "zip": "60657",
                    "links": []
                },
                "estimatedDelivery": null,
                "trackingNumber": null,
                "delivered": null,
                "links": []
            }
        ],
        "paymentStatus": "Verified",
        "customer": {
            "customerId": 1,
            "lastName": "Cicale",
            "firstName": "Julia",
            "userName": "julia.cicale",
            "billingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657",
                "links": []
            },
            "shippingAddress": {
                "addressId": 2,
                "street": "123 Business Rd.",
                "unit": "Ste 500",
                "city": "Chicago",
                "state": "IL",
                "zip": "60601",
                "links": []
            },
            "links": []
        },
        "orderState": "Verified",
        "billingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601",
            "links": []
        },
        "total": 150,
        "paymentMethod": [
            {
                "paymentId": 2,
                "paymentStatus": "Verified",
                "subTotal": 150,
                "creditCardNumber": "1234567890123456",
                "nameOnCard": "John Jones",
                "securityCode": "111",
                "validDate": "12/10",
                "links": []
            }
        ],
        "links": []
    },
    {
        "orderId": 2,
        "orderDetails": [
            {
                "orderDetailId": 1,
                "inventory": {
                    "inventoryId": 1,
                    "partner": {
                        "partnerId": 1,
                        "name": "Amazon",
                        "userName": "amazon",
                        "links": []
                    },
                    "product": {
                        "productId": 1,
                        "title": "FitBit Alta",
                        "description": "Activity Tracker",
                        "review": [],
                        "links": []
                    },
                    "price": 150,
                    "quantity": 11,
                    "links": []
                },
                "quantity": 1,
                "subTotal": 150,
                "orderState": "Pending",
                "shippingAddress": {
                    "addressId": 1,
                    "street": "123 Home St.",
                    "unit": "Unit 2",
                    "city": "Chicago",
                    "state": "IL",
                    "zip": "60657",
                    "links": []
                },
                "estimatedDelivery": null,
                "trackingNumber": null,
                "delivered": null,
                "links": []
            }
        ],
        "paymentStatus": "Pending",
        "customer": {
            "customerId": 1,
            "lastName": "Cicale",
            "firstName": "Julia",
            "userName": "julia.cicale",
            "billingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657",
                "links": []
            },
            "shippingAddress": {
                "addressId": 2,
                "street": "123 Business Rd.",
                "unit": "Ste 500",
                "city": "Chicago",
                "state": "IL",
                "zip": "60601",
                "links": []
            },
            "links": []
        },
        "orderState": "Pending",
        "billingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601",
            "links": []
        },
        "total": 150,
        "paymentMethod": [
            {
                "paymentId": 1,
                "paymentStatus": "Pending",
                "subTotal": 150,
                "creditCardNumber": "1234567890123456",
                "nameOnCard": "John Jones",
                "securityCode": "111",
                "validDate": "12/10",
                "links": []
            }
        ],
        "links": [
            {
                "rel": "cancel",
                "href": "http://18.220.231.8:8080/order/2/cancel",
                "method": "PUT"
            }
        ]
    },
    {
        "orderId": 3,
        "orderDetails": [
            {
                "orderDetailId": 3,
                "inventory": {
                    "inventoryId": 5,
                    "partner": {
                        "partnerId": 2,
                        "name": "Ebay",
                        "userName": "ebay",
                        "links": []
                    },
                    "product": {
                        "productId": 1,
                        "title": "FitBit Alta",
                        "description": "Activity Tracker",
                        "review": [],
                        "links": []
                    },
                    "price": 160,
                    "quantity": 14,
                    "links": []
                },
                "quantity": 1,
                "subTotal": 160,
                "orderState": "Ready to Pickup",
                "timeForPickUp": "12/05/17 09:00 - 17:00",
                "links": []
            }
        ],
        "paymentStatus": "Verified",
        "customer": {
            "customerId": 1,
            "lastName": "Cicale",
            "firstName": "Julia",
            "userName": "julia.cicale",
            "billingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657",
                "links": []
            },
            "shippingAddress": {
                "addressId": 2,
                "street": "123 Business Rd.",
                "unit": "Ste 500",
                "city": "Chicago",
                "state": "IL",
                "zip": "60601",
                "links": []
            },
            "links": []
        },
        "orderState": "Completed",
        "billingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601",
            "links": []
        },
        "total": 160,
        "paymentMethod": [
            {
                "paymentId": 3,
                "paymentStatus": "Paid",
                "subTotal": 160,
                "creditCardNumber": "1234567890123456",
                "nameOnCard": "John Jones",
                "securityCode": "111",
                "validDate": "12/10",
                "links": []
            }
        ],
        "links": []
    },
    {
        "orderId": 4,
        "orderDetails": [
            {
                "orderDetailId": 4,
                "inventory": {
                    "inventoryId": 1,
                    "partner": {
                        "partnerId": 1,
                        "name": "Amazon",
                        "userName": "amazon",
                        "links": []
                    },
                    "product": {
                        "productId": 1,
                        "title": "FitBit Alta",
                        "description": "Activity Tracker",
                        "review": [],
                        "links": []
                    },
                    "price": 150,
                    "quantity": 11,
                    "links": []
                },
                "quantity": 1,
                "subTotal": 150,
                "orderState": "Pending",
                "shippingAddress": {
                    "addressId": 1,
                    "street": "123 Home St.",
                    "unit": "Unit 2",
                    "city": "Chicago",
                    "state": "IL",
                    "zip": "60657",
                    "links": []
                },
                "estimatedDelivery": null,
                "trackingNumber": null,
                "delivered": null,
                "links": []
            }
        ],
        "paymentStatus": "Pending",
        "customer": {
            "customerId": 1,
            "lastName": "Cicale",
            "firstName": "Julia",
            "userName": "julia.cicale",
            "billingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657",
                "links": []
            },
            "shippingAddress": {
                "addressId": 2,
                "street": "123 Business Rd.",
                "unit": "Ste 500",
                "city": "Chicago",
                "state": "IL",
                "zip": "60601",
                "links": []
            },
            "links": []
        },
        "orderState": "Pending",
        "billingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601",
            "links": []
        },
        "total": 150,
        "paymentMethod": [
            {
                "paymentId": 4,
                "paymentStatus": "Pending",
                "subTotal": 150,
                "creditCardNumber": "1234567890123456",
                "nameOnCard": "John Jones",
                "securityCode": "111",
                "validDate": "12/10",
                "links": []
            }
        ],
        "links": [
            {
                "rel": "cancel",
                "href": "http://18.220.231.8:8080/order/4/cancel",
                "method": "PUT"
            }
        ]
    },
    {
        "orderId": 5,
        "orderDetails": [
            {
                "orderDetailId": 5,
                "inventory": {
                    "inventoryId": 1,
                    "partner": {
                        "partnerId": 1,
                        "name": "Amazon",
                        "userName": "amazon",
                        "links": []
                    },
                    "product": {
                        "productId": 1,
                        "title": "FitBit Alta",
                        "description": "Activity Tracker",
                        "review": [],
                        "links": []
                    },
                    "price": 150,
                    "quantity": 11,
                    "links": []
                },
                "quantity": 1,
                "subTotal": 150,
                "orderState": "Shipped",
                "shippingAddress": {
                    "addressId": 1,
                    "street": "123 Home St.",
                    "unit": "Unit 2",
                    "city": "Chicago",
                    "state": "IL",
                    "zip": "60657",
                    "links": []
                },
                "estimatedDelivery": 1512773580000,
                "trackingNumber": "1234567898765432123456",
                "delivered": null,
                "links": []
            }
        ],
        "paymentStatus": "Verified",
        "customer": {
            "customerId": 1,
            "lastName": "Cicale",
            "firstName": "Julia",
            "userName": "julia.cicale",
            "billingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657",
                "links": []
            },
            "shippingAddress": {
                "addressId": 2,
                "street": "123 Business Rd.",
                "unit": "Ste 500",
                "city": "Chicago",
                "state": "IL",
                "zip": "60601",
                "links": []
            },
            "links": []
        },
        "orderState": "Completed",
        "billingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601",
            "links": []
        },
        "total": 150,
        "paymentMethod": [
            {
                "paymentId": 5,
                "paymentStatus": "Paid",
                "subTotal": 150,
                "creditCardNumber": "5216740121470216",
                "nameOnCard": "Julia Cicale",
                "securityCode": "000",
                "validDate": "12/21",
                "links": []
            }
        ],
        "links": []
    },
    {
        "orderId": 6,
        "orderDetails": [
            {
                "orderDetailId": 6,
                "inventory": {
                    "inventoryId": 1,
                    "partner": {
                        "partnerId": 1,
                        "name": "Amazon",
                        "userName": "amazon",
                        "links": []
                    },
                    "product": {
                        "productId": 1,
                        "title": "FitBit Alta",
                        "description": "Activity Tracker",
                        "review": [],
                        "links": []
                    },
                    "price": 150,
                    "quantity": 11,
                    "links": []
                },
                "quantity": 1,
                "subTotal": 150,
                "orderState": "Ready to Ship",
                "shippingAddress": {
                    "addressId": 1,
                    "street": "123 Home St.",
                    "unit": "Unit 2",
                    "city": "Chicago",
                    "state": "IL",
                    "zip": "60657",
                    "links": []
                },
                "estimatedDelivery": null,
                "trackingNumber": null,
                "delivered": null,
                "links": []
            }
        ],
        "paymentStatus": "Verified",
        "customer": {
            "customerId": 1,
            "lastName": "Cicale",
            "firstName": "Julia",
            "userName": "julia.cicale",
            "billingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657",
                "links": []
            },
            "shippingAddress": {
                "addressId": 2,
                "street": "123 Business Rd.",
                "unit": "Ste 500",
                "city": "Chicago",
                "state": "IL",
                "zip": "60601",
                "links": []
            },
            "links": []
        },
        "orderState": "Completed",
        "billingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601",
            "links": []
        },
        "total": 150,
        "paymentMethod": [
            {
                "paymentId": 6,
                "paymentStatus": "Paid",
                "subTotal": 150,
                "creditCardNumber": "5216740121470216",
                "nameOnCard": "Julia Cicale",
                "securityCode": "000",
                "validDate": "12/21",
                "links": []
            }
        ],
        "links": []
    }
]
```
#### g. Order Cancel  
##### Description: 
This method allows us to cancel an order (instead of shipping it)  
##### Path:
order/{orderId}/cancel
##### Example URI: 
http://18.220.231.8:8080/order/7/cancel
##### Parameters
none  
##### Method: 
PUT 
##### Headers:   
```
Accept:application/json  
Content-Type:application/json  
```
##### Body:  
none
##### Response:  
```
{
    "orderId": 7,
    "orderDetails": [
        {
            "orderDetailId": 7,
            "inventory": {
                "inventoryId": 1,
                "partner": {
                    "partnerId": 1,
                    "name": "Amazon",
                    "userName": "amazon"
                },
                "product": {
                    "productId": 1,
                    "title": "FitBit Alta",
                    "description": "Activity Tracker",
                    "review": []
                },
                "price": 150,
                "quantity": 11
            },
            "quantity": 1,
            "subTotal": 150,
            "orderState": "Canceled",
            "shippingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657"
            },
            "estimatedDelivery": null,
            "trackingNumber": null,
            "delivered": null
        }
    ],
    "paymentStatus": "Pending",
    "customer": {
        "customerId": 1,
        "lastName": "Cicale",
        "firstName": "Julia",
        "userName": "julia.cicale",
        "billingAddress": {
            "addressId": 1,
            "street": "123 Home St.",
            "unit": "Unit 2",
            "city": "Chicago",
            "state": "IL",
            "zip": "60657"
        },
        "shippingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601"
        }
    },
    "orderState": "Canceled",
    "billingAddress": {
        "addressId": 2,
        "street": "123 Business Rd.",
        "unit": "Ste 500",
        "city": "Chicago",
        "state": "IL",
        "zip": "60601"
    },
    "total": 150,
    "paymentMethod": [
        {
            "paymentId": 7,
            "paymentStatus": "Refunded",
            "subTotal": 150,
            "creditCardNumber": "5216740121470216",
            "nameOnCard": "Julia Cicale",
            "securityCode": "000",
            "validDate": "12/21"
        }
    ]
}
```
### 2. Allowing Partners to use your site to sell their products with functionalities such as:  
#### a. Need to register and create profile of partners  
##### Description: 
This method allows us to register a new Partner inside of the application  
#### Path:  
partner
##### Example URI: 
http://18.220.231.8:8080/partner
##### Parameters
none  
##### Method: 
POST  
##### Headers:   
```
Accept:application/json  
Content-Type:application/json  
```
##### Body:  
```
{
	"name": "JackRabbit",
	"userName": "jackrabbit",
	"password": "password12345"
} 
```
##### Response:  
```
{
    "partnerId": 3,
    "name": "JackRabbit",
    "userName": "jackrabbit"
}
```
#### b. Add product or products in market place  
##### Description: 
This method allows us to register a new product in the database  
##### Path:  
product
##### Example URI: 
http://18.220.231.8:8080/product
##### Parameters
none  
##### Method: 
POST  
##### Headers:   
```
Accept:application/json  
Content-Type:application/json  
```
##### Body:  
```
{
    "productId": 0,
    "title": "Amazon Echo",
    "description": "Alexa enabled device with a speaker"
} 
```
##### Response:  
```
{
    "productId": 5,
    "title": "Amazon Echo",
    "description": "Alexa enabled device with a speaker",
    "review": null
}
```
#### c. Add products to inventory of partners 
##### Description: 
This method allows us to increase the inventory of a partner in the database  
##### Path:  
inventory/{inventoryId}/increase
##### Example URI: 
http://18.220.231.8:8080/inventory/1/increase
##### Parameters
none  
##### Method: 
PUT  
##### Headers:   
```
Accept:application/json  
Content-Type:application/json  
```
##### Body:  
none
##### Response:  
```
{
    "inventoryId": 1,
    "partner": {
        "partnerId": 1,
        "name": "Amazon",
        "userName": "amazon"
    },
    "product": {
        "productId": 1,
        "title": "FitBit Alta",
        "description": "Activity Tracker",
        "review": []
    },
    "price": 150,
    "quantity": 11,
    "_links": {
        "increase": {
            "href": "http://localhost:8080/inventory/1/increase",
            "method": "PUT"
        }
    }
}
```
#### d. Push orders that customers made to partners  
##### Description: 
This method allows the partners to get a list of all the OrderDetails that had been placed over their inventories.  
##### Path:  
order/partnerId/{partnerId}
##### Example URI: 
http://18.220.231.8:8080/order/partnerId/1  
##### Parameters
none 
##### Method: 
GET  
##### Headers:   
```
Accept:application/json  
```
##### Body:  
```
none
```
##### Response:  
```
[
    {
        "orderId": 1,
        "orderDetails": [
            {
                "orderDetailId": 1,
                "inventory": {
                    "inventoryId": 1,
                    "partner": {
                        "partnerId": 1,
                        "name": "Amazon",
                        "userName": "amazon",
                        "links": []
                    },
                    "product": {
                        "productId": 1,
                        "title": "FitBit Alta",
                        "description": "Activity Tracker",
                        "review": [],
                        "links": []
                    },
                    "price": 150,
                    "quantity": 11,
                    "links": []
                },
                "quantity": 1,
                "subTotal": 150,
                "orderState": "Pending",
                "timeForPickUp": null,
                "links": []
            }
        ],
        "paymentStatus": "Pending",
        "customer": {
            "customerId": 1,
            "lastName": "Cicale",
            "firstName": "Julia",
            "userName": "julia.cicale",
            "billingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657",
                "links": []
            },
            "shippingAddress": {
                "addressId": 2,
                "street": "123 Business Rd.",
                "unit": "Ste 500",
                "city": "Chicago",
                "state": "IL",
                "zip": "60601",
                "links": []
            },
            "links": []
        },
        "orderState": "Pending",
        "billingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601",
            "links": []
        },
        "total": 150,
        "paymentMethod": [
            {
                "paymentId": 1,
                "paymentStatus": "Pending",
                "subTotal": 150,
                "creditCardNumber": "1234567890123456",
                "nameOnCard": "John Jones",
                "securityCode": "111",
                "validDate": "12/10",
                "links": []
            }
        ],
        "links": []
    }
]
```
#### e. Get acknowledgement of order fulfillment  
##### Description: 
This method allows us to mark one orderDetail item as delivered, only if the current status is "Shipped".  
##### Path:  
order/{orderId}/orderDetail/{orderDetailId}/delivered
##### URI: 
http://18.220.231.8:8080/order/2/orderDetail/2/delivered
##### Parameters
none  
##### Method: 
PUT 
##### Headers:   
```
Accept:application/json  
Content-Type:application/json  
```
##### Body:  
```
none
```
##### Response:  
```
{
    "orderId": 2,
    "orderDetails": [
        {
            "orderDetailId": 2,
            "inventory": {
                "inventoryId": 2,
                "partner": {
                    "partnerId": 1,
                    "name": "Amazon",
                    "userName": "amazon"
                },
                "product": {
                    "productId": 2,
                    "title": "Bluetooth Headphones",
                    "description": "Wireless and comfortable headphones for running",
                    "review": []
                },
                "price": 80,
                "quantity": 9
            },
            "quantity": 1,
            "subTotal": 80,
            "orderState": "Delivered",
            "shippingAddress": {
                "addressId": 1,
                "street": "123 Home St.",
                "unit": "Unit 2",
                "city": "Chicago",
                "state": "IL",
                "zip": "60657"
            },
            "estimatedDelivery": 1512777063000,
            "trackingNumber": "trackingNumber",
            "delivered": 1512518084148
        }
    ],
    "paymentStatus": "Verified",
    "customer": {
        "customerId": 1,
        "lastName": "Cicale",
        "firstName": "Julia",
        "userName": "julia.cicale",
        "billingAddress": {
            "addressId": 1,
            "street": "123 Home St.",
            "unit": "Unit 2",
            "city": "Chicago",
            "state": "IL",
            "zip": "60657"
        },
        "shippingAddress": {
            "addressId": 2,
            "street": "123 Business Rd.",
            "unit": "Ste 500",
            "city": "Chicago",
            "state": "IL",
            "zip": "60601"
        }
    },
    "orderState": "Completed",
    "billingAddress": {
        "addressId": 2,
        "street": "123 Business Rd.",
        "unit": "Ste 500",
        "city": "Chicago",
        "state": "IL",
        "zip": "60601"
    },
    "total": 80,
    "paymentMethod": [
        {
            "paymentId": 2,
            "paymentStatus": "Paid",
            "subTotal": 80,
            "creditCardNumber": "1234567890123456",
            "nameOnCard": "John Jones",
            "securityCode": "111",
            "validDate": "12/10"
        }
    ]
}
```
### 3. Features to make your APIs robust:  
#### a. ERROR Handling  
We create a null variable that will be sent back to the user no matter if the operation was completed successfully or not. If there is a problem, we make sure the variable to be sent is set to null.  

```
	@GET
	@Produces({ "application/xml", "application/json" })
	@Path("/searchInventoryofProduct")
	public List<InventoryRepresentation> getProducts(@QueryParam("keywords") String keywords) {
		List<InventoryRepresentation> listInventoryRepresentation = null;
		try {
			listInventoryRepresentation = orderActivity.getProducts(keywords);
		} catch (Exception e) {
			e.printStackTrace();
			listInventoryRepresentation = null;
		}
		return listInventoryRepresentation;
	}
```
Furthermore, we can consider the underlying logic of the requested operation as a normal method. Which would return true when the operation is completed successfully, and false if not. Then we return a 200 or a 400 error code respectively.  

```
	@PUT
	@Produces({ "application/xml", "application/json" })
	@Path("/markOrderAsDelivered")
	public Response deliveredOrderDetail(OrderDetailDeliveredRequest orderDetailDeliveredRequest) {
		try {
			if (partnerActivity.deliveredOrderDetail(orderDetailDeliveredRequest)) {
				return Response.status(Status.OK).build();
			} else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
```
#### b. Exception handling  
We are adding a try and a catch block surrounding the code that could cause an exception in all methods, then we print the exception to the log files of the server and send a response with the generic error 500.  

```
	try {
			if (partnerActivity.fulfillOrder(customerOrderRequest)) {
				return Response.status(Status.OK).build();
			} else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
```
