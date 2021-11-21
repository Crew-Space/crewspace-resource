package com.crewspace.api.dto.res.space;

import com.crewspace.api.domain.spaceMember.SpaceMember;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnteredSpaceResponseDTO {

    private List<SpaceList> spaces;

    @Getter
    @AllArgsConstructor
    public static class SpaceList {
        private Long spaceId;
        private String spaceName;
        private String spaceImage;
    }

    private EnteredSpaceResponseDTO(List<SpaceList> spaces) {
        this.spaces = spaces;
    }

    public static EnteredSpaceResponseDTO toEnteredSpaceResponseDTO(List<SpaceMember> spaces){
        List<SpaceList> spaceLists = spaces.stream()
            .map(s -> new SpaceList(s.getSpace().getId(), s.getSpace().getName(), s.getSpace().getImage()))
            .collect(Collectors.toList());

        return new EnteredSpaceResponseDTO(spaceLists);
    }
}
