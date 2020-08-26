package com.august.mypetclinic.controller;

import com.august.mypetclinic.model.Owner;
import com.august.mypetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnersControllerTest {

  @Mock
  private OwnerService ownerService;

  @InjectMocks
  private OwnersController ownersController;

  MockMvc mockMvc;

  Set<Owner> owners = new HashSet<>();

  @BeforeEach
  void setUp() {
    owners.add(Owner.builder().id(1L).build());
    owners.add(Owner.builder().id(2L).build());

    mockMvc = MockMvcBuilders.standaloneSetup(ownersController).build();
  }

  @Test
  void index() throws Exception {

    when(ownerService.findAll()).thenReturn(owners);

    mockMvc.perform(MockMvcRequestBuilders.get("/owners/index"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/index"))
        .andExpect(model().attribute("owners", hasSize(2)));
  }

  @Test
  void findOwner() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
        .andExpect(status().isOk())
        .andExpect(view().name("notImplemented"));

    verifyNoInteractions(ownerService);
  }

  @Test
  void showOwner() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

    mockMvc.perform(MockMvcRequestBuilders.get("/owners/123"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/ownerDetails"))
        .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
  }
}