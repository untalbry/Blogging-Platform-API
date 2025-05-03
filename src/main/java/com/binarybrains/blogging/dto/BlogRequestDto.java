package com.binarybrains.blogging.dto;

import com.binarybrains.blogging.model.Blog;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "BlogRequestDto", description = "Object used to create a Blog entity through the API")
public class BlogRequestDto {
    @JsonProperty
    @Schema(description = "Blog title")
    @NotBlank(message = "Title must not be blank and max 50 characters")
    @Size(min=5, max = 50, message = "Title must be at most 50 characters and at least 5 characters")
    private String title;
    @JsonProperty
    @Schema(description = "Blog content")
    @NotNull
    @Size(message = "Description must be between 1 and 50 characters", min = 1, max = 50)
    private String content;
    @JsonProperty
    @Schema(description = "Identifier of the category to which the blog belongs")
    private Long idCategory;

    public Blog toEntity(){
        return Blog.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
