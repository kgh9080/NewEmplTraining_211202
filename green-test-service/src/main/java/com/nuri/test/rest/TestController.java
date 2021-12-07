package com.nuri.test.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(value="/upload1")  //method = RequestMethod.GET)
@Slf4j
public class TestController {
    @GetMapping("/options")
    public String uploading(){
        log.info("upload options1111111111111111111");
        return "upload1에 들어옴";
    }
}
