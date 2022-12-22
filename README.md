# jetbrains-recipes
Jetbrains recipe project

Simple Recipe project with the following stack: Gradle, Spring Boot, Spring (Data, Security, Web), lombok.
H2 database used.

Using security user can register into application, user data will be saved in DB and encoded.

Simple validation constraints are applied to entities. No UI available, only API.

# REST API

## Register user

### Request

`POST /api/register`

      {
          "email": "test123ewf@mail.ru",
          "password": "qwerty123"
      }

### Response
User is validated. If user registered HTTP status 200 OK returned.
If user does not match contraints or already exists HTTP 400 (BAD REQUEST) returned.

## Recipes API (available only for logged in users)

## Create new Recipe
### Request

`POST /api/recipe`

      {
          "name": "first recipe",
          "category": "category",
          "description": "recipe description",
          "ingredients": ["first ingredient", "second ingredient", "3rd ingredient"],
          "directions": ["add solt", "add milk"]
      }

### Response
Recipe is validated. If user is not logged in, 403 (Forbidden) returned.
If recipe is not valid 400 (Bad request).
After recipe creation, its id returned

{
    "id": 1
}

## Get existing request
### Request

`GET /api/recipe/{id}`

### Response

If recipe not found, HTTP 404 returned.
      {
          "name": "first recipe",
          "category": "category",
          "date": "2022-12-22T16:55:10.075253",
          "description": "recipe description",
          "ingredients": [
              "first ingredient",
              "second ingredient",
              "3rd ingredient"
          ],
          "directions": [
              "add solt",
              "add milk"
          ]
      }
      
      
