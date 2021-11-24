package com.crewspace.api.dto.req.space;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateSpaceRequest {
    private MultipartFile image;

    @NotNull(message = "동아리 이름은 필수입니다.")
    private String name;
    @NotNull(message = "동아리 설명은 필수입니다.")
    private String description;

    @NotEmpty(message = "회원 분류 한 가지는 꼭 넣어주세요!")
    private List<@NotBlank String> memberCategory;

    private Boolean hasBirthdate = false;
    private Boolean hasEmail = false;
    private Boolean hasContact = false;
    private Boolean hasSns = false;
    private Boolean hasEtc = false;

    public CreateSpaceRequestDTO toCreateSpaceDTO(String imageURL, String bannerImage, String memberEmail){
        return CreateSpaceRequestDTO.builder()
            .memberEmail(memberEmail)
            .image(imageURL)
            .bannerImage(bannerImage)
            .name(name)
            .description(description)
            .memberCategory(memberCategory)
            .hasBirthdate(hasBirthdate)
            .hasEmail(hasEmail)
            .hasContact(hasContact)
            .hasSns(hasSns)
            .hasEtc(hasEtc)
            .build();
    }
}
