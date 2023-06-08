package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    // 테스트 케이스 만드는 단축키 ctrl + shift + t
    // Repository 는 단순히 저장소에 저장, 꺼내기 -> 좀 더 기계스럽게
    // Service 는 좀 더 비지니스에 가깝다 -> 비지니스에 가까운 용어를 사용한다

    // 여기서 repository를 생성하는 것이 아닌
    private final MemberRepository memberRepository;
    // 외부에서 repository를 넣어주게 변경
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public long join(Member member){
        //같은 이름이 있는 중복 회원 x
        // Optional 에 많은 메서드가 있어서 사용가능
        // Optional로 감싸서 가져오기때문에 null이라도 Optional로 감싸서 보내준다
        // Optional을 바로 쓰는 것은 권장되지 않는다
        // Optional 을 바로 쓸 때
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다");
//        });
        // findByName 을 하면 Optional 객체이기 때문에 바로 .ifPresent 를 사용하여 중복을 확인할 수 있다
        // 로직이 쭉 나오면 메서드로 뽑는 것이 좋다
        // 리펙토링 단축키 : ctrl + alt + shift + t
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member); // 중복 회원 검증 통과하면 저장
        // Id를 반환한다
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다");
                        });
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    // 회원 하나만 찾기 -> 아이디에 해당하는 회원
    public Optional<Member> findOne(long memberId){
        return memberRepository.findById(memberId);
    }
}
