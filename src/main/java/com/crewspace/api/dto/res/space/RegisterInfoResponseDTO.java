package com.crewspace.api.dto.res.space;

import com.crewspace.api.domain.spaceMember.MemberCategory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterInfoResponseDTO {
    private Boolean hasBirthdate;
    private Boolean hasEmail;
    private Boolean hasContact;
    private Boolean hasSns;
    private Boolean hasEtc;
    private List<MemberCategoryList> memberCategoryList;

    @Getter
    @AllArgsConstructor
    public static class MemberCategoryList{
        private Long categoryId;
        private String categoryName;
    }

    @Builder
    public RegisterInfoResponseDTO(Boolean hasBirthdate, Boolean hasEmail, Boolean hasContact,
        Boolean hasSns, Boolean hasEtc, List<MemberCategoryList> memberCategoryList) {
        this.hasBirthdate = hasBirthdate;
        this.hasEmail = hasEmail;
        this.hasContact = hasContact;
        this.hasSns = hasSns;
        this.hasEtc = hasEtc;
        this.memberCategoryList = memberCategoryList;
    }

    public static RegisterInfoResponseDTO toRegisterInfoResponseDTO(List<MemberCategory> memberCategories){
        List<MemberCategoryList> memberCategoryList = memberCategories.stream()
            .map(category -> new MemberCategoryList(category.getId(), category.getName()))
            .collect(Collectors.toList());

        return RegisterInfoResponseDTO.builder()
            .hasBirthdate(memberCategories.get(0).getSpace().getHasBirthdate())
            .hasContact(memberCategories.get(0).getSpace().getHasContact())
            .hasEmail(memberCategories.get(0).getSpace().getHasEmail())
            .hasSns(memberCategories.get(0).getSpace().getHasSns())
            .hasEtc(memberCategories.get(0).getSpace().getHasEtc())
            .memberCategoryList(memberCategoryList)
            .build();
    }
}
