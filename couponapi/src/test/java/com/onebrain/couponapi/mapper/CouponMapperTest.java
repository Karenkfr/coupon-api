package com.onebrain.couponapi.mapper;

import com.onebrain.couponapi.domain.*;
import com.onebrain.couponapi.dto.CouponResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CouponMapperTest {

    @Test
    void shouldMapCouponToResponse() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Test",
                BigDecimal.valueOf(10),
                Instant.now().plusSeconds(3600),
                true
        );

        CouponResponse response = CouponMapper.toResponse(coupon);

        assertEquals("ABC123", response.code());
        assertEquals(CouponStatus.ACTIVE, response.status());
    }
}

