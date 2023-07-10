package com.app.demo.repositories;


import com.app.demo.dao.ShipmentDao;
import com.app.demo.entities.ProductEntity;
import com.app.demo.entities.ShipmentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
@Transactional()
public class ShipmentRepository implements ShipmentDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public HashMap<String, Object> createShipment(ShipmentEntity shipment, Set<ProductEntity> products) {
        HashMap<String, Object> response = new HashMap<>();


        ShipmentEntity newShipment = entityManager.merge(shipment);

        response.put("shipment", newShipment);

        return response;
    }

    @Override
    public ShipmentEntity findShipmentById(int id) {
        return entityManager.find(ShipmentEntity.class, id);
    }

    @Override
    public List<ShipmentEntity> getAllShipments() {
        return entityManager.createQuery("FROM ShipmentEntity").getResultList();
    }

    @Override
    public ShipmentEntity updateShipment(ShipmentEntity shipment) {
        return entityManager.merge(shipment);
    }

    @Override
    public void deleteShipment(ShipmentEntity shipment) {
        entityManager.remove(shipment);
    }
}
