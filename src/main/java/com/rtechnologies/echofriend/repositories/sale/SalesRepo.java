package com.rtechnologies.echofriend.repositories.sale;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.sales.SalesEntity;
import com.rtechnologies.echofriend.models.sale.response.SalesProjection;

@Repository
public interface SalesRepo extends CrudRepository<SalesEntity, Long> {

    @Query(value = "select * from sales s inner join saleproduct sp on sp.saleidfk=s.saleid", nativeQuery = true)
    List<SalesProjection> findOrders();

    @Query(value = "select * from sales s inner join saleproduct sp on sp.saleidfk=s.saleid where s.useridfk=?1", nativeQuery = true)
    List<SalesProjection> findOrdersByUserid(Long id);

    @Query(value = "select * from sales s inner join saleproduct sp on sp.saleidfk=s.saleid where s.salestimestamp=?1", nativeQuery = true)
    List<SalesProjection> findOrdersByDateTime(Timestamp orderTimestamp);

    @Query(value = "select * from sales s inner join saleproduct sp on sp.saleidfk=s.saleid where s.salestimestamp=?1 and s.useridfk=?2", nativeQuery = true)
    List<SalesProjection> findOrdersByDateTimeUserid(Timestamp orderTimestamp, Long userid);
    
}
