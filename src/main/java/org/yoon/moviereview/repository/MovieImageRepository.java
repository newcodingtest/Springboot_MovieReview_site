package org.yoon.moviereview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yoon.moviereview.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

}
