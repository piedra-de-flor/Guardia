package com.capstone.capstonedesign.domain.entity;

import com.capstone.capstonedesign.domain.vo.Face;
import com.capstone.capstonedesign.domain.vo.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ProtectedTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private Image image;
    private Face face;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public ProtectedTarget(Member member, String name, int age, Image image) {
        this.member = member;
        this.name = name;
        this.age = age;
        this.image = image;
    }

    public void update(String name, String age) {
        if (name != null) {
            this.name = name;
        }
        if (age != null) {
            this.age = Integer.parseInt(age);
        }
    }

    public boolean isMine(Member member) {
        return this.member.getId() == member.getId();
    }
}
