package spring.jpa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import spring.jpa.domain.Member;
import spring.jpa.repository.MemberRepository;

@Service
@Transactional(readOnly = true)  // 스프링이 제공하는 @Transactional 권장
// @AllArgsConstructor // 모든 필드멤버의 생성자를 만들어줌
@RequiredArgsConstructor  // final이 붙은 모든 필드멤버의 생성자를 만들어줌
public class MemberService {

  private final MemberRepository memberRepository;

  // public MemberService(
  //   MemberRepository memberRepository
  // ) {
  //   this.memberRepository = memberRepository;
  // }

  /**
   * 회원 가입
   */
  @Transactional
  public Long join(Member member) {
    validateDuplicateMember(member);  // 중복회원 검증
    memberRepository.save(member);

    return member.getId();
  }

  /**
   * 회원 조회 - 전체
   */
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  /**
   * 회원 조회 - 단건
   */
  public Member findMember(Long memberId) {
    return memberRepository.findOne(memberId);
  }

  public void validateDuplicateMember(Member member) {
    List<Member> findMembers = memberRepository.findByName(member.getName());

    if(!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }
}
