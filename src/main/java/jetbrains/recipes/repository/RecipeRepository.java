package jetbrains.recipes.repository;

import jetbrains.recipes.model.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    @Query
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

    @Query
    List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
