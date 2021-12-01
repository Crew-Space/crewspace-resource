package com.crewspace.api.dto.req.spaceMember;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ModifyMemberRequest {
    private MultipartFile image;

    @NotNull(message = "이름은 필수입니다!")
    private String name;
    @NotNull(message = "설명은 필수입니다!")
    private String description;
    @NotNull(message = "카테고리 아이디는 필수입니다!")
    private Long memberCategoryId;

    private String birthdate = "";
    private String email = "";
    private String contact = "";
    private String sns = "";
    private String etc = "";

    public ModifyMemberRequestDTO toModifyMemberRequestDTO(Long spaceId, String memberEmail, String imageURL){
        return ModifyMemberRequestDTO.builder()
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
