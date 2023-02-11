package com.junkyu.searchblogservice.domain.repository;

import com.junkyu.searchblogservice.domain.BlogData;
import com.junkyu.searchblogservice.domain.repository.custom.BlogDataCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogDataRepository extends JpaRepository<BlogData, Long>, BlogDataCustomRepository {
}
