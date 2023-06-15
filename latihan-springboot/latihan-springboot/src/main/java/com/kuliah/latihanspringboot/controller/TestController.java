package com.kuliah.latihanspringboot.controller;

import com.kuliah.latihanspringboot.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
////@RequestMapping(value = "/api")
@Slf4j
public class TestController {

    private String url = "http://localhost:8080"; // Ganti dengan URL yang sesuai
    String finalUrl = url + "/person/save";

    @GetMapping(value = "/login")
    public String openLoginPage(Model model, HttpServletResponse httpServletResponse, HttpServletRequest request) {
        log.info("Selamat Anda Berhasil masuk ke dalam halaman Login!");
        model.addAttribute("loginInput", new AppUser());
        return "login";
    }

    @GetMapping(value = "/register")
    public String openRegisterPage(Model model, HttpServletResponse httpServletResponse, HttpServletRequest request) {
        log.info("Selamat Anda Berhasil masuk ke dalam halaman register!");
        return "register";
    }

    @GetMapping(value = "/dashboard")
    public String openHomePage(Model model, HttpServletResponse httpServletResponse, HttpServletRequest request) {
        log.info("Selamat Anda Berhasil masuk ke dalam halaman home!");
        return "dashboard";
    }

    @PostMapping(value = "/login")
    public String submitLogin(@ModelAttribute("loginInput") AppUser appUser, Model model, HttpServletResponse httpServletResponse, HttpServletRequest request) {
        log.info("========================================");
        log.info("LOGIN ATTEMPT");
        log.info("========================================");
        log.info("username: {}", appUser.getUsername());
        log.info("password: {}", appUser.getPassword());

        if (appUser.getUsername().equals("Arum")) {
            log.info("user with Username {} exists", appUser.getUsername());

            if (!appUser.getPassword().equals("password")) {
                log.info("wrong password", appUser.getUsername());
                System.out.println("\n");
                log.info("========================================");
                log.info("LOGIN FAILED");
                log.info("========================================");
                return "redirect:/login";
            } else {
                System.out.println("\n");
                log.info("========================================");
                log.info("LOGIN SUCCESS");
                log.info("========================================");

                // Create HttpHeaders
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("client_id", "rzyamel8080");
                headers.set("api-key", "3f162d85-0bdi-44a4-aaff-5f91903a334");

                // Set request body
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("fname", appUser.getFname());
                requestBody.put("lname", appUser.getLname());
                requestBody.put("username", appUser.getUsername());
                requestBody.put("password", appUser.getPassword());
                requestBody.put("msg", "halo");
                boolean isAutomatic = false;

                // Create HttpEntity with headers and body
                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

                // Create RestTemplate
                RestTemplate restTemplate = new RestTemplate();
                String finalUrl = url + "/person/save";
                if (!isAutomatic) {
                    finalUrl = url + "/person/save-from-map";
                }
                ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.POST, entity, String.class);
                log.info("");
                log.info("response from api");
                log.info("=======================");
                log.info("{}", response.getBody());
                log.info("=======================");
                return "redirect:/dashboard";
            }
        } else {
            log.info("user with username {} does not exist", appUser.getUsername());
            System.out.println("\n");
            log.info("========================================");
            log.info("LOGIN FAILED");
            log.info("========================================");
            return "redirect:/register";
        }
    }
}


