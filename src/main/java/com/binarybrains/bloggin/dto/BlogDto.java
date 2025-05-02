package com.binarybrains.bloggin.dto;

import com.binarybrains.bloggin.model.Blog;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "BlogDto", description = "Objeto que representa Blog")
public class BlogDto {
    @JsonProperty
    private Long id;
    @JsonProperty
    @Schema(description = "Titulo del blog")
    @NotBlank(message = "Title must not be blank and max 50 characters")
    @Size(min=5, max = 50, message = "Title must be at most 50 characters and at least 5 characters")
    private String title;
    @JsonProperty
    @Schema(description = "Contenido del blog")
    @NotNull
    @Size(message = "Description must be between 1 and 50 characters", min = 1, max = 50)
    private String content;
    @JsonProperty
    private Long idCategory;
    @JsonProperty
    private LocalDateTime createdAt;
    @JsonProperty
    private LocalDateTime lastUpdate;
    public Blog toEntity(){
        return Blog.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .build();

    }
    public static BlogDto fromEntity(Blog blog){

        return BlogDto.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .idCategory(blog.getCategory().getId())
                .createdAt(blog.getCreatedAt())
                .lastUpdate(blog.getLastUpdate())
                .build();
    }
}
