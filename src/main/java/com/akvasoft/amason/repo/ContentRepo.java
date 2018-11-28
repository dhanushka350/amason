package com.akvasoft.amason.repo;

import com.akvasoft.amason.common.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepo extends JpaRepository<Content, Integer> {
    boolean existsByProductTitleEquals(String s);
}
