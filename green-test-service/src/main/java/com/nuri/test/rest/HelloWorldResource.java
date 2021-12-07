package com.nuri.test.rest;

import com.nuri.test.domain.entity.HelloWorld;
import com.nuri.test.domain.service.HelloWorldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(value="/test")  //method = RequestMethod.GET)
@Slf4j
public class HelloWorldResource {
    private final HelloWorldService helloWorldService;

    @GetMapping
    public List<HelloWorld> data1(int id){
        HelloWorld helloWorld1 = new HelloWorld();
        helloWorld1.setTestId(id);
        return helloWorldService.findByCondition(helloWorld1);

    }


    @GetMapping("{id}")
    /*@ResponseBody*/ //요청값을 받고 응답해주기(ResponseBody + Controller > RestController) //RequestBody는 매개변수를 사용하여 요청값 받기(뷰O) ← rest방식
    public String data2(@PathVariable("id") int id){
        HelloWorld helloWorld2 = new HelloWorld();
        helloWorld2.setTestId(id);
        List<HelloWorld> list1 = helloWorldService.findByCondition(helloWorld2);
        String tempSTR = "";

        for (HelloWorld vo : list1) {
            log.info("반복문"+vo.getText());
            tempSTR += vo.getText() + "\n";
        }
        log.info( "결과값" + tempSTR);
        return tempSTR;
    }


    /*@GetMapping("/upload")
    public String uploadOptions(){
        log.info("upload options");
//        String temp = new String(new RedirectView("/abc/defg/hijklm.do"));
        String temp = "redirect:/upload1/options";
        return temp;
    }*/

    @GetMapping("/upload")
    public RedirectView uploadOptions(){
        log.info("upload options_ hello world resource");
        return new RedirectView("/upload/options");
    }



}
