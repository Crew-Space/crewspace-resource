package com.crewspace.api.dto.res.postCategory;

import com.crewspace.api.domain.post.PostCategory;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCategoryListResponseDTO {

    private List<CategoryList> noticeCategories;
    private List<CategoryList> communityCategories;

    @Getter
    @AllArgsConstructor
    public static class CategoryList{
        private Long categoryId;
        private String categoryName;
    }

    private PostCategoryListResponseDTO(
        List<CategoryList> noticeCategory,
        List<CategoryList> communityCategory) {
        this.noticeCategories = noticeCategory;
        this.communityCategories = communityCategory;
    }

    public static PostCategoryListResponseDTO from(List<PostCategory> postCategories){
        List<CategoryList> noticeCategory = new ArrayList<>();
        List<CategoryList> communityCategory = new ArrayList<>();

        postCategories.stream()
            .forEach(category -> {
                if(category.getIsNotice()) noticeCategory.add(new CategoryList(category.getId(), category.getName()));
                else communityCategory.add(new CategoryList(category.getId(), category.getName()));
            });

        return new PostCategoryListResponseDTO(noticeCategory, communityCategory);
    }
}
