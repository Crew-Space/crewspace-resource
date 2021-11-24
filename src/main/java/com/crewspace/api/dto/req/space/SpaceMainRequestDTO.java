package com.crewspace.api.dto.req.space;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpaceMainRequestDTO {
    private String memberEmail;
    private Long spaceId;

    private SpaceMainRequestDTO(String memberEmail, Long spaceId) {
        this.memberEmail = memberEmail;
        this.spaceId = spaceId;
    }

    public static SpaceMainRequestDTO of(String memberEmail, Long spaceId){
        return new SpaceMainRequestDTO(memberEmail, spaceId);
    }
}
