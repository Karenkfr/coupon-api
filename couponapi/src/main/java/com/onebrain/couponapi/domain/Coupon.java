package com.onebrain.couponapi.domain;

import com.onebrain.couponapi.exception.BusinessException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 6, unique = true)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(nullable = false)
    private Instant expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus status;

    @Column(nullable = false)
    private boolean published;

    @Column(nullable = false)
    private boolean redeemed;

    protected Coupon() {
    }

    public Coupon(
            String code,
            String description,
            BigDecimal discountValue,
            Instant expirationDate,
            boolean published
    ) {
        this.code = sanitizeCode(code);
        this.description = description;
        validateDiscount(discountValue);
        validateExpiration(expirationDate);

        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.published = published;
        this.status = CouponStatus.ACTIVE;
        this.redeemed = false;
    }

    private String sanitizeCode(String code) {
        String sanitized = code.replaceAll("[^a-zA-Z0-9]", "");

        if (sanitized.length() != 6) {
            throw new BusinessException("Coupon code must have exactly 6 alphanumeric characters");
        }
        return sanitized;
    }

    private void validateDiscount(BigDecimal value) {
        if (value.compareTo(BigDecimal.valueOf(0.5)) < 0) {
            throw new BusinessException("Discount value must be at least 0.5");
        }
    }

    private void validateExpiration(Instant expiration) {
        if (expiration.isBefore(Instant.now())) {
            throw new BusinessException("Expiration date cannot be in the past");
        }
    }

    public void delete() {
        if (this.status == CouponStatus.DELETED) {
            throw new BusinessException("Coupon already deleted");
        }
        this.status = CouponStatus.DELETED;
    }

    public UUID getId() { return id; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public BigDecimal getDiscountValue() { return discountValue; }
    public Instant getExpirationDate() { return expirationDate; }
    public CouponStatus getStatus() { return status; }
    public boolean isPublished() { return published; }
    public boolean isRedeemed() { return redeemed; }
}
