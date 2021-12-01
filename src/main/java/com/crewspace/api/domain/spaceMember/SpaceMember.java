package com.crewspace.api.domain.spaceMember;

import com.crewspace.api.domain.BaseTimeEntity;
import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.space.Space;
import com.crewspace.api.dto.req.spaceMember.ModifyMemberRequestDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class SpaceMember extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "space_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_category_id")
    private MemberCategory memberCategory;

    private Boolean isAdmin;

    private String image;
    private String name;
    private String description;

    private String birthdate;
    private String email;
    private String contact;
    private String sns;
    private String etc;

    private Boolean noticePush;
    private Boolean myNoticePush;
    private Boolean communityPush;

    // 연관 관계 메서드
    public void setMemberCategory(MemberCategory memberCategory){
        this.memberCategory = memberCategory;
        memberCategory.getSpaceMembers().add(this);
    }
    // 생성 메서드
    @Builder
    public SpaceMember(Space space, Member member,
        MemberCategory memberCategory, Boolean isAdmin, String image, String name, String description,
        String birthdate, String email, String contact, String sns, String etc) {
        this.space = space;
        this.member = member;
        this.setMemberCategory(memberCategory);

        this.isAdmin = isAdmin;
        this.image = image;
        this.name = name;
        this.description = description;

        this.birthdate = birthdate != null ? birthdate : "";
        this.email = email != null ? email : "";
        this.contact = contact != null ? contact : "";
        this.sns = sns != null ? sns : "";
        this.etc = etc != null ? etc : "";

        if(member.getPush()){
            this.noticePush = true;
            this.myNoticePush = true;
            this.communityPush = true;
        }else{
            this.noticePush = false;
            this.myNoticePush = false;
            this.communityPush = false;
        }
    }

    // 업데이트 메서드
    public void update(ModifyMemberRequestDTO requestDTO, MemberCategory memberCategory){
        this.image = requestDTO.getProfileImage().length() > 0 ? requestDTO.getProfileImage() : this.image;
        this.name = requestDTO.getName() ;
        this.description = requestDTO.getDescription();
        this.memberCategory = memberCategory;
        this.isAdmin = memberCategory.getIsAdmin();
        this.birthdate = requestDTO.getBirthdate();
        this.email = requestDTO.getEmail();
        this.contact = requestDTO.getContact();
        this.sns = requestDTO.getSns();
        this.etc = requestDTO.getEtc();
    }
}
