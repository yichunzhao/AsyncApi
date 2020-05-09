package com.ynz.asyncapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Phone {
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 8, unique = true)
    private String number;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Person person;

    public Phone(String number) {
        setNumber(number);
    }


}
