package com.crewspace.api.domain;

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
public class SpaceMember extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "space_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String image;
    private String name;
    private String description;

    private String birthdate;
    private String email;
    private String contact;
    private String sns;

    // 생성 메서드
    @Builder
    public SpaceMember(Space space, Member member, String image, String name,
        String description, String birthdate, String email, String contact, String sns) {

        this.space = space;
        this.member = member;

        this.image = image;
        this.name = name;
        this.description = description;

        this.birthdate = birthdate;
        this.email = email;
        this.contact = contact;
        this.sns = sns;
    }
}