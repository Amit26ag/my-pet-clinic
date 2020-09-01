package com.august.mypetclinic.controller;

import com.august.mypetclinic.model.Pet;
import com.august.mypetclinic.model.Visit;
import com.august.mypetclinic.services.PetService;
import com.august.mypetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VisitsController {

  private final VisitService visitService;

  private final PetService petService;

  public VisitsController(VisitService visitService, PetService petService) {
    this.visitService = visitService;
    this.petService = petService;
  }

  @InitBinder
  public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  /**
   * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
   * we always have fresh data - Since we do not use the session scope, make sure that
   * Pet object always has an id (Even though id is not part of the form fields)
   * @param petId
   * @return Pet
   */
  @ModelAttribute("visit")
  public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Model model) {
    Pet pet = this.petService.findById(petId);
    Visit visit = Visit.builder().pet(pet).build();
    pet.addVisit(visit);
    model.addAttribute("pet", pet);
    return visit;
  }

  // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
  @GetMapping("/owners/*/pets/{petId}/visits/new")
  public String initNewVisitForm(@PathVariable("petId") Long petId, Model model) {
    return "pets/createOrUpdateVisitForm";
  }

  // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
  @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
  public String processNewVisitForm(Visit visit, BindingResult result) {
    if (result.hasErrors()) {
      return "pets/createOrUpdateVisitForm";
    }
    else {
      this.visitService.save(visit);
      return "redirect:/owners/{ownerId}";
    }
  }

}
