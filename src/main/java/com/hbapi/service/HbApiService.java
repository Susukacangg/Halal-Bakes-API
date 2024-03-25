package com.hbapi.service;

import com.hbapi.entity.AdditiveEntity;
import hbapi.model.HalalNutriBaseModel;
import hbapi.model.OpenFoodFactsModel;
import hbapi.model.ProdInfoModel;
import hbapi.model.QuestionInfoModel;

import java.util.List;

public interface HbApiService {

    ProdInfoModel getProdInfo(String barcode);

    List<QuestionInfoModel> getAllQuestions();

    OpenFoodFactsModel getBarcodeInfo(String barcode);

    HalalNutriBaseModel getHalalNutriModel(String qStr);

    AdditiveEntity getEcode(String ecode);

}
