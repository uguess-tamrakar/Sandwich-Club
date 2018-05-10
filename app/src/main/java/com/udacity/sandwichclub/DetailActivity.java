package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView originTv = findViewById(R.id.origin_tv);
        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        originTv.setText(sandwich.getPlaceOfOrigin().isEmpty() ? "N/A" : sandwich.getPlaceOfOrigin());

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        String alsoKnownAs = "";
        for (int i = 0; i < alsoKnownAsList.size(); i++) {
            alsoKnownAs += "\r\n" + alsoKnownAsList.get(i);
        }

        alsoKnownAsTv.setText(alsoKnownAs.trim().isEmpty() ? "N/A" : alsoKnownAs.trim());

        List<String> ingredientsList = sandwich.getIngredients();
        String ingredients = "";
        for (int i = 0; i < ingredientsList.size(); i++) {
            ingredients += ingredientsList.get(i) + "\r\n";
        }

        ingredientsTv.setText(ingredients.trim().isEmpty() ? "N/A" : ingredients.trim());
        descriptionTv.setText(sandwich.getDescription().isEmpty() ? "N/A" : sandwich.getDescription().trim());
    }
}
