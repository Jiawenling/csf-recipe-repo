import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Message, Recipe} from "../model";
import {lastValueFrom} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  constructor(private http: HttpClient) { }

  getAllRecipes(): Promise<Partial<Recipe>[]>{
    return lastValueFrom(this.http.get<Partial<Recipe>[]>('/api/recipes'))
  }

  getRecipe(recipeId: string): Promise<Partial<Recipe>>{
    return lastValueFrom(this.http.get<Partial<Recipe>>(`/api/recipe/${recipeId}`))
  }

  saveRecipe(recipe:Recipe): Promise<Message>{
    return lastValueFrom(this.http.post<Message>('/api/recipe', recipe))
  }
}
