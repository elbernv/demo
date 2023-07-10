package com.app.demo.services;

import com.app.demo.dto.ShipmentDto;
import com.app.demo.entities.CustomerEntity;
import com.app.demo.entities.MembershipEntity;
import com.app.demo.entities.ProductEntity;
import com.app.demo.entities.ShipmentEntity;
import com.app.demo.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    public HashMap<String, Object> createShipment(ShipmentDto shipment){
        CustomerEntity customer = this.customerService.validateCustomer(shipment.getCustomerId());
        this.validateCustomerNextRenewal (customer.getNextRenewal());

        MembershipEntity membership = customer.getMembership();
        Set<ProductEntity> products = this.validateProducts(shipment.getProductsId(), membership.getPrio());
        ShipmentEntity shipmentEntity = new ShipmentEntity();

        BigDecimal totalCost = this.calculateTotalCost(products);

        ZoneOffset zoneOffset = ZoneOffset.of("-04:00");
        LocalDateTime now = LocalDateTime.now();
        OffsetDateTime offsetDateTime = now.atOffset(zoneOffset);

        shipmentEntity.setTotalCost(totalCost);
        shipmentEntity.setDeliverDate(offsetDateTime);
        shipmentEntity.setCustomer(customer);
        shipmentEntity.setProductEntities(products);

        return this.shipmentRepository.createShipment(shipmentEntity, products);
    }

    private Set<ProductEntity> validateProducts(List<Integer> productIds, Integer membershipPrio){
        Set<ProductEntity> products = new HashSet<>();

        productIds.forEach((productId)->{
            ProductEntity product = this.productService.validateProduct(productId);

            if(membershipPrio > product.getMinPrio() ){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "some products do not meet the requirements");
            }

            products.add(product);
        });

        return products;
    }

    private void validateCustomerNextRenewal( OffsetDateTime nextRenewal){
        ZoneOffset zoneOffset = ZoneOffset.of("-04:00");
        LocalDateTime now = LocalDateTime.now();
        OffsetDateTime offsetDateTime = now.atOffset(zoneOffset);

        if(nextRenewal.isBefore(offsetDateTime)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "your membership has expired");
        }
    }

    private BigDecimal calculateTotalCost(Set<ProductEntity> products){
        BigDecimal totalCost = new BigDecimal(0);

        for(ProductEntity product: products){
            totalCost = totalCost.add(product.getCost());
        }

        return totalCost;
    }

    public List<ShipmentEntity> getAllShipments(){
        return this.shipmentRepository.getAllShipments();
    }

    public ShipmentEntity updateShipment(ShipmentEntity shipment, int id){
        this.validateShipment(id);
        shipment.setId(id);
        return this.shipmentRepository.updateShipment(shipment);
    }

    public ShipmentEntity updateShipment(ShipmentEntity shipment){
        return this.shipmentRepository.updateShipment(shipment);
    }

    private ShipmentEntity validateShipment(int id){
        ShipmentEntity shipment = this.shipmentRepository.findShipmentById(id);

        if(shipment == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "shipment Not Found");
        }

        return shipment;
    }

    public ShipmentEntity getShipmentById(int id){
        return this.validateShipment(id);
    }

    public HashMap<String, String> deleteShipment(int id){
        try {
            ShipmentEntity shipment = this.validateShipment(id);
            this.shipmentRepository.deleteShipment(shipment);
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "shipment deleted");

            return response;
        }
        catch (DataIntegrityViolationException err){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "xd");
        }
    }
}
