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

    @Override
    public ProdInfoModel getProdInfo(String barcode) {
        Optional<ProductInfo> productInfoOptional = productInfoRepo.findProductInfoByBarcode(barcode);

        if(productInfoOptional.isPresent()) {
            ProdInfoModel prodInfoModel = new ProdInfoModel();
            ProductInfo productInfo = productInfoOptional.get();

            prodInfoModel.setName(productInfo.getName());
            prodInfoModel.setCompanyName(productInfo.getCompanyName());
            prodInfoModel.setManuLocation(productInfo.getManuLoc());
            boolean isProdHalal = true;

            // adding ingredients into a list
            List<ProdInfoIngredientModel> ingredientsList = new ArrayList<>();
            List<ProductInfoIngredient> productInfoIngredients = productInfoIngredientRepo.findProductInfoIngredientByBarcode(barcode);
            for(ProductInfoIngredient infoIngredient: productInfoIngredients) {
                Optional<Ingredient> ingredientOptional = ingredientRepo.findIngredientByIngredientId(infoIngredient.getIngredientId());
                Ingredient ingredient = ingredientOptional.get();

                ProdInfoIngredientModel ingredientItem = new ProdInfoIngredientModel();
                ingredientItem.setName(ingredient.getIngredientName());
                if(!ingredient.getHalalStatus().equals("HALAL"))
                    isProdHalal = false;
                ingredientItem.setHalalStatus(ingredient.getHalalStatus());

                ingredientsList.add(ingredientItem);
            }

            // adding additives into a list
            List<ProdInfoAdditiveModel> additivesList = new ArrayList<>();
            List<ProductInfoAdditive> productInfoAdditives = productInfoAdditiveRepo.findProductInfoAdditiveByBarcode(barcode);
            for(ProductInfoAdditive infoAdditive: productInfoAdditives) {
                Optional<Additive> additiveOptional = additiveRepo.findEcodeByEcodeId(infoAdditive.getEcode());
                Additive additive = additiveOptional.get();

                ProdInfoAdditiveModel additiveItem = new ProdInfoAdditiveModel();
                additiveItem.setName(additive.getEcodeId());
                additiveItem.setHalalStatus(additive.getHalalStatus());
                if(!additive.getHalalStatus().equals("HALAL"))
                    isProdHalal = false;
                additiveItem.setDescription(additive.getDescription());

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
    public Additive getEcode(String ecode) {
        ecode = ecode.toLowerCase();
        if(!ecode.startsWith("e"))
            ecode = "E" + ecode.replaceAll("[^0-9]", "");

        log.info(ecode);
        Optional<Additive> additiveOptional = additiveRepo.findEcodeByEcodeId(ecode);
        Additive additive = null;
        if(additiveOptional.isPresent())
            additive = additiveOptional.get();

        return additive;
    }
}
