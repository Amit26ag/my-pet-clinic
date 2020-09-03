package com.august.mypetclinic.controller;

import com.august.mypetclinic.model.Vet;
import com.august.mypetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class VetsController {

    private final VetService vetService;

    public VetsController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"/vets", "/vets.html", "/vets/index", "/vets/list"})
    public String index(Model model) {
        model.addAttribute("vets", vetService.findAll());
        return "vets/index";
    }

    @GetMapping("/api/vets")
    @ResponseBody
    public Set<Vet> getVets() {
        return vetService.findAll();
    }

}
