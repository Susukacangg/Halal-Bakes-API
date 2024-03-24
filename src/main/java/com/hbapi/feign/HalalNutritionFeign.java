package com.hbapi.feign;

import hbapi.model.HalalNutriBaseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "halal-nutrition", url = "${feign.halalnutrition.url}")
public interface HalalNutritionFeign {
    @GetMapping(path = "/apiv2/?q={queryStr}&result=15", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    String getBaseInfo(@PathVariable("queryStr") String queryStr);
}
