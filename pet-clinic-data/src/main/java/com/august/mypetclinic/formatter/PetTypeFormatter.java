package com.august.mypetclinic.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import com.august.mypetclinic.model.PetType;
import com.august.mypetclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

  private final PetTypeService petTypeService;

  @Autowired
  public PetTypeFormatter(PetTypeService pets) {
    this.petTypeService = pets;
  }

  @Override
  public String print(PetType petType, Locale locale) {
    return petType.getName();
  }

  @Override
  public PetType parse(String text, Locale locale) throws ParseException {
    Collection<PetType> findPetTypes = this.petTypeService.findAll();
    for (PetType type : findPetTypes) {
      if (type.getName().equals(text)) {
        return type;
      }
    }
    throw new ParseException("type not found: " + text, 0);
  }

}