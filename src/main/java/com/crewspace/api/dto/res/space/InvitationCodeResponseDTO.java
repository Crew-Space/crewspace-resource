package com.crewspace.api.dto.res.space;

import com.crewspace.api.domain.space.Space;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InvitationCodeResponseDTO {

    private Long spaceId;
    private String spaceName;
    private String spaceImage;
    private String spaceDescription;

    private InvitationCodeResponseDTO(Long spaceId, String spaceName, String spaceImage,
        String spaceDescription) {
        this.spaceId = spaceId;
        this.spaceName = spaceName;
        this.spaceImage = spaceImage;
        this.spaceDescription = spaceDescription;
    }

    public static InvitationCodeResponseDTO toInvitationCodeResponseDTO(Space space){
        return new InvitationCodeResponseDTO(space.getId(), space.getName(), space.getImage(), space.getDescription());
    }
}
