package com.example.NestDigitalApp.dao;

import com.example.NestDigitalApp.model.EmpLeave;
import com.example.NestDigitalApp.model.Leaves;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeavesDao extends CrudRepository<com.example.NestDigitalApp.model.Leaves, Integer>{
    @Query(value = "SELECT `id`, `casual_leave`, `emp_id`, `sick_leave`, `special_leave`, `year` FROM `leaves` WHERE `emp_id`= :id", nativeQuery = true)
    List<Leaves> GetLeaveDetails(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `leaves` SET `casual_leave`= :casualLeave,`sick_leave`= :sickLeave,`special_leave`= :specialLeave WHERE `emp_id`= :id", nativeQuery = true)
    void UpdateLeave(@Param("id") Integer id, @Param("casualLeave") Integer casualLeave, @Param("sickLeave") Integer sickLeave, @Param("specialLeave") Integer specialLeave);

    @Query(value = "SELECT `id`, `apply_date`, `emp_id`, `from_date`, `leave_status`, `leave_type`, `remarks`, `to_date` FROM `empleave` WHERE `emp_id`= :empId  AND :date BETWEEN `from_date` AND `to_date`", nativeQuery = true)
    List<EmpLeave> GetLeaveUpdates(@Param("empId") Integer empId, @Param("date") String date);


}
