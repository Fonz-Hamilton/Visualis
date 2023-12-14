package org.fonzhamilton.visualis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;
/**
 * Role Model
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    public Role(String name) {
        this.name = name;
    }
}
