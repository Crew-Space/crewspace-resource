package com.crewspace.api.controller.v1;

import static com.crewspace.api.constants.SuccessCode.LOAD_MEMBER_CATEGORIES_SUCCES;
import static com.crewspace.api.constants.SuccessCode.LOAD_POST_CATEGORIES_SUCCES;

import com.crewspace.api.dto.req.category.CategoryListRequestDTO;
import com.crewspace.api.dto.res.memberCategory.MemberCategoryListResponse;
import com.crewspace.api.dto.res.memberCategory.MemberCategoryListResponseDTO;
import com.crewspace.api.service.MemberCategoryService;
import com.crewspace.api.utils.SecurityUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members/categories")
public class MemberCategoryController {

    private final MemberCategoryService memberCategoryService;

    @GetMapping("")
    public ResponseEntity<MemberCategoryListResponse> postCategoryList(@Valid @RequestHeader("Space-Id") Long spaceId){

        String memberEmail = SecurityUtil.getCurrentMemberId();

        MemberCategoryListResponseDTO responseDTO = memberCategoryService.categoryList(
            CategoryListRequestDTO.of(spaceId, memberEmail));

        return MemberCategoryListResponse.newResponse(LOAD_MEMBER_CATEGORIES_SUCCES, responseDTO);
    }

}
