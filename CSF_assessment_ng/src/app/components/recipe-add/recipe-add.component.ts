import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Message, Recipe} from "../../model";
import {RecipeService} from "../../services/recipe.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-recipe-add',
  templateUrl: './recipe-add.component.html',
  styleUrls: ['./recipe-add.component.css']
})
export class RecipeAddComponent implements OnInit {

  form!: FormGroup
  formArray!: FormArray;
  recipe!: Recipe
  message!: Message

  constructor(private route: Router,private fb:FormBuilder, private recipeSvc: RecipeService) { }

  ngOnInit(): void {
    this.createForm()
  }

  /*
  recipeId?: string
  title: string
  image: string
  instructions:string
  ingredients: string[]
   */

  createForm(){
    this.formArray = this.fb.array([],[Validators.required, Validators.min(1)])
    this.addFormControl()
    this.form =this.fb.group({
      "title": this.fb.control('', [Validators.required, Validators.minLength(3)]),
      "ingredients": this.formArray,
      "instructions":this.fb.control('', [Validators.required, Validators.minLength(3)]),
      "image": this.fb.control('', Validators.required)
    })
  }

  addFormControl(){
    this.formArray.push(this.fb.control('',[Validators.required,Validators.minLength(3)]))
  }

  removeFormControl(i:number){
    this.formArray.removeAt(i)
  }

  processForm(){
    console.log(this.form.value)
    this.recipe = this.form.value as Recipe
    this.recipeSvc.saveRecipe(this.recipe)
      .then(result=>
      {this.message = result as Message
      console.log(this.message.message)})
    this.form.reset()
    this.route.navigate(['/'])


  }
}
