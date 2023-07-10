package com.app.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customer", schema = "public")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "\"lastName\"", nullable = true, length = 128)
    private String lastName;

    @Column(name = "dni", nullable = true, length = 128)
    private String dni;

    @NotBlank(message = "Email is mandatory")
    @Column(name = "email", nullable = false, length = 128)
    private String email;

    @Column(name = "\"lastDelivery\"", nullable = true)
    private OffsetDateTime lastDelivery;

    @Column(name = "\"nextRenewal\"", nullable = true)
    private OffsetDateTime nextRenewal;

    @JsonIgnore
    @NotNull(message = "Please enter a valid membership")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"membershipId\"", nullable = false)
    private MembershipEntity membership;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<ShipmentEntity> shipmentEntities = new LinkedHashSet<>();

}