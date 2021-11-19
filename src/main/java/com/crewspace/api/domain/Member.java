package com.crewspace.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String oauthId;
    private String email;
    private Boolean push;

    @Enumerated(EnumType.STRING)
    private Authority authority;
}
