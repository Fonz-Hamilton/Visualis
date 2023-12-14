package org.fonzhamilton.visualis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fonzhamilton.visualis.model.DataInfo;
import org.fonzhamilton.visualis.model.User;

@Getter
@Setter
@NoArgsConstructor
public class DataDTO {
    private Long id;
    private String name;
    private DataInfo dataInfo;
    private User user;

    public DataDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setDataInfo(DataInfo dataInfo) {
        this.dataInfo = dataInfo;
    }
}
