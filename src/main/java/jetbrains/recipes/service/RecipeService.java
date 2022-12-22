package jetbrains.recipes.service;


import jetbrains.recipes.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    Recipe createNewRecipe(Recipe recipe, String email);

    Optional<Recipe> getRecipe(int id);
    void deleteRecipe(int id);
    void updateRecipe(Recipe recipe);
    List<Recipe> searchRecipeByName(String name);
    List<Recipe> searchRecipeByCategory(String category);
}
