package org.yoon.moviereview.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yoon.moviereview.entity.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    //type= EntityGraph.EntityGraphType.LOAD : attributePaths 정의한 멤버변수는 EAGER 로 불러오고 나머지 멤버변수는 각자의 FetchType을 존중해서 불러온다.
    //type= EntityGraph.EntityGraphType.FETCH : attributePaths 정의한 멤버변수만 EAGER 로 불러오고 나머지는 LAZY 로 불러온다. 즉 멤버변수의 FetchType이 뭐였는지 신경쓰지 않는다
    @EntityGraph(attributePaths = "writer", type= EntityGraph.EntityGraphType.LOAD)
    @Query("select n from Note n where n.num= :num")
    Optional<Note> getWithWriter(Long num);

    @EntityGraph(attributePaths ={"writer"}, type = EntityGraph.EntityGraphType.LOAD )
    @Query("select n from Note n where n.writer.email= :email")
    List<Note> getList(String email);

}
