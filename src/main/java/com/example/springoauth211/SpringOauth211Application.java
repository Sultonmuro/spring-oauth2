package com.example.springoauth211;

import com.example.springoauth211.entity.User;
import com.example.springoauth211.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@SpringBootApplication
@Controller
public class SpringOauth211Application extends WebSecurityConfigurerAdapter {
    final
    UserRepository userRepository;

    public SpringOauth211Application(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        User RegisterUser = new User();
        RegisterUser.setBio(principal.getAttribute("bio"));
        RegisterUser.setEmail(principal.getAttribute("email"));
        RegisterUser.setAvatar_url(principal.getAttribute("avatar_url"));
        RegisterUser.setGiven_name(principal.getAttribute("given_name"));
        RegisterUser.setLogin(principal.getAttribute("login"));
        RegisterUser.setName(principal.getAttribute("name"));
        RegisterUser.setPicture(principal.getAttribute("picture"));
        RegisterUser.setType(principal.getAttribute("type"));
        RegisterUser.setUrl(principal.getAttribute("url"));
        userRepository.save(RegisterUser); // save db
        return "index";
    }

    @GetMapping("/lists")
    public String getCategoryList(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "users";

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .oauth2Login();
        // @formatter:on
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringOauth211Application.class, args);
        int i = new Random().nextInt(999999);
        System.out.println(i);

    }
}
