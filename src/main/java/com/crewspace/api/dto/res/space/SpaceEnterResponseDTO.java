package com.crewspace.api.dto.res.space;

import com.crewspace.api.domain.spaceMember.SpaceMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpaceEnterResponseDTO {
    private String profileImage;
    private String name;
    private String categoryName;

    private SpaceEnterResponseDTO(String profileImage, String name, String categoryName) {
        this.profileImage = profileImage;
        this.name = name;
        this.categoryName = categoryName;
    }

    public static SpaceEnterResponseDTO toSpaceEnterResponseDTO(SpaceMember spaceMember){
        return new SpaceEnterResponseDTO(spaceMember.getImage(), spaceMember.getName(), spaceMember.getMemberCategory().getName());
    }
}
