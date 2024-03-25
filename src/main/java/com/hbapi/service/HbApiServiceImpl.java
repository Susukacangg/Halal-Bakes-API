package com.hbapi.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbapi.entity.*;
import com.hbapi.feign.HalalNutritionFeign;
import com.hbapi.feign.OpenFoodFactsFeign;
import com.hbapi.repository.*;
import com.hbapi.util.HbApiUtil;
import hbapi.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HbApiServiceImpl implements HbApiService{

    private final OpenFoodFactsFeign openFoodFactsFeign;
    private final HalalNutritionFeign halalNutritionFeign;
    private final ProductInfoRepo productInfoRepo;
    private final AdditiveRepo additiveRepo;
    private final IngredientRepo ingredientRepo;
    private final ProductInfoIngredientRepo productInfoIngredientRepo;
    private final ProductInfoAdditiveRepo productInfoAdditiveRepo;
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;

    @Override
    public ProdInfoModel getProdInfo(String barcode) {
        Optional<ProductInfoEntity> productInfoOptional = productInfoRepo.findProductInfoByBarcode(barcode);

        if(productInfoOptional.isPresent()) {
            ProdInfoModel prodInfoModel = new ProdInfoModel();
            ProductInfoEntity productInfoEntity = productInfoOptional.get();

            prodInfoModel.setName(productInfoEntity.getName());
            prodInfoModel.setCompanyName(productInfoEntity.getCompanyName());
            prodInfoModel.setManuLocation(productInfoEntity.getManuLoc());
            boolean isProdHalal = true;

            // adding ingredients into a list
            List<ProdInfoIngredientModel> ingredientsList = new ArrayList<>();
            List<ProductInfoIngredientEntity> productInfoIngredientEntities = productInfoIngredientRepo.findProductInfoIngredientByBarcode(barcode);
            for(ProductInfoIngredientEntity infoIngredient: productInfoIngredientEntities) {
                Optional<IngredientEntity> ingredientOptional = ingredientRepo.findIngredientByIngredientId(infoIngredient.getIngredientId());
                IngredientEntity ingredientEntity = ingredientOptional.get();

                ProdInfoIngredientModel ingredientItem = new ProdInfoIngredientModel();
                ingredientItem.setName(ingredientEntity.getIngredientName());
                if(!ingredientEntity.getHalalStatus().equals("HALAL"))
                    isProdHalal = false;
                ingredientItem.setHalalStatus(ingredientEntity.getHalalStatus());

                ingredientsList.add(ingredientItem);
            }

            // adding additives into a list
            List<ProdInfoAdditiveModel> additivesList = new ArrayList<>();
            List<ProductInfoAdditiveEntity> productInfoAdditiveEntities = productInfoAdditiveRepo.findProductInfoAdditiveByBarcode(barcode);
            for(ProductInfoAdditiveEntity infoAdditive: productInfoAdditiveEntities) {
                Optional<AdditiveEntity> additiveOptional = additiveRepo.findEcodeByEcodeId(infoAdditive.getEcode());
                AdditiveEntity additiveEntity = additiveOptional.get();

                ProdInfoAdditiveModel additiveItem = new ProdInfoAdditiveModel();
                additiveItem.setName(additiveEntity.getEcodeId());
                additiveItem.setHalalStatus(additiveEntity.getHalalStatus());
                if(!additiveEntity.getHalalStatus().equals("HALAL"))
                    isProdHalal = false;
                additiveItem.setDescription(additiveEntity.getDescription());

                additivesList.add(additiveItem);
            }

            prodInfoModel.setIngredients(ingredientsList);
            prodInfoModel.setAdditives(additivesList);
            prodInfoModel.setIsHalal(isProdHalal);

            log.info(prodInfoModel.toString());
            return prodInfoModel;
        } else {
            OpenFoodFactsModel openFoodFactsModel = getBarcodeInfo(barcode);
            if(openFoodFactsModel.getStatusVerbose().equals("product found")) {
                List<IngredientModel> ingredientsToRemove = HbApiUtil.getIngredientsToRemove(openFoodFactsModel.getProduct().getIngredients());
                openFoodFactsModel.getProduct().getIngredients().removeAll(ingredientsToRemove);
            }
            log.info(openFoodFactsModel.toString());
        }

        return null;
    }

    @Override
    public List<QuestionInfoModel> getAllQuestions() {
        List<QuestionInfoModel> allQuestions = new ArrayList<>();

        List<QuestionEntity> allQuestionEntities = questionRepo.findAllQuestion();

        for(QuestionEntity questionEntity: allQuestionEntities) {
            QuestionInfoModel question = new QuestionInfoModel();
            question.setQuestion(questionEntity.getQuestion());
            question.setNumLikes(questionEntity.getNumLikes());
            question.setNumViews(questionEntity.getNumViews());

            List<AnswerInfoModel> allAnswersCurrQuestion = new ArrayList<>();
            List<AnswerEntity> allAnswerEntitiesCurrQuestion = answerRepo.findAllAnswerByQuestionIdFk(questionEntity.getQuestionId());
            for(AnswerEntity answerEntity: allAnswerEntitiesCurrQuestion) {
                AnswerInfoModel answer = new AnswerInfoModel();
                answer.setAnswer(answerEntity.getAnswer());
                answer.setAnswererName(answerEntity.getAnswererName());

                allAnswersCurrQuestion.add(answer);
            }

            question.setAnswerList(allAnswersCurrQuestion);

            allQuestions.add(question);
        }

        return allQuestions;
    }

    @Override
    public OpenFoodFactsModel getBarcodeInfo(String barcode) {
        String apiFields = "product_name,ingredients,additives_n,food_groups,allergens,alcohol,packaging_text,labels,links";

        OpenFoodFactsModel responseModel = new OpenFoodFactsModel();
        try {
            responseModel = openFoodFactsFeign.getBarcodeInfo(barcode, apiFields);
        } catch(Exception e) {
            responseModel.setStatusVerbose("product not found");
            log.info("dun have that barcode");
        }

        return responseModel;
    }

    @Override
    public HalalNutriBaseModel getHalalNutriModel(String qStr) {
        String jsonString = halalNutritionFeign.getBaseInfo(qStr);
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        HalalNutriBaseModel response = null;
        try {
            response = mapper.readValue(jsonString, HalalNutriBaseModel.class);
        } catch(Exception e) {
            log.error(e.getMessage());
        }
        return response;
    }

    @Override
    public AdditiveEntity getEcode(String ecode) {
        ecode = ecode.toLowerCase();
        if(!ecode.startsWith("e"))
            ecode = "E" + ecode.replaceAll("[^0-9]", "");

        log.info(ecode);
        Optional<AdditiveEntity> additiveOptional = additiveRepo.findEcodeByEcodeId(ecode);
        AdditiveEntity additiveEntity = null;
        if(additiveOptional.isPresent())
            additiveEntity = additiveOptional.get();

        return additiveEntity;
    }
}
