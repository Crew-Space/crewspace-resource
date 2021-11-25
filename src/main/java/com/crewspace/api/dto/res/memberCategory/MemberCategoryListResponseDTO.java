package com.crewspace.api.dto.res.memberCategory;

import com.crewspace.api.domain.spaceMember.MemberCategory;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCategoryListResponseDTO {

    private Long myMemberId;
    private long numOfMember;
    List<CategoryList> memberCategories;

    @Getter
    @AllArgsConstructor
    public static class CategoryList{
        private Long categoryId;
        private String categoryName;
    }

    private MemberCategoryListResponseDTO(SpaceMember spaceMember, long numOfMember,
        List<CategoryList> memberCategories) {
        this.myMemberId = spaceMember.getId();
        this.numOfMember = numOfMember;
        this.memberCategories = memberCategories;
    }

    public static MemberCategoryListResponseDTO of(SpaceMember spaceMember, long numOfMember,
        List<MemberCategory> memberCategories){
        List<CategoryList> categoryLists = memberCategories.stream()
            .map(category ->
                new CategoryList(category.getId(), category.getName())
            ).collect(Collectors.toList());
        return new MemberCategoryListResponseDTO(spaceMember, numOfMember, categoryLists);
    }
}
