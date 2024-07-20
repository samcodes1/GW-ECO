package com.rtechnologies.echofriend.repositories.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.products.ProductsEntity;
import com.rtechnologies.echofriend.models.companies.response.CompanyProjection;
import com.rtechnologies.echofriend.models.products.response.ProductProjections;

@Repository
public interface ProductsRepo extends JpaRepository<ProductsEntity, Long> {
    @Query(value = "select * from products p inner join productcategory c on c.categoryid=p.categoryidfk left join producttype pt on pt.producttypeid=p.producttypeidfk", nativeQuery = true)
    List<ProductProjections> findProducts();

    @Query(value = "select * from products p inner join productcategory c on c.categoryid=p.categoryidfk left join producttype pt on pt.producttypeid=p.producttypeidfk where p.productid=?1", nativeQuery = true)
    List<ProductProjections> findProducts(Long productid);

    @Query(value = "select * from products p inner join productcategory c on c.categoryid=p.categoryidfk left join producttype pt on pt.producttypeid=p.producttypeidfk where p.categoryidfk=?1", nativeQuery = true)
    List<ProductProjections> findProductsByCategory(Long id);

    @Query(value = "SELECT COUNT(*) from products p inner JOIN companies c on c.companyid=p.companyidfk where c.companyid=?1",
    nativeQuery = true)
    Integer countCompanyProducts(Long companyId);

    @Query(value = "select count(*) from voucher v inner join companies c on v.shopidfk=c.companyid where c.companyid=?1",
    nativeQuery = true)
    Integer countCompanyVoucher(Long companyId);

    @Query(value = "select count(*) from saleproduct sp inner join sales s on s.saleid=sp.saleidfk inner join products p on p.productid=sp.productidfk inner JOIN companies c on c.companyid=p.companyidfk where c.companyid=?1 and Date(s.salestimestamp) = CURDATE()",
    nativeQuery = true)
    Integer countCompanyOrderToday(Long companyId);

    @Query(value = "select count(*) from saleproduct sp inner join sales s on s.saleid=sp.saleidfk inner join products p on p.productid=sp.productidfk inner JOIN companies c on c.companyid=p.companyidfk where c.companyid=?1",
    nativeQuery = true)
    Integer countCompanyTotalOrder(Long companyId);

    @Query(value = "select count(DISTINCT s.useridfk) from saleproduct sp inner join sales s on s.saleid=sp.saleidfk inner join products p on p.productid=sp.productidfk inner JOIN companies c on c.companyid=p.companyidfk where c.companyid=?1",
    nativeQuery = true)
    Integer countCompanyCustomer(Long companyId);

    @Query(value = "select u.username, c.companyname, p.productprice from saleproduct sp inner join sales s on s.saleid=sp.saleidfk inner join products p on p.productid=sp.productidfk inner JOIN companies c on c.companyid=p.companyidfk inner join users u on u.userid=s.useridfk where c.companyid=?1 order by s.saleid desc limit 5",
    nativeQuery = true)
    List<CompanyProjection> recentOrder(Long companyId);

    @Query(value = "select sp.saleproductid as transactionid, sp.total as amount, s.`state`, s.salestimestamp from saleproduct sp inner join sales s on s.saleid=sp.saleidfk inner join products p on p.productid=sp.productidfk inner JOIN companies c on c.companyid=p.companyidfk inner join users u on u.userid=s.useridfk where c.companyid=?1 order by s.salestimestamp desc",
    nativeQuery = true)
    List<CompanyProjection> orders(Long companyId);

    @Query(value = "select p.productname, c.companyname, pc.category, p.productdescription, p.productimage from products p INNER join companies c on c.companyid=p.companyidfk inner join productcategory pc on pc.categoryid=p.categoryidfk",
    nativeQuery = true)
    List<CompanyProjection> getCompanyProducts();

    @Query(value = "select p.productid, p.productname, p.productprice, c.companyname, pc.category, p.productdescription, p.productimage from products p INNER join companies c on c.companyid=p.companyidfk inner join productcategory pc on pc.categoryid=p.categoryidfk where c.companyid=?1",
    nativeQuery = true)
    List<CompanyProjection> getCompanyProductsbyCompanyId(Long companyid);


}
