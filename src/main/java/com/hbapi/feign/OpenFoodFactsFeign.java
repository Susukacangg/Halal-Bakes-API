package com.hbapi.feign;

import hbapi.model.OpenFoodFactsModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "open-food-facts", url = "${feign.openfoodfacts.url}")
public interface OpenFoodFactsFeign {

    @GetMapping(path = "/product/{barcodeNum}?fields={fields}")
    OpenFoodFactsModel getBarcodeInfo(@PathVariable("barcodeNum") String barcodeNum, @PathVariable("fields") String fields);
}
