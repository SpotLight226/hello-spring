package hello.hellospring.Repository;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest { // 다른 곳에 사용될 일이 없으므로 default클래스로
    // repository 테스트
    // 만들시 같은 이름 패키지, 이름에 Test 를 붙인다
    // repository를 test할 객체 생성
    MemoryMemberRepository repository = new MemoryMemberRepository();
    // TEST는 서로 의존관계가 없이 설계되어야 한다
    // 저장소나 공용데이터를 하나가 끝나면 비워주어야 한다
    @AfterEach // 메서드가 동작이 끝나면 동작하는 메서드 afterEach
    public void afterEach(){
        repository.clearStore(); // 테스트 하나가 끝날 때마다 clearStore를 호출하여 저장소 비움
    }

    @Test // @Test를 입력시, org.junit.jupiter.api를 import해준다 -> 실행 가능
    public void save(){
        // member객체 생성 및 name을 지정
        Member member = new Member();
        member.setName("spring");

        //member 저장
        repository.save(member);
        //Optoinal에서 값을 꺼낼 때는 .get() -> Test에서만
        Member result = repository.findById(member.getId()).get();
        //Assertions.assertEquals -> 같은지 확인할 수 있다
//        Assertions.assertEquals(result, member); // JUnit
        //assertj.core -> 좀 더 변하게 해주는 것
        // assertThat(객체) -> result 와 같은지 판단
        // Assertions 는 static import 해서 사용한다
        // Assertions에서 alt + Enter -> static import
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member(); // Member객체 만들고
        member1.setName("spring1"); // 이름을 설정
        repository.save(member1); // repository에 저장
        
        // 변수 이름 동시에 바꾸기 -> shift + f6
        Member member2 = new Member(); // Member객체 만들고
        member2.setName("spring2"); // 이름을 설정
        repository.save(member2); // repository에 저장

        // ctrl + alt + v -> 결과를 저장할 객체타입 + 객체명이 한번에
        // Optional 에서 .get() 하면 값을 꺼낼 수 있다
        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1); // 찾은 객체가 member와 같을 때 통과

    }

    // class에서 Test돌렸을 때 findAll이 먼저 실행되어, findByName의 객체와 다른 member1이 저장됨
    // Test가 하나 끝나면, 데이터를 clear해주어야 한다
    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // findAll 메서드 실행하여 list 반환
        List<Member> result = repository.findAll();
        // result의 사이즈가 2와 같으면 통과
        assertThat(result.size()).isEqualTo(2);
    }

 }
