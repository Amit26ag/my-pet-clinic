package com.august.mypetclinic.controller;

import com.august.mypetclinic.model.Owner;
import com.august.mypetclinic.model.Pet;
import com.august.mypetclinic.model.PetType;
import com.august.mypetclinic.services.OwnerService;
import com.august.mypetclinic.services.PetService;
import com.august.mypetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetsController {

  private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

  private final OwnerService ownerService;
  private final PetService petService;
  private final PetTypeService petTypeService;


  public PetsController(OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
    this.ownerService = ownerService;
    this.petService = petService;
    this.petTypeService = petTypeService;
  }

  @ModelAttribute("types")
  public Collection<PetType> populatePetTypes() {
    return petTypeService.findAll();
  }

  @ModelAttribute("owner")
  public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
    return ownerService.findById(ownerId);
  }

  @InitBinder("owner")
  public void initOwnerBinder(WebDataBinder webDataBinder) {
    webDataBinder.setDisallowedFields("id");
  }

//  @InitBinder("pet")
//  public void initPetBinder(WebDataBinder dataBinder) {
//    dataBinder.setValidator(new PetValidator());
//  }

  @GetMapping("/pets/new")
  public String initCreationForm(Owner owner, Model model) {
    Pet pet = new Pet();
    pet.setOwner(owner);
    owner.addPet(pet);
    model.addAttribute("pet", pet);
    return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/pets/new")
  public String processCreationForm(Owner owner, Pet pet, BindingResult result, Model model) {
    if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
      result.rejectValue("name", "duplicate", "already exists");
    }
    owner.addPet(pet);
    if (result.hasErrors()) {
      model.addAttribute("pet", pet);
      return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }
    else {
      this.petService.save(pet);
      return "redirect:/owners/" + owner.getId();
    }
  }

  @GetMapping("/pets/{petId}/edit")
  public String initUpdateForm(@PathVariable("petId") Long petId, Owner owner, Model model) {
    Pet pet = this.petService.findById(petId);
    pet.setOwner(owner);
    model.addAttribute("pet", pet);
    return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/pets/{petId}/edit")
  public String processUpdateForm(Pet pet, BindingResult result, Owner owner, Model model) {
    if (result.hasErrors()) {
      pet.setOwner(owner);
      model.addAttribute("pet", pet);
      return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    } else {
      owner.addPet(pet);
      this.petService.save(pet);
      return "redirect:/owners/" + owner.getId();
    }
  }

}
