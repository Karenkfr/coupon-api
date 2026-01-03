package com.onebrain.couponapi.service;

import com.onebrain.couponapi.domain.Coupon;
import com.onebrain.couponapi.domain.CouponStatus;
import com.onebrain.couponapi.dto.CreateCouponRequest;
import com.onebrain.couponapi.dto.CouponResponse;
import com.onebrain.couponapi.exception.ResourceNotFoundException;
import com.onebrain.couponapi.repository.CouponRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CouponService {

    private final CouponRepository repository;

    public CouponService(CouponRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CouponResponse create(CreateCouponRequest request) {
        Coupon coupon = new Coupon(
                request.code(),
                request.description(),
                request.discountValue(),
                request.expirationDate(),
                Boolean.TRUE.equals(request.published())
        );
        return CouponResponse.from(repository.save(coupon));
    }

    public CouponResponse findById(UUID id) {
        Coupon coupon = repository.findByIdAndStatusNot(id, CouponStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));

        return CouponResponse.from(coupon);
    }

    @Transactional
    public void delete(UUID id) {
        Coupon coupon = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));
        coupon.delete();
    }
}
