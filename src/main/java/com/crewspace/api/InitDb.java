package com.crewspace.api;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.member.MemberRepository;
import com.crewspace.api.dto.req.category.postCategory.CreatePostCategoryRequestDTO;
import com.crewspace.api.dto.req.category.postCategory.CreatePostCategoryRequestDTO.CategoryList;
import com.crewspace.api.dto.req.post.WriteCommunityRequestDTO;
import com.crewspace.api.dto.req.post.WriteNoticeRequestDTO;
import com.crewspace.api.dto.req.space.CreateSpaceRequestDTO;
import com.crewspace.api.dto.req.space.SpaceEnterRequestDTO;
import com.crewspace.api.service.CommunityPostService;
import com.crewspace.api.service.NoticePostService;
import com.crewspace.api.service.PostCategoryService;
import com.crewspace.api.service.SpaceService;
import java.time.LocalDateTime;
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
        private final PostCategoryService postCategoryService;

        // 수한
        public void member(){
            // 유저 생성
            Member member1 = new Member("1", "suhan124@spacer.com", "카카오 이미지-예원", "예원", true);
            memberRepository.save(member1);

            Member member2 = new Member("2", "suhan125@spacer.com", "카카오 이미지-테스트 유저", "테스트 유저", false);
            memberRepository.save(member2);

            Member member3 = new Member("3", "suhan126@spacer.com", "카카오 이미지-테스트 유저", "테스트 유저", false);
            memberRepository.save(member3);

            Member member4 = new Member("4", "suhan127@spacer.com", "카카오 이미지-테스트 유저", "테스트 유저", false);
            memberRepository.save(member4);

            Member member5 = new Member("5", "suhan128@spacer.com", "카카오 이미지-테스트 유저", "테스트 유저", false);
            memberRepository.save(member5);

            Member member6 = new Member("6", "suhan129@spacer.com", "카카오 이미지-테스트 유저", "테스트 유저", false);
            memberRepository.save(member6);

            Member member7 = new Member("7", "suhan1210@spacer.com", "카카오 이미지-테스트 유저", "테스트 유저", false);
            memberRepository.save(member7);

            Member member8 = new Member("8", "suhan1211@spacer.com", "카카오 이미지-테스트 유저", "테스트 유저", false);
            memberRepository.save(member8);

            Member member9 = new Member("9", "suhan1212@spacer.com", "카카오 이미지-테스트 유저", "테스트 유저", false);
            memberRepository.save(member9);

            Member member10 = new Member("10", "suhan1213@spacer.com", "카카오 이미지-테스트 유저", "테스트 유저", false);
            memberRepository.save(member10);

            // 운영진
            Member member11 = new Member("11", "suhan123@spacer.com", "https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/profile_suhan.png", "김수한", false);
            memberRepository.save(member11);

            // 시연자
            Member member12 = new Member("12", "one9872@naver.com", "카카오 이미지-테스트 유저", "테스트 유저", false);
            memberRepository.save(member12);
        }

        // 동아리 생성(13) - 동아리 배너, 이미지 삽입

        public void createSpace(){
            List<String> memberCategory = new ArrayList<>();
            memberCategory.add("기획팀");
            memberCategory.add("디자인팀");
            memberCategory.add("개발팀");
            CreateSpaceRequestDTO createSpaceRequest = CreateSpaceRequestDTO.builder()
                .memberEmail("suhan123@spacer.com")
                .image("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/heyj_profile_image.png")
                .bannerImage("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/heyj_banner.png")
                .name("HEYJ")
                .memberCategory(memberCategory)
                .description("안녕하세요! 대학생 연합 IT 동아리 HEYJ 헤이제이 입니다! \n 기획, 디자인, 개발 파트 여러분 모두 한 기수 동안 많은 추억과 인연 가져가시길 바랍니다 :)")
                .hasBirthdate(true)
                .hasEmail(true)
                .hasSns(true)
                .hasContact(false)
                .hasEtc(false)
                .build();
            spaceService.create(createSpaceRequest);
        }

        // 동아리 가입 // 운영(16,4) 기획(17,5) 디쟌(18,6) 개발(19,7)
        public void spaceEnter(){
            SpaceEnterRequestDTO spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan124@spacer.com")
                .profileImage("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/profile_et_1.png")
                .name("이정일")
                .description("헤이제이 회장 김수한 입니다! 반가워요~")
                .memberCategoryId(Long.valueOf(4))
                .isAdmin(true)
                .birthdate("796/03/03")
                .email("suhan123@spacer.com")
                .sns("jung2")
                .build();
            spaceService.enterSpace(spaceEnterRequest);

            spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan125@spacer.com")
                .profileImage("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/profile_et_2.png")
                .name("김정이")
                .description("잘부탁드립니다")
                .memberCategoryId(Long.valueOf(6))
                .isAdmin(false)
                .birthdate("796/03/03")
                .email("suhan124@spacer.com")
                .sns("jung2")
                .build();
            spaceService.enterSpace(spaceEnterRequest);

            spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan126@spacer.com")
                .profileImage("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/profile_et_3.png")
                .name("삼동식")
                .description("다들 반갑습니다!")
                .memberCategoryId(Long.valueOf(6))
                .isAdmin(false)
                .birthdate("1996/03/03")
                .email("suhan124@spacer.com")
                .sns("jung2")
                .build();
            spaceService.enterSpace(spaceEnterRequest);

            spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan127@spacer.com")
                .profileImage("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/profile_et_4.png")
                .name("사동식")
                .description("저는 고기를 좋아해요")
                .memberCategoryId(Long.valueOf(7))
                .isAdmin(false)
                .birthdate("1996/06/06")
                .email("suhan125@spacer.com")
                .sns("jung2")
                .build();
            spaceService.enterSpace(spaceEnterRequest);

            spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan128@spacer.com")
                .profileImage("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/profile_et_5.png")
                .name("이민영")
                .description("개발은 잘 못하지만 열심히 하겠습니다")
                .memberCategoryId(Long.valueOf(5))
                .isAdmin(false)
                .birthdate("1999/06/06")
                .email("suhan12dafa5@spacer.com")
                .sns("jung2")
                .build();
            spaceService.enterSpace(spaceEnterRequest);


            spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan129@spacer.com")
                .profileImage("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/profile_et_6.png")
                .name("안은희")
                .description("스터디 10개 하는중")
                .memberCategoryId(Long.valueOf(6))
                .isAdmin(false)
                .birthdate("2001/06/06")
                .email("suhan12dafa5@spacer.com")
                .sns("jung2")
                .build();
            spaceService.enterSpace(spaceEnterRequest);

            spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan1210@spacer.com")
                .profileImage("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/profile_et_7.png")
                .name("한소희")
                .description("힘들다 지금은 몇시지")
                .memberCategoryId(Long.valueOf(6))
                .isAdmin(false)
                .birthdate("2011/06/06")
                .email("aa9919dd5@spacer.com")
                .sns("jfd")
                .build();
            spaceService.enterSpace(spaceEnterRequest);

            spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan1211@spacer.com")
                .profileImage(defaultProfileImage)
                .name("오지희")
                .description("잘부탁!!")
                .memberCategoryId(Long.valueOf(6))
                .isAdmin(false)
                .birthdate("1967/06/06")
                .email("aa9on35@spacer.com")
                .sns("jfd")
                .build();
            spaceService.enterSpace(spaceEnterRequest);

            spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan1212@spacer.com")
                .profileImage(defaultProfileImage)
                .name("김나연")
                .description("나연나연")
                .memberCategoryId(Long.valueOf(7))
                .isAdmin(false)
                .birthdate("1967/06/06")
                .email("aa9on35@spacer.com")
                .sns("jfd")
                .build();
            spaceService.enterSpace(spaceEnterRequest);

            spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan1213@spacer.com")
                .profileImage(defaultProfileImage)
                .name("고창석")
                .description("나 싱글인디 창석이여")
                .memberCategoryId(Long.valueOf(6))
                .isAdmin(false)
                .birthdate("1970/06/06")
                .email("aa9on35@spacer.com")
                .sns("jfd")
                .build();
            spaceService.enterSpace(spaceEnterRequest);
        }

        // 카테고리 생성
        public void makeCategory(){
            List<CategoryList> categoryLists = new ArrayList<>();
            categoryLists.add(new CategoryList("과제공지", true));
            categoryLists.add(new CategoryList("정보공유", false));

            CreatePostCategoryRequestDTO categoryRequest = CreatePostCategoryRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .categoryList(categoryLists)
                .memberEmail("suhan123@spacer.com")
                .build();

            postCategoryService.create(categoryRequest);
        }

        // 공지 게시글 작성 // 운영(16,4) 기획(17,5) 디쟌(18,6) 개발(19,7)
        public void noticePost(){
            List<String> noImages = new ArrayList<>();
            List<Long> noticeTargetsAll = new ArrayList<>();
            noticeTargetsAll.add(Long.valueOf(4));
            noticeTargetsAll.add(Long.valueOf(5));
            noticeTargetsAll.add(Long.valueOf(6));
            noticeTargetsAll.add(Long.valueOf(7));

            List<Long> noticeTargetsDesign = new ArrayList<>();
            noticeTargetsAll.add(Long.valueOf(6));

            List<Long> noticeTargetsPM = new ArrayList<>();
            noticeTargetsAll.add(Long.valueOf(5));

            List<Long> noticeTargetsDeveloper = new ArrayList<>();
            noticeTargetsAll.add(Long.valueOf(7));

            WriteNoticeRequestDTO writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan123@spacer.com")
                .postCategoryId(Long.valueOf(2))
                .targets(noticeTargetsAll)
                .images(noImages)
                .title("HEYJ 임원진 연락처")
                .description("회장 김수한 : 010-9184-0000\n부회장 이선호 : 010-3443-0000\n총무 김재영 : 010-4244-0000\n운영팀장 박지은 : 010-9301-0000\n미디어팀장 한정민 : 010-5627-0000\n기획 파트장 권혁수 : 010-0000-5702\n디자인 파트장 김지영 : 010-0000-3858/n서버 파트장 김성권 : 010-0000-7226\n안드로이드 파트장 박진형 : 010-2287-0000/niOS 파트장 박지훈 : 010-0000-7236\n웹파트장 권진희 : 010-0000-1928")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

            writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan123@spacer.com")
                .postCategoryId(Long.valueOf(2))
                .targets(noticeTargetsAll)
                .images(noImages)
                .title("오리엔테이션 안내")
                .description("이번주 토요일에 진행되는 OT에 대해 공지드립니다! 아래 내용을 참고해주세요~\n✅ 타임라인\n15:00 - 15:30OT 줌 접속, 출석체크\n15:30 - 16:00 SOPT 소개, 임원진 소개\n16:00 - 16:30 SOPT 운영진 공지\n16:30 - 16:45 쉬는 시간\n16:45 - 17:30 운영팀 레크레이\n17:30 - 18:00 파트별 시간\n✅ 출석체크를 꼭 제시간에 부탁드립니다.\n출석 체크 시 자리에 없으시면 출석으로 인정받으실 수 없습니다. \n출석은 불시에 여러 차례 진행됨으로 본인의 와이파이 상태, 주변 상황을 항상 체크해주세요.")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

            writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan123@spacer.com")
                .postCategoryId(Long.valueOf(19))
                .targets(noticeTargetsAll)
                .images(noImages)
                .title("버디버디 미션 공지")
                .description("안녕하세요! 버디버디는 헤이제이의 엄청나고 대단한 재밌는 이벤트 입니다! 여러분 모두 버디버디 하세요~\nOT 오프라인 인원이 4명 미만일 경우 버디버디 팀 내에서 자율적으로 조정 가능합니다!🙌\n버디버디 지역은 28기 지원서에 써있듯이 활동지역 및 역을 기준으로 지정되었음으로 이 또한 변경이 어렵습니다.")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

            writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan123@spacer.com")
                .postCategoryId(Long.valueOf(2))
                .targets(noticeTargetsAll)
                .images(noImages)
                .title("OT ZOOM 접속 안내")
                .description("안녕하세요 여러분!\nOT ZOOM 접속은 아래 링크를 이용해주세요 :)\n15:00~15:30까지 입장후 화면을 참고해 1차 출석인증을 부탁드립니다!\n줌 접속 이름은 '버디버디몇조_이름' (ex)27조_이희원) 으로 변경해주세요\n모든 버디버디 조원이 출석인증을 했다면 조장님은 줌 채팅방에 \"OO조 전원 출석\"이라고 부탁드립니다!\n▶ Zoom 회의 참가\nhttps://zoom.us/j/7042627360?pwd=N2Y0WW9tTUUyQ2JBQnFveThaYlk0UT09")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

            writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan123@spacer.com")
                .postCategoryId(Long.valueOf(2))
                .targets(noticeTargetsAll)
                .images(noImages)
                .title("스터디 안내")
                .description("HEYJ 28기 스터디 개설 및 모집과 관련한 노션 페이지를 공개합니다.\n(https://www.notion.so/28-c079e68c63a84eb287288ed92b32593e)\n(28기 스터디 노션 페이지 편집권한은 오늘중으로 각파트장님들이 사용자 추가를 해드릴 예정입니다)")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

            writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan123@spacer.com")
                .postCategoryId(Long.valueOf(19))
                .targets(noticeTargetsAll)
                .images(noImages)
                .title("버디버디 2차 미션")
                .description("안녕하세요 운영팀장 황지은입니다.\nOT 때 안내드린 버디버디 미션에 대해 공지드립니다!\n버디버디 미션은 2가지가 있습니다.\n1. 버디버디 소개글을 작성해 주세요. (4월 2일 금요일 23:59까지 -> 1차 세미나 전날까지)\n2. 온라인 네트워킹 가이드를 참고해서 버디버디조와 즐거운 시간을 보낸 인증 글을 올려주세요. (4월 30일 금요일 23:59까지 -> 1차행사 전날까지)")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

            writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan123@spacer.com")
                .postCategoryId(Long.valueOf(19))
                .targets(noticeTargetsDesign)
                .images(noImages)
                .title("디자인 1차 과제 공지")
                .description("디자인 파트 여러분 첫번째 과제를 공지합니다! 다들 아래 내용을 참고하시고 과제 모두 해오세요~~\n온라인, 오프라인모임 모두 재밌고! 유익한! 세미나 되시길 바랍니다💙")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

            writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan123@spacer.com")
                .postCategoryId(Long.valueOf(19))
                .targets(noticeTargetsDeveloper)
                .images(noImages)
                .title("개발 1차 과제")
                .description("안녕하세요 여러분! 개발파트 첫번째 과제는 공식냠냠입니다! \n 공식냠냠이란?\n각자 공부한걸 발표하는 시간입니다 룰루랄라~~")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

            List<String> images = new ArrayList<>();
            images.add("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/notice_9.png");

            writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan123@spacer.com")
                .postCategoryId(Long.valueOf(2))
                .targets(noticeTargetsAll)
                .images(images)
                .title("중간고사 휴식 안내")
                .description("토요일 대학교 중간고사 (다른 일정 및 시험은 인정되지 않습니다.)\n세미나 중 1시간 이상 참석\n위 두가지를 모두 만족할 경우 '지각'으로 판단하여 출석 점수를 '-0.5'점만 감점 합니다.\n❗️따라서, 4/24 토요일 대학교 중간고사 일정으로 세미나를 모두 참석하지 못하는 분들은 아래의 내용을 숙지하여 불가피한 결석 처리를 면하시길 바랍니다! ")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);


            List<Long> noticeTargetsPMDesign = new ArrayList<>();
            noticeTargetsAll.add(Long.valueOf(5));
            noticeTargetsAll.add(Long.valueOf(6));

            images = new ArrayList<>();
            images.add("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/notice_10.png");

            writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan123@spacer.com")
                .postCategoryId(Long.valueOf(2))
                .targets(noticeTargetsPMDesign)
                .images(images)
                .title("기획/디자인 특별 세미나")
                .description("기획 파트 3차 심화 세미나 공지\n 안녕하세요 HEYJ 파트원 여러분! \n시험기간에 모두 고생 많으십니다\n공부에 지치셨을 파트원 분들을 위해 귀가 쫑긋할 만한 소식을 들고 왔는데요!\n4/28 수요일에 열릴 3차 심화 세미나에서는 덕질 필수 어플 '블립'의 프로덕트 매니저이신 최우창 연사님이 직접! 참여 하여 고객 인터뷰를 다루는 기획 파트 심화 세미나를 함께 해주실 예정입니다. 비 기획 파트의 신청도 받고 있으니 많은 관심 부탁드려요~\n \n \n 최우창 연사님 이력/n- (현) '블립' 앱 프로덕트 매니저\n- (전) '리디북스' 마케팅 팀\n- (전) '퍼블리' 마케팅 및 프로젝트 매니저\n \n 📝신청 기간: 4/22 (목) 22시 ~ 4/25 (일) 낮 12시\n📝신청 인원: 40명\n❗신청은 선착순 기준이며, 비 기획파트의 경우 기획 파트 인원을 제외한 여석만큼 참가할 수 있습니다.!\n📝신청 방법: 4월 22일 목요일 10시에 신청 링크가 SOPT 28기 전체 공지방에 올라올 예정입니다.\n \n 이런 분들이 신청하시면 좋아요!\n- 3차 정규 세미나에서 진행될 고객 인터뷰 내용을 실무적으로 어떻게 반영하는지 알고 싶다!\n- 고객 인터뷰의 내용을 바탕으로 소통하는 방법이 알고 싶다!\n- 고객 인터뷰의 결과를 팀이랑 소통하는 과정에 대해 알고 싶다!\n \n 많은 관심 부탁드려요~")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

            writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(1))
                .memberEmail("suhan123@spacer.com")
                .postCategoryId(Long.valueOf(19))
                .targets(noticeTargetsDesign)
                .images(noImages)
                .title("디자인 특별 자료 공유")
                .description("여러분 3차 세미나도 유익하셨나요?\n시험기간으로 한 주 쉬고 만났는데요!\n시험기간에도 많은 분들이 솝포메이션을 공유해주셨어요 ><\n파트별 도움되는 자료\n'앱스토어/플레이스토어업로드용 아이콘 자동 생성 사이트'\nby iOS 파트장 지훈님\n'눈길 끄는 앱 만드는 9가지 애니메이션'\nby 승우님")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

        }

        // 커뮤니티 게시글 작성
        public void communityPost(){
            // 커뮤니티 게시글 생성
            List<String> noImages = new ArrayList<>();

            WriteCommunityRequestDTO writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(1),
                "suhan124@spacer.com", Long.valueOf(3), noImages, "안녕하세요 기획팀 이정일입니다! 요즘날씨가 추워졌는데 방어 좋아하시나요?");
            communityPostService.write(writeCommunityRequest);

            writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(1),
                "suhan125@spacer.com", Long.valueOf(20), noImages, "IT 컨퍼런스 초대권이 2장 생겼어요 관심있으신분 있나요? 방가방가 햄톨스~~~");
            communityPostService.write(writeCommunityRequest);

            writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(1),
                "suhan126@spacer.com", Long.valueOf(3), noImages, "방어파티 4명 구합니다! 저랑 방어 먹으로 가실분? 장소는 홍대 바다회사랑입니다~~ 배고프다");
            communityPostService.write(writeCommunityRequest);

            writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(1),
                "suhan127@spacer.com", Long.valueOf(20), noImages, "안녕하세요~ 디자인팀 동식입니다! 제가 일하는 곳에서 알바를 구해요 저 대신 일할래요?");
            communityPostService.write(writeCommunityRequest);

            writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(1),
                "suhan128@spacer.com", Long.valueOf(3), noImages, "여러분 내일 홍대에서 모각공 하실래요?! 근데 전 안가요 ㅎㅎ");
            communityPostService.write(writeCommunityRequest);

            writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(1),
                "suhan129@spacer.com", Long.valueOf(20), noImages, "[spacer 이희원 대표님 온라인 강연] 제가 너무 좋아하는 기업의 대표님 강연이 있어요! 듣던가 말던가");
            communityPostService.write(writeCommunityRequest);

            writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(1),
                "suhan1210@spacer.com", Long.valueOf(20), noImages, "정보공유 합니다 ! 노트북 중에 애플이 가장 좋은거 아시나요? 왤까요");
            communityPostService.write(writeCommunityRequest);

            writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(1),
                "suhan1211@spacer.com", Long.valueOf(20), noImages, "안녕하세요 개발팀 오지희입니다! 지인이 프로젝트 팀원을 구하고 있어서 공유합니다");
            communityPostService.write(writeCommunityRequest);

            writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(1),
                "suhan1212@spacer.com", Long.valueOf(20), noImages, "figma의 flame 기능을 아시나요?! 디자인을 완전 편하게 할 수 있는 개꿀팁! 대방출출출");
            communityPostService.write(writeCommunityRequest);

            List<String> images = new ArrayList<>();
            images.add("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/community_10-1.png");
            images.add("https://crewspace.s3.ap-northeast-2.amazonaws.com/dummy/community_10-2.png");

            writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(1),
                "suhan1213@spacer.com", Long.valueOf(20), noImages, "애플 컨퍼런스 정보 공유합니다~ \n 애플에서 2021 하반기 컨퍼런스를 한대요! \n 디자인, 개발 세션이 있으니 관심있는 분들 신청하시면 좋을거 같아요 \n 대학교 재학중이신 분들은 무료로 참석이 가능하고, 졸업생 분들은 사전신청하면 1만원에 가능합니다! \n 아래 정보 확인하시구 다들 컨퍼런스 참여해보세요~ \n \n \n ▶행사명 : 2021 애플 오픈 컨퍼런스 KOREA\n ▶일시 : 2021년 12월 15일\n ▶개요 : 애플최고 애플사랑해 \n ▶후원 : 이희원 대표이사, spacer, 원티드\n <이벤트>\n 사전신청자 대상으로 이벤트를 진행합니다! \n -이벤트 참여자 전원 상품 증정! \n 참여방법 : 선착순 사전신청(200명) \n 상품 \n 맥프로 : 1명 \n 아이폰13 : 2명 \n 애플워치 : 3명 \n 에어팟 프로 : 5명 \n 참여자 전원 2021 애플 굿즈 증정! \n \n \n 문의 : 010-2021-2021");
            communityPostService.write(writeCommunityRequest);
        }

        public void dbInit() {
            member();
            createSpace();// 운영(16,4) 기획(17,5) 디쟌(18,6) 개발(19,7)
            spaceEnter();
            makeCategory();
            noticePost();
            communityPost();
        }
    }

}