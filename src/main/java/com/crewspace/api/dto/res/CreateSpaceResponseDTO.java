package com.crewspace.api.dto.res;

import com.crewspace.api.domain.space.Space;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateSpaceResponseDTO {
    private Long spaceId;
    private String invitationCode;
    private String spaceName;
    private String spaceImage;

    private CreateSpaceResponseDTO(Long spaceId, String invitationCode, String spaceName,
        String spaceImage) {
        this.spaceId = spaceId;
        this.invitationCode = invitationCode;
        this.spaceName = spaceName;
        this.spaceImage = spaceImage;
    }

    public static CreateSpaceResponseDTO toCreateSpaceResponseDTO(Space space){
        return new CreateSpaceResponseDTO(space.getId(), space.getInvitationCode(), space.getName(), space.getImage());
    }
}
