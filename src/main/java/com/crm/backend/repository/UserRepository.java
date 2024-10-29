package com.crm.backend.repository;

import com.crm.backend.dto.SalesRepDetailsDto;
import com.crm.backend.dto.SalesRepListDto;
import com.crm.backend.model.Customer;
import com.crm.backend.model.Role;
import com.crm.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") Role role);

    @Query("SELECT new com.crm.backend.dto.SalesRepListDto(u.id, u.name, " +
            "(SELECT COUNT(c) FROM Customer c WHERE c.createdBy = u AND c.customerType = 'CONVERTED'), " +
            "(SELECT COUNT(c) FROM Customer c WHERE c.createdBy = u)) " +
            "FROM User u WHERE u.role = 'SALES_REP'")
    List<SalesRepListDto> findSalesRepList();


    @Query("SELECT new com.crm.backend.dto.SalesRepDetailsDto(u.id, u.name, u.email, "
            + "COALESCE(SUM(CASE WHEN c.customerType = 'CONVERTED' THEN 1 ELSE 0 END), 0), "
            + "COUNT(DISTINCT c.id)) "
            + "FROM User u "
            + "LEFT JOIN Customer c ON c.createdBy.id = u.id "
            + "WHERE u.role = 'SALES_REP' AND u.id = :id "
            + "GROUP BY u.id")
    SalesRepDetailsDto findSalesRepDetailsById(@Param("id") Long id);

    @Query("SELECT new com.crm.backend.dto.SalesRepDetailsDto(u.id, u.name, u.email, "
            + "COALESCE(SUM(CASE WHEN c.customerType = 'CONVERTED' THEN 1 ELSE 0 END), 0), "
            + "COUNT(DISTINCT c.id)) "
            + "FROM User u "
            + "LEFT JOIN Customer c ON c.createdBy.id = u.id "
            + "WHERE u.role = 'SALES_REP' AND u.email = :email "
            + "GROUP BY u.id")
    SalesRepDetailsDto findSalesRepDetailsByEmail(String email);



    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
