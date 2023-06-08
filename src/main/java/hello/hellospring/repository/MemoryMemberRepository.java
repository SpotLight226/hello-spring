package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{
    // implements 한 인터페이스에서 alt + enter 해서 메서드를 선택해서 오바라이드할 메서드 생성 가능
    // 오바라이드 단축키 ctrl + o
    // 임포트 단축키 alt + enter
    private static Map<Long, Member> store = new HashMap<>(); // 회원을 저장할 Map <id, name>
    private static long sequence = 0L; // 키 값을 생성
    @Override
    public Member save(Member member) {
        member.setId(++sequence); // 아이디 세팅 : 시퀀스 값을 하나 올려준다
        store.put(member.getId(), member); // store에 저장 : 저장하기 전에 id값을 세팅하고,
                                           // 이름은 넘어온 상태 (회원가입할 때 적는 이름)
        return member; // 저장된 결과 반환
    }

    @Override
    public Optional<Member> findById(Long id) {
        //store.get(id)가 null 일 수 도 있으니, Optional로 감싼다
        return Optional.ofNullable(store.get(id)); // null이 반환되도 클라이언트에서 작업할 수 있게 Optional로 감싼다
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 루프를 톨려서 member.getName이 매개변수로 들어온 name과 같은지 확인하고, 같은 경우에만 필터링 , 찾으면 반환
        // 람다식으로 value를 루프로 돌린다
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // 하나라도 찾으면 반환
    }

    @Override
    public List<Member> findAll() {
        //store의 membber를 list에 담아 리턴
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear(); // store를 비운다
    }
}
