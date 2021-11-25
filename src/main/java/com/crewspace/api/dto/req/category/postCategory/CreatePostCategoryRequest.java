package com.crewspace.api.dto.req.category.postCategory;

import com.crewspace.api.dto.req.category.postCategory.CreatePostCategoryRequestDTO.CategoryList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CreatePostCategoryRequest {

    private List<@Valid NewCategory> newCategory;

    @Getter
    @Setter
    public static class NewCategory{
        @NotNull(message = "카테고리 이름은 필수입니다.")
        private String categoryName;
        @NotNull(message = "공지 여부 값은 필수입니다.")
        private Boolean isNotice;
    }

    public CreatePostCategoryRequestDTO toCreatePostCategoryDTO(Long spaceId, String memberEmail){
        List<CategoryList> categoryList = newCategory.stream()
            .map(category -> new CategoryList(category.getCategoryName(), category.getIsNotice()))
            .collect(Collectors.toList());

        return CreatePostCategoryRequestDTO.builder()
            .memberEmail(memberEmail)
            .spaceId(spaceId)
            .categoryList(categoryList)
            .build();
    }
}
