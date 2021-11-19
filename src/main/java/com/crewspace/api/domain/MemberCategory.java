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
public class MemberCategory {

    @Id @GeneratedValue
    @Column(name = "member_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_member_id")
    private SpaceMember spaceMember;

    private String name;

    @Builder
    public MemberCategory(Space space, SpaceMember spaceMember, String name) {
        this.space = space;
        this.spaceMember = spaceMember;
        this.name = name;
    }
}
