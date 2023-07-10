package com.app.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "membership", schema = "public")
public class MembershipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "key is mandatory")
    @Column(name = "key", nullable = false, length = 128)
    private String key;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @NotNull(message = "Please enter a valid prio")
    @Min(value = 1, message = "prio min value must be greater than or equal to 1")
    @Max(value = 100, message = "prio max value must be less than or equal to 100")
    @Column(name = "prio", nullable = false)
    private Integer prio;

    @NotNull(message = "Please enter a valid duration")
    @Column(name = "duration", nullable = false)
    private Long duration;

    @JsonBackReference
    @OneToMany(mappedBy = "membership")
    private Set<CustomerEntity> customers = new LinkedHashSet<>();
}