package org.fonzhamilton.visualis.model;
import lombok.*;
import jakarta.persistence.*;

/**
 * Data model
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data_info_id")
    private DataInfo dataInfo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
