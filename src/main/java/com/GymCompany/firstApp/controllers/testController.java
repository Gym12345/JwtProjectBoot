package com.GymCompany.firstApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/test")
@Controller
public class testController {
    
    @GetMapping(value = "/main")
    public String mainPage(HttpServletRequest request) {
        return "ajaxTest";
    }
    
    @GetMapping("/ajax")
    public void handleAjaxRequest(@RequestParam(name = "param", required = false, defaultValue = "default") String param, Model model, HttpServletResponse response) throws IOException {
        model.addAttribute("param", param);
        String htmlResponse = renderHtml(model);
        response.setContentType("text/html");
        response.getWriter().write(htmlResponse);
    }
    
    private String renderHtml(Model model) {
        // For simplicity, we'll hardcode the HTML response here. In a real application, you'd probably use a templating engine.
        String param = (String) model.getAttribute("param");
        return "<div><h2>Result</h2><p>Parameter: " + param + "</p></div>";
    }
}
