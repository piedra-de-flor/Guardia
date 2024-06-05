package com.capstone.capstonedesign.domain.entity.membership;

import com.capstone.capstonedesign.domain.entity.protectedTarget.ProtectedTarget;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nickName;
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ProtectedTarget> protectedTargets = new ArrayList<>();

    @Builder
    public Member(String name, String email, String password, List<String> roles) {
        this.nickName = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String updateNickName(String nickName) {
        this.nickName = nickName;
        return this.nickName;
    }

    public Boolean updatePassword(String password) {
        this.password = password;
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
