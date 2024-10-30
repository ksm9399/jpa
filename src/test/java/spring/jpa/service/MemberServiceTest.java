package spring.jpa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import spring.jpa.domain.Member;
import spring.jpa.repository.MemberRepository;

@SpringBootTest
@Transactional
public class MemberServiceTest {

  @Autowired
  MemberService memberService;

  @Autowired
  MemberRepository memberRepository;

  @Test
  public void 회원가입() throws Exception {
    // given
    Member member = new Member();
    member.setName("kim");

    // when
    Long saveId = memberService.join(member);

    // then
    assertEquals(member, memberRepository.findOne(saveId));

  }

  @Test
  public void 중복_회원_가입() throws Exception {
    // given
    Member member1 = new Member();
    Member member2 = new Member();

    member1.setName("kim");
    member2.setName("kim");

    // when
    memberService.join(member1);

    // then
    assertThrows(IllegalStateException.class, () -> {
      memberService.join(member2);
    });
  }
}
