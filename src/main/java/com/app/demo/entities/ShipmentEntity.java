package com.app.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "shipment", schema = "public")
public class ShipmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "\"totalCost\"", nullable = false, precision = 10, scale = 6)
    private BigDecimal totalCost;

    @Column(name = "\"deliverDate\"", nullable = false)
    private OffsetDateTime deliverDate;

    @JsonManagedReference
    @NotNull(message = "customer is mandatory")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "\"customerId\"", nullable = false)
    private CustomerEntity customer;


    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shipment_products",
            joinColumns = @JoinColumn(name = "\"shipmentId\""),
            inverseJoinColumns = @JoinColumn(name = "\"productId\""))
    private Set<ProductEntity> productEntities = new HashSet<>();
}