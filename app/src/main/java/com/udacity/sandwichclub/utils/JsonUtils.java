package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try{

            JSONObject rootObject = new JSONObject(json);

            JSONObject name = rootObject.getJSONObject("name");
            String mainName = name.getString("mainName");


            JSONArray alsoKnownAsJSONArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i <  alsoKnownAsJSONArray.length(); i++){
                String alsoKnownAsItem = alsoKnownAsJSONArray.getString(i);
                alsoKnownAs.add(alsoKnownAsItem);
            }

            String placeOfOrigin = rootObject.getString("placeOfOrigin");

            String description = rootObject.getString("description");

            String image = rootObject.getString("image");


            JSONArray ingredientsJSONArray = rootObject.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i <  ingredientsJSONArray.length(); i++){
                String ingredientsItem = ingredientsJSONArray.getString(i);
                ingredients.add(ingredientsItem);
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        }catch (JSONException e){
            Log.e("JsonUtils", "Parsing problems", e);
            return null;
        }

    }
}
