import { Component, OnInit } from '@angular/core';
import {RecipeService} from "../../services/recipe.service";
import {Recipe} from "../../model";

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {

  constructor(private recipeSvc: RecipeService) { }

  recipeList!: Partial<Recipe>[]

  ngOnInit(): void {
    this.recipeSvc.getAllRecipes()
      .then(result => this.recipeList = result)
      .catch(error=> console.warn(error))
  }

}
