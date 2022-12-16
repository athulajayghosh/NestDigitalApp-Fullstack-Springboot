package com.example.NestDigitalApp.controller;

import com.example.NestDigitalApp.dao.EmployeeDao;
import com.example.NestDigitalApp.dao.LeavesDao;
import com.example.NestDigitalApp.dao.SecurityDao;
import com.example.NestDigitalApp.model.Employee;
import com.example.NestDigitalApp.model.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private EmployeeDao empdao;
    @Autowired
    private LeavesDao ldao;
    @Autowired
    private SecurityDao sdao;

    DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy");
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/addEmployee", consumes = "application/json", produces = "application/json")
    public HashMap<String, String> AddEmployee(@RequestBody Employee emp){
        List<Employee> emp1 = (List<Employee>) empdao.UserLoginDetailsByCred(emp.getUsername(), emp.getPassword(), emp.getEmpcode());
        HashMap<String, String> hashMap = new HashMap<>();
        if(emp1.size()==0){
            LocalDateTime now = LocalDateTime.now();
            empdao.save(emp);
            List<Employee> result = (List<Employee>) empdao.UserLoginDetailsById(emp.getEmpcode());
            com.example.NestDigitalApp.model.Leaves l = new com.example.NestDigitalApp.model.Leaves();
            l.setEmpId(String.valueOf(result.get(0).getId()));
            l.setYear(dt.format(now));
            l.setCasualLeave(20);
            l.setSickLeave(7);
            l.setSpecialLeave(3);
            ldao.save(l);
            hashMap.put("status","success");
        }else{
            hashMap.put("status","failed");
        }
        return hashMap;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/searchEmployee", consumes = "application/json", produces = "application/json")
    public List<Employee> SearchEmployee(@RequestBody Employee emp){
        return  (List<Employee>) empdao.SearchEmployee(emp.getEmpcode());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/viewAllEmployee")
    public List<Employee> GetAllEmployee(){
        return (List<Employee>) empdao.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/addSecurity", consumes = "application/json", produces = "application/json")
    public HashMap<String, String> AddSecurity(@RequestBody Security sc){
        sdao.save(sc);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status","success");
        return hashMap;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/viewAllSecurity")
    public List<Security> GetAllSec(){
        return (List<Security>) sdao.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/editEmployee", consumes = "application/json", produces = "application/json")
    public HashMap<String, String> EditEmployee(@RequestBody Employee emp){
        System.out.println(emp.getId());
        empdao.EditEmployee(emp.getId(), emp.getDesignation(), emp.getEmail(), emp.getName(), emp.getPassword(), emp.getPhone(), emp.getUsername());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status","success");
        return hashMap;
    }
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/getempcode", consumes = "application/json", produces = "application/json")
    public List<Employee> GetEmployeeId(@RequestBody Employee emp){
        return  (List<Employee>) empdao.SearchEmployee(emp.getEmpcode());
    }



}
