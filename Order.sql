Create table Delivery (
orderId varchar(45) not null,
deliveryStatus enum('INPROGRESS','ONTHEWAY','COMPLETED','PICKUP'),
deliveryDateTime varchar(45) not null,
shippingTeamId integer);

drop table Delivery;
TRUNCATE TABLE Delivery;
select * from Delivery ;
desc Delivery;

drop table Delivery;
Create table OrderHistory (
orderId varchar(45) not null,
customerId varchar(45) ,
orderSubTotal double not null,
orderDate varchar(45) not null,
orderStatus enum('SUBMITTED','DELIVERED'),
paymentMethod enum('CASHONDELIVERY','PAYPAL','CREDITCARD','NETBANKING','DEBITCART','PAYTM') default 'CASHONDELIVERY',
deliveryMethod enum('PICKUP','HOMEDELIVEY','SHIPPINGADDRESSDELIVERY') default 'HOMEDELIVEY',
taxPercentange  double not null,
deliveryCompleted boolean,
ShippingAddress varchar(255),
phoneNumber varchar(45),
isOkaytoCall boolean);

drop table OrderHistory;
TRUNCATE TABLE OrderHistory;
select * from OrderHistory ;
desc OrderHistory;

Create table OrderHistoryItem (
id int not null auto_increment ,
orderId varchar(45) not null,
customerId varchar(45) ,
supplierId varchar(45),
productCode varchar(45) ,
productName varchar(45) ,
quantity integer ,
totalPrice double ,
sellPrice double ,
currencyType enum('RS','USD','DOLLAR') default 'RS',
discount integer,
discountType enum('NOTDEFINED','PERCENTAGE','FLATAMOUNT','QUANTITY'),
primary key (id)
)ENGINE=MYISAM ;

drop table OrderHistoryItem;
TRUNCATE TABLE OrderHistoryItem;
select * from OrderHistoryItem ;
desc OrderHistoryItem;


Create table CustomerOrder (
orderId varchar(45) not null,
customerId varchar(45) ,
orderSubTotal double ,
orderDate varchar(45) ,
orderEndDate varchar(45),
lastUpdatedDate varchar(45),
orderStatus enum('INCOMPLETE','COMPLETE','SUBMITTED') default 'INCOMPLETE' ,
taxPercentange  double ,
paymentMethod enum('NOTDEFINE','CASHONDELIVERY','PAYPAL','CREDITCARD','NETBANKING','DEBITCART','PAYTM') default 'NOTDEFINE',
deliveryMethod enum('NOTDEFINE','PICKUP','HOMEDELIVEY','SHIPPINGADDRESSDELIVERY') default 'NOTDEFINE',
ShippingAddress varchar(255),
zipCode  varchar(45),
deliveryCompleted boolean,phoneNumber varchar(45),
isOkaytoCall boolean,
PRIMARY KEY (orderId));

TRUNCATE TABLE CustomerOrder;
select * from CustomerOrder ;
drop table CustomerOrder;

Create table OrderItem (
id int not null auto_increment ,
orderId varchar(45) not null,
supplierId varchar(45),
productCode varchar(45) ,
productName varchar(45) ,
quantity integer ,
totalPrice double ,
sellPrice double ,
currencyType enum('RS','USD','DOLLAR') default 'RS',
discount integer,
discountType enum('NOTDEFINED','PERCENTAGE','FLATAMOUNT','QUANTITY')default 'NOTDEFINE',
primary key (id))ENGINE=MYISAM ;

TRUNCATE TABLE OrderItem;
select * from OrderItem ;
desc OrderItem;
drop table OrderItem;