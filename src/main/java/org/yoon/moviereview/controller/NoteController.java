package org.yoon.moviereview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yoon.moviereview.dto.NoteDTO;
import org.yoon.moviereview.service.NoteService;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/notes/")
public class NoteController {

    private final NoteService noteService;

    @PostMapping(value = "register")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO){
        log.info("-----------register-----------");
        log.info(noteDTO);

        Long num = noteService.register(noteDTO);

        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    //produces: 서버-> 클라이언트의 반환 타입을 강제, consumes: 클라이언트 -> 서버 전송 타입을 강제
    @GetMapping(value="/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDTO> read(@PathVariable("num") Long num){
        log.info("-------- read --------");
        log.info(num);
        return new ResponseEntity<>(noteService.get(num), HttpStatus.OK);
    }

    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteDTO>> getList(String email){
        log.info("----------- getList -----------");
        log.info(email);

        return new ResponseEntity<>(noteService.getAllWithWriter(email),HttpStatus.OK);
    }

    @DeleteMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("num") Long num){
        log.info("----------- remove -----------");
        log.info(num);

        noteService.remove(num);

        return new ResponseEntity<>("removed",HttpStatus.OK);
    }

    @PutMapping(value="/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String>modify(@RequestBody NoteDTO noteDTO){
        log.info("------------- modify ------------- ");
        log.info(noteDTO);

        noteService.modify(noteDTO);

        return new ResponseEntity<>("modified", HttpStatus.OK);
    }
}
