package com.app.demo.dao;

import com.app.demo.entities.ProductEntity;
import com.app.demo.entities.ShipmentEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ShipmentDao {
    HashMap<String, Object> createShipment(ShipmentEntity shipment, Set<ProductEntity> products);

    ShipmentEntity findShipmentById(int id);

    List<ShipmentEntity> getAllShipments();

    ShipmentEntity updateShipment(ShipmentEntity shipment);

    void deleteShipment(ShipmentEntity shipment);
}
