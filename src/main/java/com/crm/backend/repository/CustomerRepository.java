package com.crm.backend.repository;


import com.crm.backend.dto.CustomerListDto;
import com.crm.backend.model.Customer;
import com.crm.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c.createdBy.name, COUNT(c) as totalSales " +
            "FROM Customer c " +
            "WHERE c.customerType = com.crm.backend.model.CustomerType.CONVERTED " +
            "GROUP BY c.createdBy.name " +
            "ORDER BY totalSales DESC")
    List<Object[]> findTopSalesRepsBySales();

    @Query("SELECT c.createdBy.name, " +
            "100 * COUNT(CASE WHEN c.customerType = com.crm.backend.model.CustomerType.CONVERTED THEN 1 END) / " +
            "NULLIF(COUNT(c), 0) AS conversionRate " +
            "FROM Customer c " +
            "GROUP BY c.createdBy.name " +
            "ORDER BY conversionRate DESC")
    List<Object[]> findTopSalesRepsByConversionRate();

    @Query("SELECT COUNT(c) FROM Customer c WHERE c.customerType = 'CONVERTED'")
    long getTotalSales();

    @Query("SELECT new com.crm.backend.dto.CustomerListDto(c.id, c.name, c.email, c.phoneNumber, c.customerType, c.createdBy) " +
            "FROM Customer c " +
            "WHERE c.createdBy.id = :salesRepId")
    List<CustomerListDto> findCustomersBySalesRepsId(@Param("salesRepId") Long salesRepId);


    Customer findCustomerById(Long id);

    void deleteByCreatedBy(User user);
}


