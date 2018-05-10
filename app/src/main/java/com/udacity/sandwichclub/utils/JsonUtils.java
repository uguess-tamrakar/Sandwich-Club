package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static String LOG_TAG = JsonUtils.class.getName();

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        // {"name":
        // {"mainName":"Ham and cheese sandwich",
        // "alsoKnownAs":[]},
        // "placeOfOrigin":"",
        // "description":"A ham and cheese sandwich is a common type of sandwich. It is made by putting cheese and sliced ham between two slices of bread.
        // The bread is sometimes buttered and/or toasted. Vegetables like lettuce, tomato, onion or pickle slices can also be included.
        // Various kinds of mustard and mayonnaise are also common.",
        // "image":"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG",
        // "ingredients":["Sliced bread","Cheese","Ham"]}

        try {
            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject nameJsonObj = jsonObject.getJSONObject("name");

                String mainName = nameJsonObj.optString("mainName");

                JSONArray alsoKnownAsArray = nameJsonObj.getJSONArray("alsoKnownAs");
                List<String> alsoKnownAs = new ArrayList<String>();

                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    alsoKnownAs.add(alsoKnownAsArray.optString(i));
                }

                String placeOfOrigin = jsonObject.optString("placeOfOrigin");
                String description = jsonObject.optString("description");
                String image = jsonObject.optString("image");

                JSONArray ingredientsJSONArray = jsonObject.getJSONArray("ingredients");
                List<String> ingredients = new ArrayList<String>();

                for (int i = 0; i < ingredientsJSONArray.length(); i++) {
                    ingredients.add(ingredientsJSONArray.optString(i));
                }

                sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
            }
        } catch (JSONException ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }

        return sandwich;
    }
}
