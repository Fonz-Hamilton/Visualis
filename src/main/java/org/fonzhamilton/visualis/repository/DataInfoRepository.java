package org.fonzhamilton.visualis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.fonzhamilton.visualis.model.DataInfo;


@Repository
public interface DataInfoRepository extends JpaRepository<DataInfo, Long> {
    public DataInfo findDataInfoByName(String name);
    public boolean existsByName(String name);

}
