package com.example.NestDigitalApp.dao;

import com.example.NestDigitalApp.model.EmpLeave;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface EmpLeaveDao extends CrudRepository<EmpLeave, Integer> {

    @Query(value = "SELECT l.`id`, l.`apply_date`, e.emp_code, e.id AS empId, e.name, l.`from_date`, l.`leave_status`, l.`remarks`, l.`to_date`, l.`leave_type` FROM `empleave` AS l JOIN `employee` AS e ON e.id = l.emp_id", nativeQuery = true)
    List<Map<String, String>> GetAllLeaves();

    @Query(value = "SELECT `id`, `apply_date`, `emp_id`, `from_date`, `leave_status`, `leave_type`, `remarks`, `to_date` FROM `empleave` WHERE `emp_id`= :id", nativeQuery = true)
    List<EmpLeave> GetEmployeeLeaves(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `empleave` SET `leave_status`= :status WHERE `id`= :id", nativeQuery = true)
    void UpdateLeaves(@Param("id") int id, @Param("status") int status);

    @Query(value = "SELECT el.`id`, e.`name`,e.`empcode` , el.`apply_date`, el.`emp_id`, el.`from_date`, el.`leave_status`, el.`leave_type`, el.`remarks`, el.`to_date` FROM `empleave` el JOIN `employee` e  ON e.id = el.emp_id WHERE `leave_status` = '0'", nativeQuery = true)
    List<Map<String,String>> GetPendingLeaves();


}
