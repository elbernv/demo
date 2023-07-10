-- CreateTable
CREATE TABLE "customer" (
    "id" SERIAL NOT NULL,
    "name" VARCHAR(128) NOT NULL,
    "lastName" VARCHAR(128),
    "dni" VARCHAR(128),
    "email" VARCHAR(128) NOT NULL,
    "lastDelivery" TIMESTAMPTZ(3),
    "nextRenewal" TIMESTAMPTZ(3),
    "membershipId" INTEGER NOT NULL,

    CONSTRAINT "customer_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "membership" (
    "id" SERIAL NOT NULL,
    "key" VARCHAR(128) NOT NULL,
    "name" VARCHAR(128) NOT NULL,
    "prio" INTEGER NOT NULL,
    "duration" BIGINT NOT NULL,

    CONSTRAINT "membership_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "product" (
    "id" SERIAL NOT NULL,
    "name" VARCHAR(256) NOT NULL,
    "cost" DECIMAL(10,6) NOT NULL,
    "minPrio" INTEGER NOT NULL,

    CONSTRAINT "product_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "shipment" (
    "id" SERIAL NOT NULL,
    "totalCost" DECIMAL(10,6) NOT NULL,
    "deliverDate" TIMESTAMPTZ(3) NOT NULL,
    "customerId" INTEGER NOT NULL,

    CONSTRAINT "shipment_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "shipment_products" (
    "shipmentId" INTEGER NOT NULL,
    "productId" INTEGER NOT NULL,

    CONSTRAINT "shipment_products_pkey" PRIMARY KEY ("shipmentId","productId")
);

-- AddForeignKey
ALTER TABLE "customer" ADD CONSTRAINT "customer_membershipId_fkey" FOREIGN KEY ("membershipId") REFERENCES "membership"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "shipment" ADD CONSTRAINT "shipment_customerId_fkey" FOREIGN KEY ("customerId") REFERENCES "customer"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "shipment_products" ADD CONSTRAINT "shipment_products_shipmentId_fkey" FOREIGN KEY ("shipmentId") REFERENCES "shipment"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "shipment_products" ADD CONSTRAINT "shipment_products_productId_fkey" FOREIGN KEY ("productId") REFERENCES "product"("id") ON DELETE RESTRICT ON UPDATE CASCADE;
