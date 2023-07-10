package com.app.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product", schema = "public")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "name is mandatory")
    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @NotNull(message = "cost is mandatory")
    @Column(name = "cost", nullable = false, precision = 10, scale = 6)
    private BigDecimal cost;

    @NotNull(message = "minPrio is mandatory")
    @Min(value = 1, message = "minprio min value must be greater than or equal to 1")
    @Max(value = 100, message = "minprio max value must be less than or equal to 100")
    @Column(name = "\"minPrio\"", nullable = false)
    private Integer minPrio;

    @JsonIgnore
    @ManyToMany(mappedBy = "productEntities")
    private Set<ShipmentEntity> shipmentEntities = new HashSet<>();
}