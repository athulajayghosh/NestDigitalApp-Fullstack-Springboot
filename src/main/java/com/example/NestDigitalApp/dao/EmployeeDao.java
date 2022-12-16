package com.example.NestDigitalApp.dao;

import com.example.NestDigitalApp.model.Employee;
import com.example.NestDigitalApp.model.Security;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeDao extends CrudRepository<Employee, Integer> {
    @Query(value = "SELECT `id`, `designation`, `email`, `empcode`, `name`, `password`, `phone`, `username` FROM `employee` WHERE `username`= :username AND `password`= :password", nativeQuery = true)
    List<Employee> UserLoginDetails(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT `id`, `designation`, `email`, `empcode`, `name`, `password`, `phone`, `username` FROM `employee` WHERE `username`= :username AND `password`= :password AND `empcode`= :empCode", nativeQuery = true)
    List<Employee> UserLoginDetailsByCred(@Param("username") String username, @Param("password") String password,@Param("empCode") String empCode);

    @Query(value = "SELECT `id`, `designation`, `email`, `empcode`, `name`, `password`, `phone`, `username` FROM `employee` WHERE `empcode`= :empCode", nativeQuery = true)
    List<Employee> UserLoginDetailsById(@Param("empCode") String empCode);

    @Query(value = "SELECT `id`, `designation`, `email`, `empcode`, `name`, `password`, `phone`, `username` FROM `employee` WHERE `empcode`= :empcode", nativeQuery = true)
    List<Employee> SearchEmployee(@Param("empcode") String empcode);

    @Query(value = "SELECT `id`, `designation`, `email`, `empcode`, `name`, `password`, `phone`, `username` FROM `employee` WHERE `id`= :id", nativeQuery = true)
    List<Employee> GetEmployeeProfile(@Param("id") int id);


    @Modifying
    @Transactional
    @Query(value = "UPDATE `employee` SET `designation`= :designation,`email`= :email,`name`= :name,`password`= :password,`phone`= :phone,`username`= :username WHERE `id`= :id", nativeQuery = true)
    void EditEmployee(@Param("id") int id, @Param("designation") String designation,@Param("email") String email,@Param("name") String name,@Param("password") String password,@Param("phone") String phone,@Param("username") String username);

}
