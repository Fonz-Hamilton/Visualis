package org.fonzhamilton.visualis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.fonzhamilton.visualis.model.Data;

import java.util.List;


@Repository
public interface DataRepository extends JpaRepository<Data, Long>{
    public Data findDataByName(String name);
    public List<Data> findDataById(Long id);

    @Query("SELECT y FROM Data y WHERE y.user.id = :userId")
    public List<Data> getAllDataByUserId(@Param("userId") Long userId);
}
