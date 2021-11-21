package com.crewspace.api.dto.res.space;

import com.crewspace.api.domain.space.Space;
import java.util.List;
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
    private List<MemberCategory> memberCategory;

    @Getter
    @AllArgsConstructor
    public static class MemberCategory{
        private Long categoryId;
        private String categoryName;
    }

    @Builder
    public RegisterInfoResponseDTO(Boolean hasBirthdate, Boolean hasEmail, Boolean hasContact,
        Boolean hasSns, Boolean hasEtc, List<MemberCategory> memberCategory) {
        this.hasBirthdate = hasBirthdate;
        this.hasEmail = hasEmail;
        this.hasContact = hasContact;
        this.hasSns = hasSns;
        this.hasEtc = hasEtc;
        this.memberCategory = memberCategory;
    }

    public static RegisterInfoResponseDTO toRegisterInfoResponseDTO(Space space, List<MemberCategory> memberCategory){
        return RegisterInfoResponseDTO.builder()
            .hasBirthdate(space.getHasBirthdate())
            .hasContact(space.getHasContact())
            .hasEmail(space.getHasEmail())
            .hasSns(space.getHasSns())
            .hasEtc(space.getHasEtc())
            .memberCategory(memberCategory)
            .build();
    }
}
