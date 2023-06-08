package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MemberServiceTest {
    // shift + f10 : 이전 실행을 다시 실행
    //테스트 위해 서비스 생성
    MemberService memberService;
    // memberService 만 있으니 clear 불가
    // repository를 가져온다 -> MemberService에서 만든 repository 객체와 다른 객체다
    // 같은 것으로 테스트하기 위해
    MemoryMemberRepository memberRepository; // 선언만

    // test 실행 전, 수행하는 메서드
    @BeforeEach
    public void beforEach() {
        // memberRepository를 만들고
        memberRepository = new MemoryMemberRepository();
        // memberService를 생성할 때, 넣어준다
        memberService = new MemberService(memberRepository);
        // memberService에서 memberRepository만들어 넣어준다
        // memberService입장에서는 외부에서 만들어 넣어주는 것으로 DI 방식이다
    }

    @AfterEach // test가 돌 때마다 DB의 값을 다 날려준다
    public void afterEach(){
        memberRepository.clearStore();
    }

    // build 될 때 테스트는 포함되지 않는다
    //  test는 한글로 작성해도 OK : 테스트 케이스 이므로
    // test 문법 : given(이 데이터를 기반), when(이걸 검증), then(검증부)
    @Test
    void 회원가입() {
        //given
        // member 객체 생성
        Member member = new Member();
        // name을 설정
        member.setName("hello");
        //when
        //Service의 join을 검증
        // join은 member의 id를 반환한다
        long saveId = memberService.join(member);

        //then
        // findOne : id를 반환하는 메서드
        Member findMember = memberService.findOne(saveId).get();
        //assertj를 임포트
        // member의 name이 찾은 findMember의 name과 같은지 판단
        // Assertions을 static 임포트로 넘긴다
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    // 중복 회원 검증 테스트
    @Test
    public void 중복_회원_예외() {
        //given
        //같은 이름 회원 제작
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        // 같은 이름이므로 validateDuplicateMember에서 예외가 터져야 한다
        memberService.join(member1);
        //기대 값 : exception , () : 로직을 태운다, join(member2) 해서 예외가 발생하게 람다식 작성
        // 예외가 터져야 성공
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // assertThat으로 오류 메세지를 같은 지 확인
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
       /*
        try {
            memberService.join(member2);
            // 예외를 발생시켜 확인
            // 예외가 발생하면 fail의 구문이 실행되지 않고 Exception으로 간다
            fail(" 예외가 발생해야 합니다 ");
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123123");
        }
        */
        //then
    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}