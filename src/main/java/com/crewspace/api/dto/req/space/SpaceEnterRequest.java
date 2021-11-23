package com.crewspace.api.dto.req.space;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SpaceEnterRequest {
    private MultipartFile image;

    @NotNull(message = "회원 이름은 필수입니다.")
    private String name;
    @NotNull(message = "회원 설명은 필수입니다.")
    private String description;
    @NotNull(message = "회원 분류 ID는 필수입니다.")
    private Long memberCategoryId;

    private String birthdate = "";
    private String email = "";
    private String contact = "";
    private String sns = "";
    private String etc = "";

    public SpaceEnterRequestDTO toSpaceEnterDTO(Long spaceId, String memberEmail, String imageURL){
        return SpaceEnterRequestDTO.builder()
            .spaceId(spaceId)
            .memberEmail(memberEmail)
            .profileImage(imageURL)
            .name(name)
            .description(description)
            .memberCategoryId(memberCategoryId)
            .birthdate(birthdate)
            .email(email)
            .contact(contact)
            .sns(sns)
            .etc(etc)
            .build();
    }
}
