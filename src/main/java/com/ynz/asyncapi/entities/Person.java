package com.ynz.asyncapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * the best practice for modeling oneToMany relationship.
 *
 *     <ul>
 *         <li>modeling as a bilateral relationship.</li>
 *         <li>taking toMany side as the owning side.</li>
 *         <li>taking one aide as the inverse side.</li>
 *     </ul>
 *
 * </p>
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Phone> phones = new HashSet<>();

    public Person(String name) {
        setName(name);
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setPerson(this);
    }

}
