package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.ALREADY_FIXED_POST;
import static com.crewspace.api.constants.ExceptionCode.ALREADY_SAVED_POST;
import static com.crewspace.api.constants.ExceptionCode.FIXED_POST_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.POST_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.SAVED_POST_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.SPACE_MEMBER_NOT_FOUND;

import com.crewspace.api.domain.memberPost.FixedPost;
import com.crewspace.api.domain.memberPost.FixedPostRepository;
import com.crewspace.api.domain.memberPost.SavedPost;
import com.crewspace.api.domain.memberPost.SavedPostRepository;
import com.crewspace.api.domain.post.NoticePostRepository;
import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.post.PostRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.memberPost.MemberPostRequestDTO;
import com.crewspace.api.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberPostService {

    private final SpaceMemberRepository spaceMemberRepository;
    private final PostRepository postRepository;
    private final NoticePostRepository noticePostRepository;

    private final SavedPostRepository savedPostRepository;
    private final FixedPostRepository fixedPostRepository;
    @Transactional
    public void save(MemberPostRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        Post post = postRepository.findById(request.getPostId())
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        savedPostRepository.findByPostAndMember(post, spaceMember)
            .ifPresent(savedPost ->{ throw new CustomException(ALREADY_SAVED_POST); });

        SavedPost savedPost = request.toSavedPost(post, spaceMember);
        savedPostRepository.save(savedPost);
        return;
    }

    @Transactional
    public void unSave(MemberPostRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        Post post = postRepository.findById(request.getPostId())
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if(savedPostRepository.deleteByPostAndMember(post, spaceMember).size() == 0){
            throw new CustomException(SAVED_POST_NOT_FOUND);
        }
        return;
    }

    @Transactional
    public void fix(MemberPostRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        Post post = noticePostRepository.findById(request.getPostId())
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        fixedPostRepository.findByPostAndMember(post, spaceMember)
            .ifPresent(savedPost ->{ throw new CustomException(ALREADY_FIXED_POST); });

        FixedPost fixedPost = request.toFixedPost(post, spaceMember);
        fixedPostRepository.save(fixedPost);

        return;
    }

    @Transactional
    public void unFix(MemberPostRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        Post post = noticePostRepository.findById(request.getPostId())
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if(fixedPostRepository.deleteByPostAndMember(post, spaceMember).size() == 0){
            throw new CustomException(FIXED_POST_NOT_FOUND);
        }
        return;
    }
}
