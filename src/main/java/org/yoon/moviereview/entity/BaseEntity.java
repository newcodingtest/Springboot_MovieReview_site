package org.yoon.moviereview.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass//테이블로 생성되지 않음
@EntityListeners(value={AuditingEntityListener.class})//JPA 내부에서 엔티티 객체가 변화를 감지한다.
@Getter
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "regdate", updatable = false) //updatable = false: 엔티티 객체가 db에 반영될때 해당컬럼값은 변경되지 않음ㄴ
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
