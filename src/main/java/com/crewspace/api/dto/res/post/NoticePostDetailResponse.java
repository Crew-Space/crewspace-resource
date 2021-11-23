package com.crewspace.api.dto.res.post;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class NoticePostDetailResponse extends BaseResponse {
    NoticePostDetailResponseDTO data;

    private NoticePostDetailResponse(Boolean success, String msg,
        NoticePostDetailResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static NoticePostDetailResponse of(Boolean success, String msg,
        NoticePostDetailResponseDTO data){
        return new NoticePostDetailResponse(success, msg, data);
    }

    public static ResponseEntity<NoticePostDetailResponse> newResponse(SuccessCode code, NoticePostDetailResponseDTO data){
        NoticePostDetailResponse response = NoticePostDetailResponse.of(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
