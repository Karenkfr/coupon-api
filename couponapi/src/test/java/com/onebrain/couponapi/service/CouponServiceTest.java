package com.onebrain.couponapi.service;

import com.onebrain.couponapi.domain.*;
import com.onebrain.couponapi.dto.CouponResponse;
import com.onebrain.couponapi.dto.CreateCouponRequest;
import com.onebrain.couponapi.exception.ResourceNotFoundException;
import com.onebrain.couponapi.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository repository;

    @InjectMocks
    private CouponService service;

    @Test
    void shouldCreateCoupon() {
        CreateCouponRequest request = new CreateCouponRequest(
                "ABC123",
                "Test coupon",
                BigDecimal.valueOf(10),
                Instant.now().plusSeconds(3600),
                true
        );

        Coupon savedCoupon = new Coupon(
                "ABC123",
                "Test coupon",
                BigDecimal.valueOf(10),
                Instant.now().plusSeconds(3600),
                true
        );

        when(repository.save(any(Coupon.class))).thenReturn(savedCoupon);

        CouponResponse response = service.create(request);

        assertEquals("ABC123", response.code());
        assertEquals(CouponStatus.ACTIVE, response.status());
        verify(repository).save(any(Coupon.class));
    }

    @Test
    void shouldFindCouponById() {
        UUID id = UUID.randomUUID();

        Coupon coupon = new Coupon(
                "ABC123",
                "Test",
                BigDecimal.valueOf(5),
                Instant.now().plusSeconds(3600),
                true
        );

        when(repository.findByIdAndStatusNot(id, CouponStatus.DELETED))
                .thenReturn(Optional.of(coupon));

        CouponResponse response = service.findById(id);

        assertEquals("ABC123", response.code());
    }

    @Test
    void shouldThrowExceptionWhenCouponNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.findByIdAndStatusNot(id, CouponStatus.DELETED))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(id)
        );
    }

    @Test
    void shouldDeleteCoupon() {
        UUID id = UUID.randomUUID();

        Coupon coupon = new Coupon(
                "ABC123",
                "Test",
                BigDecimal.valueOf(5),
                Instant.now().plusSeconds(3600),
                true
        );

        when(repository.findById(id)).thenReturn(Optional.of(coupon));

        service.delete(id);

        assertEquals(CouponStatus.DELETED, coupon.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingCoupon() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.delete(id)
        );
    }
}
