package com.hbapi.service;

import com.hbapi.feign.OpenFoodFactsFeign;
import hbapi.model.ProductInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HbApiServiceImpl implements HbApiService{

    private final OpenFoodFactsFeign openFoodFactsFeign;
    private final String apiFields =
            "product_name,ingredients,additives_n,food_groups,allergens,vegan,vegetarian,alcohol";

    @Override
    public ProductInfoModel getBarcodeInfo(String barcode) {
        return openFoodFactsFeign.getBarcodeInfo(barcode, apiFields);
    }
}
