package com.crewspace.api.dto.res.comment;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class CommentListResponse extends BaseResponse {
    CommentListResponseDTO data;

    private CommentListResponse(Boolean success, String msg, CommentListResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static CommentListResponse of(Boolean success, String msg, CommentListResponseDTO data){
        return new CommentListResponse(success, msg, data);
    }

    public static ResponseEntity<CommentListResponse> newResponse(SuccessCode code, CommentListResponseDTO data){
        CommentListResponse response = CommentListResponse.of(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
