package com.kuliah.httprequest.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuliah.httprequest.modelDTO.PersonDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/test")
@Slf4j

public class CobaRestController {
    @RequestMapping(value = "/person/save", method = RequestMethod.POST)
    public ResponseEntity<PersonDTO> inputPerson(@RequestBody PersonDTO personDTO) {
        String nama = personDTO.getFname() + " " + personDTO.getLname();
        String msg = "input masuk untuk " + nama;
        log.info(msg);
        log.info(String.valueOf(personDTO));
        personDTO.setMsg(msg);
        return ResponseEntity.ok(personDTO);
    }
    @RequestMapping(value = "/person/save-from-map", method = RequestMethod.POST)
    public String inputPersonByMap(@RequestBody Map<String, Object> body) throws JsonProcessingException {
        String fname = "";
        String lname = "";
        String password = "";
        String username = "";

        if (body.get("fname") != null) {
            fname = body.get("fname").toString();
        }
        if (body.get("lname") != null) {
            lname = body.get("lname").toString();
        }
        if (body.get("username") != null) {
            username = body.get("username").toString();
        }
        if (body.get("password") != null) {
            password = body.get("password").toString();
        }

        String nama = fname + " " + lname;
        String msg = "input masuk untuk " + nama;
        log.info(msg);
        log.info(String.valueOf(body));
        PersonDTO personDTO = new PersonDTO(fname, lname, username, null, password, msg);
        return new ObjectMapper().writeValueAsString(personDTO);
    }
}











