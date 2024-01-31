package com.hbapi.controller;

import com.hbapi.service.HbApiService;
import hbapi.api.Hbv1Api;
import hbapi.model.ProductInfoModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HbApiController implements Hbv1Api {

    private final HbApiService hbApiService;

    @Override
    public ResponseEntity<ProductInfoModel> getBarcodeInfo(String barcode) {
        log.info("Getting product information from barcode...");
        return ResponseEntity.ok(hbApiService.getBarcodeInfo(barcode));
    }
}
