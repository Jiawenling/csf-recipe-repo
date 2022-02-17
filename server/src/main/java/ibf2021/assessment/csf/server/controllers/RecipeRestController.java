package ibf2021.assessment.csf.server.controllers;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Optional;

/* Write your request hander in this file */
@RestController
@CrossOrigin
public class RecipeRestController {

    @Autowired
    RecipeService recipeService;

    @GetMapping(path="/api/recipe/{recipe_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRecipeById(@PathVariable String recipe_id){
        System.out.println(recipe_id);
        Optional<Recipe> recipeResult = this.recipeService.getRecipeById(recipe_id);
        if(recipeResult.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found!");
        }else{
            Recipe recipe = recipeResult.get();
            System.out.println(">>>>>>>>>>>>>>ingredients" + recipe.getIngredients());
            JsonObject recipeResultInJson = Json.createObjectBuilder()
                    .add("recipeId",recipe.getId())
                    .add("title",recipe.getTitle())
                    .add("image",recipe.getImage())
                    .add("instructions", recipe.getInstruction())
                    .add("ingredients", Json.createArrayBuilder(recipe.getIngredients()))
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(recipeResultInJson.toString());
        }
    }

    @PostMapping(path = "/api/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveRecipe(@RequestBody String payload){
        try (Reader reader = new StringReader(payload)) {
            JsonReader jReader = Json.createReader(reader);
            JsonObject recipe = jReader.readObject();
            Recipe recipeToSave = new Recipe();
            recipeToSave.setTitle(recipe.getString("title"));
            recipeToSave.setImage(recipe.getString("image"));
            recipeToSave.setInstruction(recipe.getString("instructions"));
            recipe.getJsonArray("ingredients").stream()
                    .forEach(v-> recipeToSave.addIngredient(v.toString()));
            System.out.println(recipeToSave);
            System.out.println(recipeToSave.getIngredients().get(0));
            JsonObject message = Json.createObjectBuilder()
                    .add("message", "Recipe saved")
                    .build();

            this.recipeService.addRecipe(recipeToSave);
            return ResponseEntity.status(HttpStatus.CREATED).body(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
            JsonObject message = Json.createObjectBuilder()
                    .add("message", "Something went wrong")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
        }
    }
}