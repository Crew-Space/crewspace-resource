package com.crewspace.api.dto.req.space;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.space.Space;
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
public class SpaceEnterRequestDTO {
    private Long spaceId;
    private String memberEmail;

    private String profileImage;
    private String name;
    private String description;
    private Long memberCategoryId;
    private Boolean isAdmin;

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
