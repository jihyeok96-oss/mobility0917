package com.oji.mobility.repository;

import com.oji.mobility.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<Board> searchByQuerydsl(String keyword, String userid, Pageable pageable);
}
