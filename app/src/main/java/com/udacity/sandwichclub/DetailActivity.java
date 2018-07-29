package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView ingredientsIv;
    TextView originLabelTv;
    TextView originTv;
    TextView alsoKnownAsLabelTv;
    TextView alsoKnownAsTv;
    TextView descriptionLabelTv;
    TextView descriptionTv;
    TextView ingredientsLabelTv;
    TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        originLabelTv = findViewById(R.id.origin_label_tv);
        alsoKnownAsLabelTv = findViewById(R.id.also_known_label_tv);
        descriptionLabelTv = findViewById(R.id.description_label_tv);
        ingredientsLabelTv = findViewById(R.id.ingredients_label_tv);

        ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        setTitle(sandwich.getMainName());
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv, new Callback() {
                    @Override
                    public void onSuccess() {
                        ingredientsIv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        ingredientsIv.setVisibility(View.GONE);
                    }
                });



        if(sandwich.getPlaceOfOrigin().isEmpty()){
            originLabelTv.setVisibility(View.GONE);
            originTv.setVisibility(View.GONE);

        }else {
            originLabelTv.setVisibility(View.VISIBLE);
            originTv.setVisibility(View.VISIBLE);
            originTv.setText(sandwich.getPlaceOfOrigin());;
        }


        if(sandwich.getDescription().isEmpty()){
            descriptionLabelTv.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);

        }else {
            descriptionLabelTv.setVisibility(View.VISIBLE);
            descriptionTv.setVisibility(View.VISIBLE);
            descriptionTv.setText(sandwich.getDescription());
        }


        if((null != sandwich.getAlsoKnownAs()) && (sandwich.getAlsoKnownAs().size() > 0)){
            StringBuilder alsoKnownAsBuilder = new StringBuilder();

            for (String alsoKnownAs : sandwich.getAlsoKnownAs()){
                alsoKnownAsBuilder.append(alsoKnownAs).append(", ");;
            }

            alsoKnownAsBuilder.setLength(alsoKnownAsBuilder.length() - 2);
            alsoKnownAsLabelTv.setVisibility(View.VISIBLE);
            alsoKnownAsTv.setVisibility(View.VISIBLE);
            alsoKnownAsTv.setText(alsoKnownAsBuilder);

        }else{
            alsoKnownAsLabelTv.setVisibility(View.GONE);
            alsoKnownAsTv.setVisibility(View.GONE);
        }


        if((null != sandwich.getIngredients()) && (sandwich.getIngredients().size() > 0)){
            StringBuilder ingredientsBuilder = new StringBuilder();

            for (String ingredient : sandwich.getIngredients()){
                ingredientsBuilder.append(ingredient).append(", ");;
            }

            ingredientsBuilder.setLength(ingredientsBuilder.length() - 2);
            ingredientsLabelTv.setVisibility(View.VISIBLE);
            ingredientsTv.setVisibility(View.VISIBLE);
            ingredientsTv.setText(ingredientsBuilder);

        }else{
            ingredientsLabelTv.setVisibility(View.GONE);
            ingredientsTv.setVisibility(View.GONE);
        }


    }
}
