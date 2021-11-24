package com.crewspace.api.dto.res.post;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class NoticePostListResponse extends BaseResponse {

    private NoticePostListResponseDTO data;

    private NoticePostListResponse(Boolean success, String msg,
        NoticePostListResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static ResponseEntity<NoticePostListResponse> newResponse(SuccessCode code, NoticePostListResponseDTO data){
        NoticePostListResponse response = new NoticePostListResponse(true,
            code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
