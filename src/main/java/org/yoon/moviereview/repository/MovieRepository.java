package org.yoon.moviereview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yoon.moviereview.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select m, avg(coalesce(r.grade,0)), count(distinct r) from " +
            "Movie m left outer join Review r on r.movie = m group by m")
    Page<Object[]> getListPage(Pageable pageable);
}
