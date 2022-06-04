package com.application.gentlegourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoryController {

    @GetMapping("/story")
    public String loadStoryPage() {
        return "story/story";
    }

}
