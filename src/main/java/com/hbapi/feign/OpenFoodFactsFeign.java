package com.hbapi.feign;

import hbapi.model.ProductInfoModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "open-food-facts", url = "${openfoodfacts.feign.url}")
public interface OpenFoodFactsFeign {

    @GetMapping(path = "/product/{barcodeNum}?fields={fields}")
    ProductInfoModel getBarcodeInfo(@PathVariable("barcodeNum") String barcodeNum, @PathVariable("fields") String fields);
}
