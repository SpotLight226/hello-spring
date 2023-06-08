package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    // Optional : find할 때 없다면 null이 반환된다 -> Optional로 감싸서 반환하게 만든다
    Member save(Member member); // 저장소에 저장
    Optional<Member> findById(Long id); // id로 member를 찾는다
    Optional<Member> findByName(String name); // name으로 member을 찾는다
    List<Member> findAll(); // 모든 회원리스트 반환
}
