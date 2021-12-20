package org.yoon.moviereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yoon.moviereview.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
