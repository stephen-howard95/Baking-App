package com.example.bakingapp;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static final String LOG_TAG = MainActivity.class.getName();

    public static List<Recipe> parseRecipeJson(String json) throws JSONException{

        if(TextUtils.isEmpty(json)){
            return null;
        }

        List<Recipe> recipes = new ArrayList<>();

        try{
            JSONArray baseJsonResponse = new JSONArray(json);

            for(int i=0; i<baseJsonResponse.length(); i++){

                JSONObject currentRecipe = baseJsonResponse.getJSONObject(i);

                int id = currentRecipe.getInt("id");
                String name = currentRecipe.getString("name");
                int servings = currentRecipe.getInt("servings");
                String image = currentRecipe.getString("image");

                JSONArray ingredients = currentRecipe.getJSONArray("ingredients");
                ArrayList<Ingredient> ingredientsList = new ArrayList<>();
                for(int j=0; j<ingredients.length(); j++){
                    JSONObject currentIngredient = ingredients.getJSONObject(j);

                    double quantity = currentIngredient.getDouble("quantity");
                    String measure = currentIngredient.getString("measure");
                    String ingredient = currentIngredient.getString("ingredient");

                    Ingredient newIngredient = new Ingredient(quantity, measure, ingredient);

                    ingredientsList.add(newIngredient);
                }

                JSONArray steps = currentRecipe.getJSONArray("steps");
                ArrayList<Step> stepsList = new ArrayList<Step>();
                for(int j=0; j<steps.length(); j++){
                    JSONObject currentStep = steps.getJSONObject(j);

                    int stepId = currentStep.getInt("id");
                    String shortDescription = currentStep.getString("shortDescription");
                    String fullDescription = currentStep.getString("description");
                    String videoUrl = currentStep.getString("videoURL");
                    String thumbnailUrl = currentStep.getString("thumbnailURL");

                    Step newStep = new Step(stepId, shortDescription, fullDescription, videoUrl, thumbnailUrl);
                    stepsList.add(newStep);
                }
                Recipe newRecipe = new Recipe(id, name, ingredientsList, stepsList, servings, image);

                recipes.add(newRecipe);
            }

        } catch (JSONException e){
            Log.e("JsonUtils", "Problem parsing the JSON results", e);
        }
        return recipes;
    }
    public static List<Recipe> fetchRecipeList(String requestUrl) throws JSONException {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Recipe> recipesJson = parseRecipeJson(jsonResponse);

        return recipesJson;
    }
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
