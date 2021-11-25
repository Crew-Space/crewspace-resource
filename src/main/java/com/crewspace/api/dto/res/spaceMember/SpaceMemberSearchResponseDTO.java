package com.crewspace.api.dto.res.spaceMember;

import com.crewspace.api.domain.spaceMember.SpaceMember;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpaceMemberSearchResponseDTO {

    List<SpaceMemberListResponseDTO.MemberList> members;

    @Getter
    @AllArgsConstructor
    public static class MemberList {
        private Long memberId;
        private String name;
        private String profileImage;
        private String memberCategory;
    }

    private SpaceMemberSearchResponseDTO(
        List<SpaceMemberListResponseDTO.MemberList> members) {
        this.members = members;
    }

    public static SpaceMemberSearchResponseDTO from(List<SpaceMember> spaceMembers){
        List<SpaceMemberListResponseDTO.MemberList> memberLists = spaceMembers.stream()
            .map(spaceMember ->
                new SpaceMemberListResponseDTO.MemberList(spaceMember.getId(), spaceMember.getName(), spaceMember.getImage(),
                    spaceMember.getMemberCategory().getName())
            ).collect(Collectors.toList());
        return new SpaceMemberSearchResponseDTO(memberLists);
    }
}
