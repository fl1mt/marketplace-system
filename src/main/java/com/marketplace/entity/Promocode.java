package com.marketplace.entity;
import com.marketplace.discount.DiscountPromocodeType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "promocodes")
public class Promocode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountPromocodeType type;
    @Column(nullable = false)
    private BigDecimal value;
    @Column(nullable = false)
    private Long maxActivationsCount;
    @Column(nullable = false)
    private Long activationsCount = 0L;
    @Column(nullable = false)
    private Long activationsPerUser = 1L;
    @Column(name = "valid_from", nullable = false)
    private LocalDateTime validFrom;
    @Column(name = "valid_until")
    private LocalDateTime validUntil;
    @Column
    private Integer countryId;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(DiscountPromocodeType type) {
        this.type = type;
    }

    public DiscountPromocodeType getType() {
        return type;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setMaxActivationsCount(Long maxActivationsCount) {
        this.maxActivationsCount = maxActivationsCount;
    }

    public Long getMaxActivationsCount() {
        return maxActivationsCount;
    }

    public void setActivationsCount(Long activationsCount) {
        this.activationsCount = activationsCount;
    }

    public Long getActivationsCount() {
        return activationsCount;
    }

    public void setActivationsPerUser(Long activationsPerUser) {
        this.activationsPerUser = activationsPerUser;
    }

    public Long getActivationsPerUser() {
        return activationsPerUser;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom){
        this.validFrom = validFrom;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getCountryId() {
        return countryId;
    }
}