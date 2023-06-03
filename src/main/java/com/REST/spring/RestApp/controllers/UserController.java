package com.REST.spring.RestApp.controllers;

import com.REST.spring.RestApp.dto.UserDto;
import com.REST.spring.RestApp.models.User;
import com.REST.spring.RestApp.services.UserService;
import com.REST.spring.RestApp.util.UserErrorResponse;
import com.REST.spring.RestApp.util.UserNonCreatedException;
import com.REST.spring.RestApp.util.UserNotfoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController { // возвращть список объектов классов Person
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<UserDto> getUsers() { //вернем список из DTO
        return userService.findAll().stream().map(this::convertToUserDto).collect(Collectors.toList());
        //Jackson конверитрует эти объекты в JSON
    }

    @GetMapping("/{id}") //нашли Юзера по id и вернули UserDto методом convertToUserDto
    public UserDto getUser(@PathVariable("id") int id) {  // передаем id  в адресе
        return convertToUserDto(userService.findOne(id)); // Jackson автоматически сконвертирует в JASON
    }

    @PostMapping()                   //принимаем JSON используем реквестбади сконвертирует в юзер
    public ResponseEntity<HttpStatus> creat(@RequestBody @Valid UserDto user,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //если ошибка значит человек прислал невалидный JSON
            // отправим клиенту чтобы он понимал что совершил ошибку
            StringBuilder ErrorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                ErrorMessage.append(error.getField()).
                        append("-").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNonCreatedException(ErrorMessage.toString());

        }
        userService.save(convertToUser(user));
        //отравляем ответ с пустым телом и со статусом ок200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody UserDto user,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserNotfoundException();
        }

            user.setId(id);
        userService.save(convertToUser(user));


        return ResponseEntity.ok(HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
       User user1 = userService.findOne(id);

        userService.delete(user1);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private User convertToUser(UserDto userDto) {
//        ModelMapper modelMapper = new ModelMapper();//скопирует и поместит все поля которые совпадают у классов

        User user = modelMapper.map(userDto, User.class); // уменьшает код 1 параметр объект откуда копируем 2 параметра куда и что возвращаем
      user.setId(userDto.getId());
        // User user = new User();
        //        user.setFirstname(userDto.getFirstname());
//        user.setLastname(userDto.getLastname());
//        user.setAge(userDto.getAge());
//        user.setEmail(userDto.getEmail());


        return user;
    }

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);

    }


    @ExceptionHandler    // конкретно ловит определенно исключение
    private ResponseEntity<UserErrorResponse> handleException(UserNotfoundException e) {//ловим собсвтенное исключение
        UserErrorResponse response = new UserErrorResponse(
                "User this id wash not found",
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //404 not found
    }


    @ExceptionHandler    // конкретно ловит определенно исключение
    private ResponseEntity<UserErrorResponse> handleException(UserNonCreatedException e) {//ловим собсвтенное исключение
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //404 not found
    }

}
