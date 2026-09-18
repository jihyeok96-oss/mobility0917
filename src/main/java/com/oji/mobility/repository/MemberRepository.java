package com.oji.mobility.repository;

import com.oji.mobility.domain.Member;
import com.oji.mobility.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserid(String userid);

    boolean existsByUserid(String userid);
    boolean existsByName(String name);
    boolean existsByTel(String tel);
    boolean existByEmail(String email);

    long countByRole(String role);

    List<Member> findByRoleOrderByCreatedAtDesc(Role role);

    List<Member> findByDeletedAtIsNull();

    long countByCreatedAtAfter(LocalDateTime dateTime);
    long countByCreatedAtBefore(LocalDateTime dateTime);

    List<Member> findTop10ByOrderByCreatedAtDesc();


    //select * from member where name like '%' + keyword + '%'
    List<Member> findByNameContaining(String keyword);

    List<Member> findByNameStartingWith(String prefix);

    List<Member> findByNameEndingWith(String suffix);

    List<Member> findByNameContainingIgnoreCase(String keyword);

    Page<Member> findByNameContainingOrUseridContaining(String nameKeyword, String userKeyword, Pageable pageable);

    @Query("SELECT m FROM member m " +
            "WHERE name Like %:keyword% " +
            "OR email Like %:keyword% " +
            "OR email Like %: keyword% " +
            "OR tel Like %:keyword%")
    List<Member> serachMemberList(
            @Param("keyword") String keyword, Pageable pageable);


}
