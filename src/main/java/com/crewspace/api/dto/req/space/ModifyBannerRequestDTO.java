package com.crewspace.api.dto.req.space;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyBannerRequestDTO {
    private Long spaceId;
    private String memberEmail;
    private String bannerImage;
}
