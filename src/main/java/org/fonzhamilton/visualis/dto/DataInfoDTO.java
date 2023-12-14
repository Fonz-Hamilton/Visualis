package org.fonzhamilton.visualis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataInfoDTO {

    private String name;
    private String sourceLink;
    private String dataType;
    private String description;

    public DataInfoDTO(String name, String sourceLink, String dataType, String description) {
        this.name = name;
        this.sourceLink = sourceLink;
        this.dataType = dataType;
        this.description = description;
    }
}
