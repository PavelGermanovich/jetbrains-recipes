package jetbrains.recipes.service;

import jetbrains.recipes.model.Recipe;
import jetbrains.recipes.repository.RecipeRepository;
import jetbrains.recipes.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipeImpl(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Recipe createNewRecipe(Recipe recipe, String email) {
        recipe.setUser(userRepository.findByEmail(email));
        recipe.setDate(LocalDateTime.now());
        return recipeRepository.save(recipe);
    }

    @Override
    public Optional<Recipe> getRecipe(int id) {
        return recipeRepository.findById(id);
    }

    @Override
    public void deleteRecipe(int id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public void updateRecipe(Recipe recipeNew) {
        recipeNew.setDate(LocalDateTime.now());
        recipeRepository.save(recipeNew);
    }

    @Override
    public List<Recipe> searchRecipeByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    @Override
    public List<Recipe> searchRecipeByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }
}
