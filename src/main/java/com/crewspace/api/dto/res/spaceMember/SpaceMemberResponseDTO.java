package com.crewspace.api.dto.res.spaceMember;

import com.crewspace.api.domain.spaceMember.SpaceMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpaceMemberResponseDTO {

    private String profileImage;
    private String name;
    private String description;
    private String memberCategory;
    private Long memberCategoryId;

    private String birthdate;
    private String email;
    private String contact;
    private String sns;
    private String etc;

    private SpaceMemberResponseDTO(SpaceMember spaceMember) {
        this.profileImage = spaceMember.getImage();
        this.name = spaceMember.getName();
        this.description = spaceMember.getDescription();
        this.memberCategory = spaceMember.getMemberCategory().getName();
        this.memberCategoryId = spaceMember.getMemberCategory().getId();
        this.birthdate = spaceMember.getBirthdate();
        this.email = spaceMember.getEmail();
        this.contact = spaceMember.getContact();
        this.sns = spaceMember.getSns();
        this.etc = spaceMember.getEtc();
    }

    public static SpaceMemberResponseDTO from(SpaceMember spaceMember){
        return new SpaceMemberResponseDTO(spaceMember);
    }
}
