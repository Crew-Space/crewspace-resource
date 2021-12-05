package com.crewspace.api.dto.req.space;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class
ModifyBannerRequest {
    @NotNull(message = "이미지는 필수입니다!")
    private MultipartFile image;
}
