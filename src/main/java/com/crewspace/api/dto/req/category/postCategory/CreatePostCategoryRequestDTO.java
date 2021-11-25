package com.crewspace.api.dto.req.category.postCategory;


import com.crewspace.api.domain.post.PostCategory;
import com.crewspace.api.domain.space.Space;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostCategoryRequestDTO {
    private Long spaceId;
    private String memberEmail;
    private List<CategoryList> categoryList;

    @Getter
    @AllArgsConstructor
    public static class CategoryList{
        private String categoryName;
        private Boolean isNotice;
    }

    public List<PostCategory> toPostCategory(Space space){
        return categoryList.stream()
            .map(category -> PostCategory.of(space, category.categoryName, category.getIsNotice()))
            .collect(Collectors.toList());
    }
}
