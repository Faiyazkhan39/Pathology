package com.pathology.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/doctors")
    public String showDoctors() {
        return "doctors";
    }

    @GetMapping("/index")
    public String showIndex() {
        return "index";
    }
    @GetMapping("/about")
    public String showAbout() {
        return "about";
    }
    @GetMapping("/gallery")
    public String showGallery() {
        return "gallery";
    }
    @GetMapping("/blog")
    public String showBlog() {
        return "blog";
    }
    @GetMapping("/privacy")
    public String showPrivacy() {
        return "privacy";
    }
    @GetMapping("/terms")
    public String showTerms() {
        return "terms";
    }

}
