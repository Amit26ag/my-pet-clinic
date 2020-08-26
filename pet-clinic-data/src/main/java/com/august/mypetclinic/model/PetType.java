package com.august.mypetclinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PetType extends BaseEntity {

    private String name;

    @Override
    public String toString() {
        return name;
    }
}
