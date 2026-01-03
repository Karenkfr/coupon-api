package com.onebrain.couponapi.controller;

import com.onebrain.couponapi.domain.CouponStatus;
import com.onebrain.couponapi.dto.CreateCouponRequest;
import com.onebrain.couponapi.dto.CouponResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CouponControllerHttpIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void fullCrudFlowIntegrationTest() {

        CreateCouponRequest createRequest = new CreateCouponRequest(
                "HTT123",
                "HTTP Integration Test",
                BigDecimal.valueOf(5),
                Instant.now().plusSeconds(3600),
                true
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateCouponRequest> createEntity = new HttpEntity<>(createRequest, headers);

        ResponseEntity<CouponResponse> createResponse = restTemplate.postForEntity(
                "/coupon",
                createEntity,
                CouponResponse.class
        );

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        CouponResponse created = createResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.code()).isEqualTo("HTT123");
        assertThat(created.description()).isEqualTo("HTTP Integration Test");
        assertThat(created.status()).isEqualTo(CouponStatus.ACTIVE);

        UUID couponId = created.id();

        ResponseEntity<CouponResponse> getResponse = restTemplate.getForEntity(
                "/coupon/{id}",
                CouponResponse.class,
                couponId
        );

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        CouponResponse fetched = getResponse.getBody();
        assertThat(fetched).isNotNull();
        assertThat(fetched.id()).isEqualTo(couponId);
        assertThat(fetched.code()).isEqualTo("HTT123");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/coupon/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                couponId
        );

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


        ResponseEntity<CouponResponse> getAfterDelete = restTemplate.getForEntity(
                "/coupon/{id}",
                CouponResponse.class,
                couponId
        );
        
        assertThat(getAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
