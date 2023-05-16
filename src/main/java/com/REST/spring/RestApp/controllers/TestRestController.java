package com.REST.spring.RestApp.controllers;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController        // все методы в контроллере имеют анотацию @ResponseBody
@RequestMapping("/api")
public class TestRestController {

//@ResponseBody //в этом методе мы не возрващаем название для представления мы возвращаем данные
@GetMapping("/sayHello")
public String sayHello(){
return "HelloWorld"; // спринг не ищет шаблон он возвращает строку

}

}
