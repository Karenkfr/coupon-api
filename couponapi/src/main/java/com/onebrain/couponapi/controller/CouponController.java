package com.onebrain.couponapi.controller;

import com.onebrain.couponapi.dto.CreateCouponRequest;
import com.onebrain.couponapi.dto.CouponResponse;
import com.onebrain.couponapi.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService service;

    public CouponController(CouponService service) {
        this.service = service;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CouponResponse create(@RequestBody @Valid CreateCouponRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public CouponResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
