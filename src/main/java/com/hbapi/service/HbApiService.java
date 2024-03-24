package com.hbapi.service;

import com.hbapi.entity.Additive;
import hbapi.model.HalalNutriBaseModel;
import hbapi.model.OpenFoodFactsModel;
import hbapi.model.ProdInfoModel;

public interface HbApiService {

    ProdInfoModel getProdInfo(String barcode);

    OpenFoodFactsModel getBarcodeInfo(String barcode);

    HalalNutriBaseModel getHalalNutriModel(String qStr);

    Additive getEcode(String ecode);

}
