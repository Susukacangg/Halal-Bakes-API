package com.hbapi.util;

import hbapi.model.IngredientModel;

import java.util.ArrayList;
import java.util.List;

public class HbApiUtil {
    public static List<IngredientModel> getIngredientsToRemove(List<IngredientModel> ingredientsList) {
        List<IngredientModel> ingredientsToRemove = new ArrayList<>();
        for (int i = 0; i < ingredientsList.size(); i++) {
            if(!ingredientsList.get(i).getId().split(":")[0].equals("en"))
                ingredientsToRemove.add(ingredientsList.get(i));
        }

        return ingredientsToRemove;
    }
}
