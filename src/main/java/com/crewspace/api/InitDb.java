package com.crewspace.api;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.member.MemberRepository;
import com.crewspace.api.domain.memberPost.MemberPost;
import com.crewspace.api.domain.memberPost.ReadPostRepository;
import com.crewspace.api.domain.post.NoticePostRepository;
import com.crewspace.api.dto.req.post.PostRequestDTO;
import com.crewspace.api.dto.req.post.WriteCommunityRequestDTO;
import com.crewspace.api.dto.req.post.WriteNoticeRequestDTO;
import com.crewspace.api.dto.req.space.CreateSpaceRequestDTO;
import com.crewspace.api.dto.req.space.SpaceEnterRequestDTO;
import com.crewspace.api.service.CommunityPostService;
import com.crewspace.api.service.MemberPostService;
import com.crewspace.api.service.NoticePostService;
import com.crewspace.api.service.SpaceService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    private final SpaceService spaceService;



    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        @Value("${default_image.space}")
        private String defaultSpaceImage;

        @Value("${default_image.profile}")
        private String defaultProfileImage;

        @Value("${default_image.space_banner}")
        private String defaultSpaceBanner;

        private final SpaceService spaceService;
        private final MemberRepository memberRepository;
        private final CommunityPostService communityPostService;
        private final NoticePostService noticePostService;
        private final MemberPostService memberPostService;

        public void dbInit() {
            // 유저 생성
            Member member = new Member("11", "aa9919@naver.com", "카카오 이미지-예원", "예원", true);
            memberRepository.save(member);

            member = new Member("22", "test@naver.com", "카카오 이미지-테스트 유저", "테스트 유저", false);
            memberRepository.save(member);

            // 동아리 생성
            List<String> memberCategory = new ArrayList<>();
            memberCategory.add("개발팀");
            CreateSpaceRequestDTO createSpaceRequest = CreateSpaceRequestDTO.builder()
                .memberEmail("aa9919@naver.com")
                .image(defaultSpaceImage)
                .bannerImage(defaultSpaceBanner)
                .name("크루 스페이스2")
                .memberCategory(memberCategory)
                .description("크루 스페이스 설명설명\n크루설명이유")
                .hasBirthdate(false)
                .hasEmail(false)
                .hasContact(false)
                .hasSns(false)
                .hasEtc(false)
                .build();
            spaceService.create(createSpaceRequest);

            // 동아리 가입
            SpaceEnterRequestDTO spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(3))
                .memberEmail("test@naver.com")
                .profileImage(defaultProfileImage)
                .name("테스트 가입자(토큰x)")
                .description("테스트 가입자입니다.")
                .memberCategoryId(Long.valueOf(7))
                .isAdmin(false)
                .birthdate("")
                .email("")
                .contact("")
                .sns("")
                .etc("")
                .build();
            spaceService.enterSpace(spaceEnterRequest);

            // 커뮤니티 게시글 생성
            List<String> communityImages = new ArrayList<>();
            communityImages.add("testImg1");
            communityImages.add("testImg2");
            WriteCommunityRequestDTO writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(3),
                "aa9919@naver.com", Long.valueOf(5), communityImages, "커뮤니티 게시글 작성 by 예원");
            communityPostService.write(writeCommunityRequest);

            // 공지 게시글 생성
            List<String> noticeImages = new ArrayList<>();
            noticeImages.add("notice img1");
            noticeImages.add("notice img2");
            noticeImages.add("notice img3");
            List<Long> noticeTargets = new ArrayList<>();
            noticeTargets.add(Long.valueOf(6));

            WriteNoticeRequestDTO writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(3))
                .memberEmail("aa9919@naver.com")
                .postCategoryId(Long.valueOf(4))
                .targets(noticeTargets)
                .images(noticeImages)
                .title("공지 제목입니다 by 예원")
                .description("공지 설명입니다 by 예원")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

            List<String> noticeImages2 = new ArrayList<>();
            noticeImages2.add("notice img1");
            noticeImages2.add("notice img2");
            noticeImages2.add("notice img3");
            List<Long> noticeTargets2 = new ArrayList<>();
            noticeTargets2.add(Long.valueOf(6));

            WriteNoticeRequestDTO writeNoticeRequest2 = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(3))
                .memberEmail("aa9919@naver.com")
                .postCategoryId(Long.valueOf(4))
                .targets(noticeTargets)
                .images(noticeImages)
                .title("공지 제목입니다2222 by 예원")
                .description("공지 설명입니다2222 by 예원")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest2);

            // 고정 게시글 추가
            PostRequestDTO postRequest = PostRequestDTO.of(Long.valueOf(3), "aa9919@naver.com",
                Long.valueOf(13));
            memberPostService.fix(postRequest);
            PostRequestDTO postRequest2 = PostRequestDTO.of(Long.valueOf(3), "aa9919@naver.com",
                Long.valueOf(18));
            memberPostService.fix(postRequest2);

            // 저장 게시글 추가
            memberPostService.save(postRequest);

            // 커뮤니티 게시글 추가
            List<String> communityImages2 = new ArrayList<>();
            communityImages.add("2nd 게시글ㄹㄹㄹ");
            WriteCommunityRequestDTO writeCommunityRequest2 = WriteCommunityRequestDTO.of(Long.valueOf(3),
                "aa9919@naver.com", Long.valueOf(5), communityImages2, "커뮤니티 게시글222 by 예원");
            communityPostService.write(writeCommunityRequest2);

            // 저장 추가
            PostRequestDTO postRequest3 = PostRequestDTO.of(Long.valueOf(3), "aa9919@naver.com",
                Long.valueOf(10));

            memberPostService.save(postRequest3);

            //커뮤ㅣ티게시글 추가
            List<String> communityImages3 = new ArrayList<>();
            WriteCommunityRequestDTO writeCommunityRequest3 = WriteCommunityRequestDTO.of(Long.valueOf(3),
                "aa9919@naver.com", Long.valueOf(5), communityImages3, "커뮤니티 게시글333 by 예원");
            communityPostService.write(writeCommunityRequest3);

            // new 유저~
            member = new Member("33", "one9872@naver.com", "카카오 이미지-은성", "은성", true);
            memberRepository.save(member);

            member = new Member("44", "aas9919@naver.com", "카카오 이미지-예진", "예진", false);
            memberRepository.save(member);
        }
    }

}