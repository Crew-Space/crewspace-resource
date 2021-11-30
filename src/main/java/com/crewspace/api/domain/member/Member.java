package com.crewspace.api.domain.member;

import com.crewspace.api.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String oauthId;
    private String email;
    private String image;
    private String nickname;
    private Boolean push;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    // DB Init 용 public 생성자 -> 이후에 지우
    public Member(String oauthId, String email, String image, String nickname, Boolean push) {
        this.oauthId = oauthId;
        this.email = email;
        this.image = image;
        this.nickname = nickname;
        this.push = push;
    }
}
