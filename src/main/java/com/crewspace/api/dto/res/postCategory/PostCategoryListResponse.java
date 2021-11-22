package com.crewspace.api.dto.res.postCategory;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class PostCategoryListResponse extends BaseResponse {

    private PostCategoryListResponseDTO data;

    private PostCategoryListResponse(Boolean success, String msg,
        PostCategoryListResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static PostCategoryListResponse of(Boolean success, String msg,
        PostCategoryListResponseDTO data){
        return new PostCategoryListResponse(success, msg, data);
    }

    public static ResponseEntity<PostCategoryListResponse> newResponse(SuccessCode code, PostCategoryListResponseDTO data){
        PostCategoryListResponse response = PostCategoryListResponse.of(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());

    }

}
