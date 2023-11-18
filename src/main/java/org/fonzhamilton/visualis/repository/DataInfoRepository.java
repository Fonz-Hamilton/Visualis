package org.fonzhamilton.visualis.repository;

import org.fonzhamilton.visualis.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.fonzhamilton.visualis.model.DataInfo;

import java.util.List;


@Repository
public interface DataInfoRepository extends JpaRepository<DataInfo, Long> {
    public DataInfo findDataInfoByName(String name);
    public boolean existsByName(String name);

}
