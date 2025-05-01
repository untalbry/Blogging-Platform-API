package com.binarybrains.bloggin.dto;

import com.binarybrains.bloggin.model.Blog;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Schema(name = "BlogDto", description = "Objeto que representa Blog")
public class BlogDto {
    private Long id;
    @NotNull
    @Size(message = "Title must be between 1 and 50 characters", min = 1, max = 50)
    private String title;
    @NotNull
    @Size(message = "description must be between 1 and 50 characters", min = 1, max = 50)
    private String content;
    private Long idCategory;
    private LocalDateTime createdAt;
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
