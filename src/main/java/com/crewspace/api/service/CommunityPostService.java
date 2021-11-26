package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.POST_CATEGORY_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.SPACE_ADMIN_ONLY;
import static com.crewspace.api.constants.ExceptionCode.SPACE_MEMBER_NOT_FOUND;

import com.crewspace.api.domain.post.CommunityPost;
import com.crewspace.api.domain.post.CommunityPostRepository;
import com.crewspace.api.domain.post.PostCategory;
import com.crewspace.api.domain.post.PostCategoryRepository;
import com.crewspace.api.domain.post.PostImage;
import com.crewspace.api.domain.post.PostImageRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.post.WriteCommunityRequestDTO;
import com.crewspace.api.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityPostRepository communityPostRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final SpaceMemberRepository spaceMemberRepository;
    private final PostImageRepository postImageRepository;

    @Transactional
    public void write(WriteCommunityRequestDTO request){

        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        PostCategory postCategory = postCategoryRepository.findById(request.getPostCategoryId())
            .orElseThrow(() -> new CustomException(POST_CATEGORY_NOT_FOUND));


        // 커뮤니티 게시글 저장
        CommunityPost communityPost = request.toCommunityPost(spaceMember, postCategory);
        communityPostRepository.save(communityPost);

        // 게시글 이미지 저장
        List<PostImage> postImages = request.toPostImages(communityPost);
        postImageRepository.saveAll(postImages);
        return;
    }
}
