package com.onebrain.couponapi.repository;

import com.onebrain.couponapi.domain.Coupon;
import com.onebrain.couponapi.domain.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    Optional<Coupon> findByIdAndStatusNot(UUID id, CouponStatus status);

}
