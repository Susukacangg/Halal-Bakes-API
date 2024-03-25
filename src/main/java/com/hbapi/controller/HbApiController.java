package com.hbapi.controller;

import com.hbapi.service.HbApiService;
import hbapi.api.Hbv1Api;
import hbapi.model.HalalNutriBaseModel;
import hbapi.model.OpenFoodFactsModel;
import hbapi.model.ProdInfoModel;
import hbapi.model.QuestionInfoModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HbApiController implements Hbv1Api {

    private final HbApiService hbApiService;

    @Override
    public ResponseEntity<ProdInfoModel> getProdInfo(String barcode) {
        log.info("Getting full product info...");
        return ResponseEntity.ok(hbApiService.getProdInfo(barcode));
    }

    @Override
    public ResponseEntity<List<QuestionInfoModel>> getAllQuestions() {
        log.info("Getting all questions and answers...");
        return ResponseEntity.ok(hbApiService.getAllQuestions());
    }

    @Override
    public ResponseEntity<OpenFoodFactsModel> getOpenFoodFactsModel(String barcode) {
        log.info("Getting product information from barcode...");
        return ResponseEntity.ok(hbApiService.getBarcodeInfo(barcode));
    }

    @Override
    public ResponseEntity<HalalNutriBaseModel> getBaseInfo(String qStr) {
        log.info("Getting base information of query string...");
        return ResponseEntity.ok(hbApiService.getHalalNutriModel(qStr));
    }
}
