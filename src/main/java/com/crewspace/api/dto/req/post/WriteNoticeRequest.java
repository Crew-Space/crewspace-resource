package com.crewspace.api.dto.req.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class WriteNoticeRequest {

    private List<MultipartFile> image = new ArrayList<>();;

    @NotNull(message = "제목을 입력해주세요")
    private String title;
    private String description;

    @NotEmpty(message = "공지의 대상을 선택해주세요!")
    private List<@NotNull Long> targets;

    @NotNull(message = "예약 여부를 설정해주세요!")
    private Boolean isReserved;

    @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public LocalDateTime reservedTime = LocalDateTime.now();

    public WriteNoticeRequestDTO toWriteNoticeDTO(Long spaceId, String memberEmail,
        Long postCategoryId, List<String> images){
        return WriteNoticeRequestDTO.builder()
            .spaceId(spaceId)
            .memberEmail(memberEmail)
            .postCategoryId(postCategoryId)
            .targets(targets)
            .images(images)
            .title(title)
            .description(description)
            .isReserved(isReserved)
            .reservedTime(reservedTime)
            .build();
    }
}
