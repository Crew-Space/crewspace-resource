package com.crewspace.api.controller.v1;

import static com.crewspace.api.constants.SuccessCode.*;

import com.crewspace.api.dto.req.postCategory.CreatePostCategoryRequest;
import com.crewspace.api.dto.req.postCategory.PostCategoryListRequestDTO;
import com.crewspace.api.dto.res.postCategory.CreatePostCategoryResponse;
import com.crewspace.api.dto.res.postCategory.PostCategoryListResponse;
import com.crewspace.api.dto.res.postCategory.PostCategoryListResponseDTO;
import com.crewspace.api.service.PostCategoryService;
import com.crewspace.api.utils.SecurityUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts/categories")
public class PostCategoryController {

    private final PostCategoryService postCategoryService;

    @PostMapping("")
    public ResponseEntity<CreatePostCategoryResponse> create(
        @Valid @RequestHeader("Space-Id") Long spaceId, @Valid @RequestBody CreatePostCategoryRequest request){

        String memberEmail = SecurityUtil.getCurrentMemberId();

        postCategoryService.create(request.toCreatePostCategoryDTO(spaceId, memberEmail));

        return CreatePostCategoryResponse.newResponse(CREATE_CATEGORY_SUCCESS);
    }

    @GetMapping("")
    public ResponseEntity<PostCategoryListResponse> postCategoryList(@Valid @RequestHeader("Space-Id") Long spaceId){

        String memberEmail = SecurityUtil.getCurrentMemberId();

        PostCategoryListResponseDTO responseDTO = postCategoryService.categoryList(
            PostCategoryListRequestDTO.of(spaceId, memberEmail));

        return PostCategoryListResponse.newResponse(LOAD_POST_CATEGORIES_SUCCES, responseDTO);
    }

}
