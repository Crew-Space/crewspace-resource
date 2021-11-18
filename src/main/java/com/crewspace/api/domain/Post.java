package com.crewspace.api.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_member_id")
    private SpaceMember author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImages = new ArrayList<>();

    private String description;
}
