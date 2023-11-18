package org.fonzhamilton.visualis.model;
import lombok.*;
import jakarta.persistence.*;
import java.util.Collection;

/**
 * DataInfo model
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class DataInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Other fields
    private String name;
    private String sourceLink;
    private String dataType;
    private String description;


}

