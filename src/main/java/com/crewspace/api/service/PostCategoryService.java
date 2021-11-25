package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.*;

import com.crewspace.api.domain.post.PostCategory;
import com.crewspace.api.domain.post.PostCategoryRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.category.CategoryListRequestDTO;
import com.crewspace.api.dto.req.category.postCategory.CreatePostCategoryRequestDTO;
import com.crewspace.api.dto.res.postCategory.PostCategoryListResponseDTO;
import com.crewspace.api.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostCategoryService {

    private final PostCategoryRepository postCategoryRepository;
    private final SpaceMemberRepository spaceMemberRepository;

    @Transactional
    public void create(CreatePostCategoryRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        if(!spaceMember.getIsAdmin()) throw new CustomException(SPACE_ADMIN_ONLY);

        List<PostCategory> postCategories = request.toPostCategory(spaceMember.getSpace());
        postCategoryRepository.saveAll(postCategories);

        return;
    }

    public PostCategoryListResponseDTO categoryList(CategoryListRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        List<PostCategory> postCategories = postCategoryRepository.findBySpace(
            spaceMember.getSpace());

        return PostCategoryListResponseDTO.from(postCategories);
    }
}

