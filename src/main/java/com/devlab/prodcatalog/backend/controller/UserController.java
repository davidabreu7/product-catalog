package com.devlab.prodcatalog.backend.controller;

import com.devlab.prodcatalog.backend.dto.UserDto;
import com.devlab.prodcatalog.backend.dto.UserInsertDto;
import com.devlab.prodcatalog.backend.dto.UserUpdateDto;
import com.devlab.prodcatalog.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(){
        return ResponseEntity.ok(userService.findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> insert(@Valid @RequestBody UserInsertDto userInsertDto){
        UserDto userDto = userService.insert(userInsertDto);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(userDto.getId()).
                toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateById(@PathVariable Long id, @Valid @RequestBody UserUpdateDto dto){
        return ResponseEntity.ok(userService.updateById(id, dto));
    }

}
