package org.yoon.moviereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yoon.moviereview.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
