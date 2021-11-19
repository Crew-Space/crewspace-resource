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
public class PostCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    private String name;

    private Boolean isNotice;

    @Builder
    public PostCategory(Space space, String name, Boolean isNotice) {
        this.space = space;
        this.name = name;
        this.isNotice = isNotice;
    }
}
