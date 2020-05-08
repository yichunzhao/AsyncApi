package com.ynz.asyncapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * The best practice for modeling oneToMany relationship.
 *
 *     <ul>
 *         <li>modeling it as a bilateral relationship.</li>
 *         <li>taking the toMany side as the owning side.</li>
 *         <li>taking the one aide as the inverse side.</li>
 *     </ul>
 *
 * It reduces the extra junk table.
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

    @Column(nullable = false)
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
