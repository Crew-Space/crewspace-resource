package com.crewspace.api.dto.req.space;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.post.PostCategory;
import com.crewspace.api.domain.space.Space;
import com.crewspace.api.domain.spaceMember.MemberCategory;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSpaceRequestDTO {

    private String memberEmail;

    private String image;
    private String bannerImage;
    private String name;
    private String description;
    private List<String> memberCategory;

    private Boolean hasBirthdate;
    private Boolean hasEmail;
    private Boolean hasContact;
    private Boolean hasSns;
    private Boolean hasEtc;

    public Space toSpace(){
        return Space.builder()
            .image(image)
            .bannerImage(bannerImage)
            .name(name)
            .description(description)
            .hasBirthdate(hasBirthdate)
            .hasEmail(hasEmail)
            .hasContact(hasContact)
            .hasSns(hasSns)
            .hasEtc(hasEtc)
            .build();
    }

    public MemberCategory toMemberCategory(Space space, String categoryName, Boolean isAdmin){
        return MemberCategory.of(space, categoryName, isAdmin);
    }

    public PostCategory toPostCategory(Space space, String categoryName, Boolean isNotice){
        return PostCategory.of(space, categoryName, isNotice);
    }

    public SpaceMember toSpaceMember(Space space, Member member, MemberCategory adminCategory){
        return SpaceMember.builder()
            .space(space)
            .member(member)
            .memberCategory(adminCategory)
            .isAdmin(true)
            .image(member.getImage())
            .name(member.getNickname())
            .description(space.getName() + "의 운영진입니다 :)")
            .build();
    }

}
