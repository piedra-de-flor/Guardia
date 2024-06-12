package com.capstone.capstonedesign.service.membership;

import com.capstone.capstonedesign.domain.entity.membership.Member;
import com.capstone.capstonedesign.dto.membership.JwtToken;
import com.capstone.capstonedesign.dto.membership.MemberResponseDto;
import com.capstone.capstonedesign.dto.membership.MemberSignUpRequestDto;
import com.capstone.capstonedesign.repository.MemberRepository;
import com.capstone.capstonedesign.service.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository repository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDto signUp(MemberSignUpRequestDto signUpDto) {
        if (repository.findByEmail(signUpDto.email()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 email 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signUpDto.password());
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        Member savedMember = repository.save(signUpDto.toEntity(encodedPassword, roles));
        return MemberResponseDto.fromUser(savedMember);
    }

    @Transactional
    public JwtToken signIn(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }

    public MemberResponseDto read(String email) {
        Member member =repository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);

        MemberResponseDto response = MemberResponseDto.fromUser(member);
        return response;
    }

    @Transactional
    public String updateNickName(String email, String nickName) {
        Member member =repository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);

        String response = member.updateNickName(nickName);
        return response;
    }

    @Transactional
    public Boolean updatePassword(String email, String password) {
        Member member =repository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);

        boolean response = member.updatePassword(password);
        return response;
    }

    @Transactional
    public boolean delete(String email) {
        Member member = repository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);

        repository.delete(member);
        return true;
    }
}
