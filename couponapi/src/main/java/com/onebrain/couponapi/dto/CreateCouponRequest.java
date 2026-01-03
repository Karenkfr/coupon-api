package com.onebrain.couponapi.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateCouponRequest(

        @NotBlank
        String code,

        @NotBlank
        String description,

        @NotNull
        @DecimalMin("0.5")
        BigDecimal discountValue,

        @NotNull
        Instant expirationDate,

        Boolean published
) {}
