package com.binarybrains.bloggin.dto;

import com.binarybrains.bloggin.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(name = "CategoryDto", description = "Objeto que representa una categoría")
public class CategoryDto {
    private Long id;
    @NotNull
    @Size(min=1, max=50)
    @Size(message = "Name must be between 1 and 50 characters", min = 1, max = 50)
    private String name;
    public Category toEntity(){
        return Category.builder().id(this.id).name(this.name).build();
    }
    public static CategoryDto fromEntity(Category category){
        return CategoryDto.builder().id(category.getId()).name(category.getName()).build();
    }
}
