package ibf2021.assessment.csf.server.controllers;

/* Write your request hander in this file */

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class RecipesRestController {

    @Autowired
    RecipeService recipeService;

    @GetMapping(path="/api/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRecipes(){
        List<Recipe> recipeFullResults = this.recipeService.getAllRecipes();
        System.out.println(recipeFullResults);
        JsonArrayBuilder arrayBuilder =  Json.createArrayBuilder();
                recipeFullResults.stream().forEach(v -> {
                    System.out.println(v.getId());
                    arrayBuilder.add(Json.createObjectBuilder()
                            .add("recipeId", v.getId())
                            .add("title", v.getTitle()));
                });
                JsonArray recipeListResults = arrayBuilder.build();
        return ResponseEntity.status(HttpStatus.OK).body(recipeListResults.toString());
    }


}

