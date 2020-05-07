package com.ynz.asyncapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Phone {
    @Id
    @GeneratedValue
    private int id;

    private String number;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Person person;

    public Phone(String number){
        setNumber(number);
    }



}
