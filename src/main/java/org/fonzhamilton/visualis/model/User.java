package org.fonzhamilton.visualis.model;
import lombok.*;
import jakarta.persistence.*;
import java.util.Collection;

/**
 * User model
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection <Role> roles;

    public User(String userName, String firstName, String lastName, String email, String password ) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;

    }
}