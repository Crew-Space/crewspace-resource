package com.crewspace.api.dto.res.postCategory;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@JsonInclude(Include.NON_NULL)
public class CreatePostCategoryResponse extends BaseResponse {

    private CreatePostCategoryResponse(Boolean success, String msg) {
        super(success, msg);
    }


    public static CreatePostCategoryResponse of(Boolean success, String msg){
        return new CreatePostCategoryResponse(success, msg);
    }

    public static ResponseEntity<CreatePostCategoryResponse> newResponse(SuccessCode code){
        return new  ResponseEntity(CreatePostCategoryResponse.of(true, code.getMsg()), code.getStatus());
    }
}
