import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RecipeService} from "../../services/recipe.service";
import {Recipe} from "../../model";

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css']
})
export class RecipeDetailComponent implements OnInit {

  constructor(private route: Router,private activateRoute:ActivatedRoute, private recipeSvc: RecipeService ) { }
  recipeId!: string
  recipeObj:Partial<Recipe> = {}

  ngOnInit(): void {
    this.recipeId= this.activateRoute.snapshot.params['recipeId']
    this.recipeSvc.getRecipe(this.recipeId)
      .then(result=> this.recipeObj = result)
      .catch(error=> {
        console.log(error)
        alert(error.error)
        this.route.navigate(['/'])
      })
  }

}
