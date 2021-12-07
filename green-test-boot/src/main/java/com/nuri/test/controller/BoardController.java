package com.nuri.test.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(value="/board")
@Slf4j
public class BoardController {

    @GetMapping("/list")
    public String list(){
        log.info("-------------------------------");
        log.info("list");
        log.info("-------------------------------");

        return "board/list";
    }

    @GetMapping("/index")
    public String index(){
        log.info("-------------------------------");
        log.info("index");
        log.info("-------------------------------");

        return "index";
    }

}
