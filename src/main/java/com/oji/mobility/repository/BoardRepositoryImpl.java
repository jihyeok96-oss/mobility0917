package com.oji.mobility.repository;

import com.oji.mobility.domain.Board;
import com.oji.mobility.domain.QBoard;
import com.oji.mobility.domain.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Board> searchByQuerydsl(String keyword, String userid, Pageable pageable) {
        QBoard b = QBoard.board;    //Board 에서 가상테이블을 불러온 것
        QMember m = QMember.member;

        BooleanBuilder condition = new BooleanBuilder();

        if (keyword != null && !keyword.isBlank()) {
            condition.and(
                    b.title.containsIgnoreCase(keyword)
                            .or(b.content.containsIgnoreCase(keyword))
                            .or(m.name.containsIgnoreCase(keyword))
            );
        }

        if (userid != null && !userid.isBlank()) {
            condition.and(m.userid.eq(userid));
        }

        List<Board> content = jpaQueryFactory
                .selectFrom(b)
                .join(b.member, m).fetchJoin()
                .where(condition)
                .orderBy(b.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(b.count())
                .from(b)
                .join(b.member, m)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }
}
