package jetbrains.recipes.contoller;


import jetbrains.recipes.dto.CreateRecipeResponse;
import jetbrains.recipes.model.Recipe;
import jetbrains.recipes.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;


@RestController
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/api/recipe/new")
    public CreateRecipeResponse addRecipe(@RequestBody @Valid Recipe recipe, Authentication auth) {
        recipe = recipeService.createNewRecipe(recipe, auth.getName());
        return new CreateRecipeResponse(recipe.getId());
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable("id") int id, @RequestBody @Valid Recipe recipe,
                                          Authentication auth) {
        Optional<Recipe> recipeOpt = recipeService.getRecipe(id);
        if (recipeOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (recipeOpt.get().getUser().getEmail().equals(auth.getName())) {
            recipe.setId(id);
            recipe.setUser(recipeOpt.get().getUser());
            recipeService.updateRecipe(recipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<?> searchRecipe(@RequestParam(required = false, name = "category") String category,
                                          @RequestParam(required = false, name = "name") String name) {
        if ((category == null && name == null) || (!(category == null) && !(name == null))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!(category == null)) {
            return new ResponseEntity<>(recipeService.searchRecipeByCategory(category), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(recipeService.searchRecipeByName(name), HttpStatus.OK);
        }
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable("id") int id) {
        Optional<Recipe> recipeOpt = recipeService.getRecipe(id);
        if (recipeOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Such recipe was not found");
        }
        return recipeOpt.get();
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> removeRecipe(@PathVariable("id") int id, Authentication auth) {
        if (recipeService.getRecipe(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (recipeService.getRecipe(id).get().getUser().getEmail().equals(auth.getName())) {
            recipeService.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
