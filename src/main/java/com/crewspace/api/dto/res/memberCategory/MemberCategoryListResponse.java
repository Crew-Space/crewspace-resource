package com.crewspace.api.dto.res.memberCategory;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class MemberCategoryListResponse extends BaseResponse {
    MemberCategoryListResponseDTO data;

    private MemberCategoryListResponse(Boolean success, String msg,
        MemberCategoryListResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static MemberCategoryListResponse of(Boolean success, String msg,
        MemberCategoryListResponseDTO data){
        return new MemberCategoryListResponse(success, msg, data);
    }

    public static ResponseEntity<MemberCategoryListResponse> newResponse(SuccessCode code, MemberCategoryListResponseDTO data){
        MemberCategoryListResponse response = MemberCategoryListResponse.of(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
