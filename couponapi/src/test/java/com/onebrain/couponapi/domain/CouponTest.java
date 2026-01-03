package com.onebrain.couponapi.domain;

import com.onebrain.couponapi.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @Test
    void shouldCreateCouponWithValidData() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Black Friday",
                BigDecimal.valueOf(10),
                Instant.now().plusSeconds(3600),
                true
        );

        assertEquals("ABC123", coupon.getCode());
        assertEquals(CouponStatus.ACTIVE, coupon.getStatus());
        assertTrue(coupon.isPublished());
        assertFalse(coupon.isRedeemed());
    }

    @Test
    void shouldSanitizeCouponCode() {
        Coupon coupon = new Coupon(
                "A-B C!1@2#3",
                "Test",
                BigDecimal.valueOf(5),
                Instant.now().plusSeconds(3600),
                false
        );

        assertEquals("ABC123", coupon.getCode());
    }

    @Test
    void shouldThrowExceptionWhenCodeIsInvalidLength() {
        String code = "ABC12"; // inválido
        String description = "Test";
        BigDecimal discountValue = BigDecimal.valueOf(5);
        Instant expirationDate = Instant.now().plusSeconds(3600);
        boolean published = true;

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> new Coupon(code, description, discountValue, expirationDate, published)
        );

        assertEquals(
                "Coupon code must have exactly 6 alphanumeric characters",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsTooLow() {
        String code = "ABC123";
        String description = "Test";
        BigDecimal discountValue = BigDecimal.valueOf(0.1);
        Instant expirationDate = Instant.now().plusSeconds(3600);
        boolean published = true;

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> new Coupon(code, description, discountValue, expirationDate, published)
        );

        assertEquals("Discount value must be at least 0.5", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenExpirationIsInPast() {
        String code = "ABC123";
        String description = "Test";
        BigDecimal discount = BigDecimal.valueOf(5);
        Instant expiration = Instant.now().minusSeconds(3600); // passado
        boolean active = true;

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> new Coupon(code, description, discount, expiration, active)
        );

        assertEquals("Expiration date cannot be in the past", exception.getMessage());
    }

    @Test
    void shouldDeleteCoupon() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Test",
                BigDecimal.valueOf(5),
                Instant.now().plusSeconds(3600),
                true
        );

        coupon.delete();

        assertEquals(CouponStatus.DELETED, coupon.getStatus());
    }

    @Test
    void shouldNotDeleteCouponTwice() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Test",
                BigDecimal.valueOf(5),
                Instant.now().plusSeconds(3600),
                true
        );

        coupon.delete();

        BusinessException exception = assertThrows(
                BusinessException.class,
                coupon::delete
        );

        assertEquals("Coupon already deleted", exception.getMessage());
    }
}
