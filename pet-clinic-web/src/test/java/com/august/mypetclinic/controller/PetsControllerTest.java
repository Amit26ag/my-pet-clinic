package com.august.mypetclinic.controller;

import com.august.mypetclinic.model.Owner;
import com.august.mypetclinic.model.Pet;
import com.august.mypetclinic.model.PetType;
import com.august.mypetclinic.services.OwnerService;
import com.august.mypetclinic.services.PetService;
import com.august.mypetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetsControllerTest {

  @Mock
  PetService petService;

  @Mock
  OwnerService ownerService;

  @Mock
  PetTypeService petTypeService;

  @InjectMocks
  PetsController petsController;

  MockMvc mockMvc;

  Owner owner;
  Set<PetType> petTypes;

  @BeforeEach
  void setUp() {
    owner = Owner.builder().id(1L).build();

    petTypes = new HashSet<>();
    petTypes.add(PetType.builder().id(1L).name("Cat").build());
    petTypes.add(PetType.builder().id(2L).name("Dog").build());

    mockMvc = MockMvcBuilders.standaloneSetup(petsController).build();
  }

  @Test
  void initCreationForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/pets/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("pets/createOrUpdatePetForm"))
        .andExpect(model().attributeExists("pet"))
        .andExpect(model().attributeExists("owner"));
  }

  @Test
  void processCreationForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/pets/new"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/owners/1"))
        .andExpect(model().attributeExists("pet"))
        .andExpect(model().attributeExists("owner"));
  }

  @Test
  void initUpdateForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);
    when(petService.findById(anyLong())).thenReturn(Pet.builder().id(1L).build());

    mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/pets/1/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("pets/createOrUpdatePetForm"))
        .andExpect(model().attributeExists("pet"))
        .andExpect(model().attributeExists("owner"));
  }

  @Test
  void processUpdateForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/pets/1/edit"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/owners/1"))
        .andExpect(model().attributeExists("pet"))
        .andExpect(model().attributeExists("owner"));
  }
}