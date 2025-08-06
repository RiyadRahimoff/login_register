package com.azecoders.community.controller;

import com.azecoders.community.model.enums.Profession;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/enums")
@CrossOrigin(origins = "http://localhost:3000")
public class EnumController {
    @GetMapping("/professions")
    public List<Profession> professions(){
        return Arrays.asList(Profession.values());
    }
}
