package com.crewspace.api.dto.req.spaceMember;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.spaceMember.MemberCategory;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyMemberRequestDTO {

    private String memberEmail;
    private Long spaceId;

    private String profileImage;

    private String name;
    private String description;
    private Long memberCategoryId;

    private String birthdate;
    private String email;
    private String contact;
    private String sns;
    private String etc;


    public SpaceMember toSpaceMember(Member member, MemberCategory memberCategory){
        return SpaceMember.builder()
            .space(memberCategory.getSpace())
            .member(member)
            .memberCategory(memberCategory)
            .isAdmin(memberCategory.getIsAdmin())
            .image(profileImage)
            .name(name)
            .description(description)
            .birthdate(birthdate)
            .email(email)
            .contact(contact)
            .sns(sns)
            .etc(etc)
            .build();
    }


}
