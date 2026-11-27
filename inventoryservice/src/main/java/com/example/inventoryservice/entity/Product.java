package com.example.inventoryservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private BigDecimal sellingPrice;

    private BigDecimal purchasePrice;

    @NotNull
    private BigDecimal mrp;

    private BigDecimal rupeeDiscount;

    private BigDecimal percentDiscount;

    @NotBlank
    @Size(max = 100)
    private String manufacturer;

    private LocalDate mfg;

    private LocalDate expiresAt;

    private boolean isExpired;

    @NotBlank
    private String batch;

    private int stock;

    @NotBlank
    private String barcode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}