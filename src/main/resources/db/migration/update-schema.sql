ALTER TABLE shipment ADD customer_id INTEGER;

ALTER TABLE shipment ADD deliver_date TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE shipment ADD total_cost DECIMAL(10, 6);

ALTER TABLE shipment ALTER COLUMN  customer_id SET NOT NULL;

ALTER TABLE shipment ALTER COLUMN  deliver_date SET NOT NULL;

ALTER TABLE customer ADD last_delivery TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE customer ADD last_name VARCHAR(128);

ALTER TABLE customer ADD membership_id INTEGER;

ALTER TABLE customer ADD next_renewal TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE customer ALTER COLUMN  last_delivery SET NOT NULL;

ALTER TABLE customer ALTER COLUMN  last_name SET NOT NULL;

ALTER TABLE customer ALTER COLUMN  membership_id SET NOT NULL;

ALTER TABLE product ADD min_prio INTEGER;

ALTER TABLE product ALTER COLUMN  min_prio SET NOT NULL;

ALTER TABLE customer ALTER COLUMN  next_renewal SET NOT NULL;

ALTER TABLE shipment_products ADD product_id INTEGER;

ALTER TABLE shipment_products ADD shipment_id INTEGER;

ALTER TABLE shipment ALTER COLUMN  total_cost SET NOT NULL;

ALTER TABLE customer ADD CONSTRAINT FK_CUSTOMER_ON_MEMBERSHIPID FOREIGN KEY (membership_id) REFERENCES membership (id);

ALTER TABLE shipment ADD CONSTRAINT FK_SHIPMENT_ON_CUSTOMERID FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE shipment_products ADD CONSTRAINT FK_SHIPMENT_PRODUCTS_ON_PRODUCTID FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE shipment_products ADD CONSTRAINT FK_SHIPMENT_PRODUCTS_ON_SHIPMENTID FOREIGN KEY (shipment_id) REFERENCES shipment (id);

ALTER TABLE customer DROP CONSTRAINT customer_membershipId_fkey;

ALTER TABLE shipment DROP CONSTRAINT shipment_customerId_fkey;

ALTER TABLE shipment_products DROP CONSTRAINT shipment_products_productId_fkey;

ALTER TABLE shipment_products DROP CONSTRAINT shipment_products_shipmentId_fkey;

DROP TABLE _prisma_migrations CASCADE;

ALTER TABLE shipment_products DROP COLUMN productId;

ALTER TABLE shipment_products DROP COLUMN shipmentId;

ALTER TABLE shipment DROP COLUMN customerId;

ALTER TABLE shipment DROP COLUMN deliverDate;

ALTER TABLE shipment DROP COLUMN totalCost;

ALTER TABLE customer DROP COLUMN lastDelivery;

ALTER TABLE customer DROP COLUMN lastName;

ALTER TABLE customer DROP COLUMN membershipId;

ALTER TABLE customer DROP COLUMN nextRenewal;

ALTER TABLE product DROP COLUMN minPrio;

ALTER TABLE shipment_products ADD CONSTRAINT pk_shipment_products PRIMARY KEY (shipment_id, product_id);