package jetbrains.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    @NotBlank
    private String description;

    @NotNull
    @Size(min = 1)
    @ToString.Exclude
    @ElementCollection
    @CollectionTable(name = "ingredients", joinColumns = @JoinColumn(name = "id"))
    private List<String> ingredients;

    @NotNull
    @Size(min = 1)
    @ToString.Exclude
    @ElementCollection
    @CollectionTable(name = "directions", joinColumns = @JoinColumn(name = "id"))
    private List<String> directions;

    @JsonIgnore
    //Cant use cascade.all because user in that case will be deleted with recipe
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Recipe recipe = (Recipe) o;
        return id != null && Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}