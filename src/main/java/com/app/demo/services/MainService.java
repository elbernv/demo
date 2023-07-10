package com.app.demo.services;

import com.app.demo.dto.RemoveProductDto;
import com.app.demo.dto.RenewMembershipDto;
import com.app.demo.entities.CustomerEntity;
import com.app.demo.entities.MembershipEntity;
import com.app.demo.entities.ProductEntity;
import com.app.demo.entities.ShipmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MainService {
    @Autowired
    ShipmentService shipmentService;

    @Autowired
    MembershipService membershipService;

    @Autowired
    CustomerService customerService;

    public ShipmentEntity removeProduct(RemoveProductDto body){
        HashMap<String, Object> response = new HashMap<>();
        ShipmentEntity shipmentEntity = this.shipmentService.getShipmentById(body.getShipmentId());
        Set<ProductEntity> productEntitySet = shipmentEntity.getProductEntities();

        Set<ProductEntity> productEntitySetFiltered = productEntitySet.stream().filter(product -> product.getId() != body.getProductId()).collect(Collectors.toSet());
        shipmentEntity.setProductEntities(productEntitySetFiltered);

        ShipmentEntity updatedShipment = this.shipmentService.updateShipment(shipmentEntity);

        return updatedShipment;
    }

    public HashMap<String, String> renewMembership(RenewMembershipDto body){
        CustomerEntity customer =  this.customerService.validateCustomer(body.getCustomerId());
        MembershipEntity membership = this.membershipService.validateMembership(body.getMembershipId());
        HashMap<String, String> response = new HashMap<>();

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        ZoneOffset zoneOffset = ZoneOffset.of("-04:00");

        calendar.add(Calendar.DATE, Long.valueOf(membership.getDuration()).intValue());

        customer.setMembership(membership);
        customer.setNextRenewal(calendar.toInstant().atOffset(zoneOffset));

        this.customerService.updateCustomer(customer);

        response.put("message", "ok");

        return response;
    }
}
