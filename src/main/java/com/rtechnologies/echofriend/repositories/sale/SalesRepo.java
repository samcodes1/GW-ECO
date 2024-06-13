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

    @Query(value = "select s.*,sp.*, p.productimage, p.productprice, p.productname from sales s inner join saleproduct sp on sp.saleidfk=s.saleid INNER join products p on p.productid=sp.productidfk", nativeQuery = true)
    List<SalesProjection> findOrders();

    @Query(value = "select s.*,sp.*, p.productimage, p.productprice, p.productname from sales s inner join saleproduct sp on sp.saleidfk=s.saleid INNER join products p on p.productid=sp.productidfk where s.useridfk=?1", nativeQuery = true)
    List<SalesProjection> findOrdersByUserid(Long id);

    @Query(value = "select s.*,sp.*, p.productimage, p.productprice, p.productname from sales s inner join saleproduct sp on sp.saleidfk=s.saleid INNER join products p on p.productid=sp.productidfk where s.salestimestamp=?1", nativeQuery = true)
    List<SalesProjection> findOrdersByDateTime(Timestamp orderTimestamp);

    @Query(value = "select s.*,sp.*, p.productimage, p.productprice, p.productname from sales s inner join saleproduct sp on sp.saleidfk=s.saleid INNER join products p on p.productid=sp.productidfk where s.salestimestamp=?1 and s.useridfk=?2", nativeQuery = true)
    List<SalesProjection> findOrdersByDateTimeUserid(Timestamp orderTimestamp, Long userid);

    @Query(value = "select s.saleid, u.username, u.email, p.productprice, p.productname, s.salestimestamp, s.`state`  from sales s INNER join users u on u.userid=s.useridfk INNER join saleproduct sp on sp.saleidfk=s.saleid inner join products p on p.productid=sp.productidfk", nativeQuery = true)
    List<SalesProjection> findAllSales();

    @Query(value = "select s.saleid, u.username, u.email, p.productprice, p.productname, s.salestimestamp, s.`state`  from sales s INNER join users u on u.userid=s.useridfk INNER join saleproduct sp on sp.saleidfk=s.saleid inner join products p on p.productid=sp.productidfk where u.userid=?1", nativeQuery = true)
    List<SalesProjection> findAllSalesbyUserid(Long userid);

    @Query(value = "select *  from sales where useridfk=?1", nativeQuery = true)
    List<SalesProjection> findAllSalesInvoice(Long userid);

    @Query(value = "select s.*,sp.*,p.productname,p.productimage from saleproduct sp inner join sales s on s.saleid=sp.saleidfk inner join products p on p.productid=sp.productidfk where s.saleid=?1", nativeQuery = true)
    List<SalesProjection> findAllIvoiceProducts(Long invoiceid);
    
}
