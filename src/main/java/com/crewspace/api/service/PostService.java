package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.BAD_POST_TYPE;
import static com.crewspace.api.constants.ExceptionCode.POST_CATEGORY_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.POST_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.SPACE_MEMBER_NOT_FOUND;

import com.crewspace.api.domain.memberPost.FixedPostRepository;
import com.crewspace.api.domain.memberPost.ReadPostRepository;
import com.crewspace.api.domain.memberPost.SavedPostRepository;
import com.crewspace.api.domain.post.CommunityPost;
import com.crewspace.api.domain.post.NoticePost;
import com.crewspace.api.domain.post.NoticeTarget;
import com.crewspace.api.domain.post.NoticeTargetRepository;
import com.crewspace.api.domain.post.PostCategory;
import com.crewspace.api.domain.post.PostCategoryRepository;
import com.crewspace.api.domain.post.PostSupportRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.post.PostListRequestDTO;
import com.crewspace.api.dto.req.post.PostRequestDTO;
import com.crewspace.api.dto.res.post.CommunityPostDetailResponseDTO;
import com.crewspace.api.dto.res.post.CommunityPostListResponseDTO;
import com.crewspace.api.dto.res.post.CommunityPostListResponseDTO.CommunityPostList;
import com.crewspace.api.dto.res.post.NoticePostDetailResponseDTO;
import com.crewspace.api.dto.res.post.NoticePostListResponseDTO;
import com.crewspace.api.dto.res.post.NoticePostListResponseDTO.NoticePostList;
import com.crewspace.api.exception.CustomException;
import com.querydsl.core.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostSupportRepository postSupportRepository;
    private final SpaceMemberRepository spaceMemberRepository;

    private final SavedPostRepository savedPostRepository;
    private final FixedPostRepository fixedPostRepository;
    private final ReadPostRepository readPostRepository;

    private final NoticeTargetRepository noticeTargetRepository;
    private final PostCategoryRepository postCategoryRepository;

    public CommunityPostDetailResponseDTO communityDetail(PostRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        // 작성자 정보 포함
        CommunityPost communityPost = postSupportRepository.findCommunityPost(request.getPostId())
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        Boolean isAuthor = communityPost.getAuthor().getMember().getEmail().equals(request.getMemberEmail());
        Boolean isSaved = savedPostRepository.existsByPostAndMember(communityPost, spaceMember);

        return CommunityPostDetailResponseDTO.builder()
            .post(communityPost)
            .isAuthor(isAuthor)
            .isSaved(isSaved)
            .build();
    }

    @Transactional
    public NoticePostDetailResponseDTO noticeDetail(PostRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        NoticePost noticePost = postSupportRepository.findNoticePost(request.getPostId())
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        List<NoticeTarget> noticeTargets = noticeTargetRepository.findByNoticePost(noticePost);
        Boolean isAuthor = noticePost.getAuthor().getMember().getEmail().equals(request.getMemberEmail());
        Boolean isSaved = savedPostRepository.existsByPostAndMember(noticePost, spaceMember);
        Boolean isFixed = fixedPostRepository.existsByPostAndMember(noticePost, spaceMember);

        // 읽음 처리
        if(!readPostRepository.existsByPostAndMember(noticePost, spaceMember)) readPostRepository.save(request.toReadPost(noticePost,spaceMember));

        return NoticePostDetailResponseDTO.builder()
            .post(noticePost)
            .targets(noticeTargets)
            .isAuthor(isAuthor)
            .isFixed(isFixed)
            .isSaved(isSaved)
            .build();
    }

    public CommunityPostListResponseDTO communityList(PostListRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        if(!request.getPostCategoryId().equals(Long.valueOf(-1))) {
            PostCategory postCategory = postCategoryRepository.findById(request.getPostCategoryId())
                .orElseThrow(() -> new CustomException(POST_CATEGORY_NOT_FOUND));
            if(postCategory.getIsNotice()){
                throw new CustomException(POST_CATEGORY_NOT_FOUND);
            }
        }

        PageRequest paging = PageRequest.of(request.getOffset(), 10);

        List<CommunityPostList> posts;
        if(request.getType().equals("ALL")) {
            posts = postSupportRepository.allCommunityList(spaceMember, paging, request.getPostCategoryId());
        }else if(request.getType().equals("SAVED")){
            posts = postSupportRepository.saveCommunityList(spaceMember, paging, request.getPostCategoryId());
        }else{
            throw new CustomException(BAD_POST_TYPE);
        }

        return CommunityPostListResponseDTO.of(request.getOffset(), request.getType(), posts);
    }

    public NoticePostListResponseDTO noticeList(PostListRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        if(!request.getPostCategoryId().equals(Long.valueOf(-1))) {
            PostCategory postCategory = postCategoryRepository.findById(request.getPostCategoryId())
                .orElseThrow(() -> new CustomException(POST_CATEGORY_NOT_FOUND));
            if(!postCategory.getIsNotice()){
                throw new CustomException(POST_CATEGORY_NOT_FOUND);
            }
        }

        PageRequest paging = PageRequest.of(request.getOffset(), 10);

        List<NoticePostList> posts;
        if(request.getType().equals("ALL")) {
            posts = postSupportRepository.allNoticeList(spaceMember, paging, request.getPostCategoryId());
        }else if(request.getType().equals("SAVED")){
            posts = postSupportRepository.saveNoticeList(spaceMember, paging, request.getPostCategoryId());
        }else if(request.getType().equals("NREAD")){
            posts = postSupportRepository.notReadNoticeList(spaceMember, paging, request.getPostCategoryId());
        } else{
            throw new CustomException(BAD_POST_TYPE);
        }

        return NoticePostListResponseDTO.of(request.getOffset(), request.getType(), posts);
    }
}
