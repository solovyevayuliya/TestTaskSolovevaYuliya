package com.game.controller;

import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/players")
public class PlayerController {

    private final PlayerService peopleService;

    @Autowired
    public PeopleController(PlayerService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("player", peopleService.findAll());
        return "people/index";
    }
}
