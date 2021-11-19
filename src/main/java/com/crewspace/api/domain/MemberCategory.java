package com.crewspace.api.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "memberCategory", cascade = CascadeType.ALL)
    private List<SpaceMember> spaceMembers = new ArrayList<>();

    private String name;
    private Boolean isAdmin;

    @Builder
    public MemberCategory(Space space, String name, Boolean isAdmin) {
        this.space = space;
        this.name = name;
        this.isAdmin = isAdmin;
    }
}
