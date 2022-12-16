package com.example.NestDigitalApp.controller;

import com.example.NestDigitalApp.dao.LeavesDao;
import com.example.NestDigitalApp.dao.EmpLeaveDao;
import com.example.NestDigitalApp.model.Employee;
import com.example.NestDigitalApp.model.EmpLeave;
import com.example.NestDigitalApp.model.Leaves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LeaveController {
    @Autowired
    private EmpLeaveDao ldao;
    @Autowired
    private LeavesDao l1dao;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/applyLeave", produces = "application/json", consumes = "application/json")
    public HashMap<String, String> ApplyLeave(@RequestBody EmpLeave lv){
        LocalDateTime now = LocalDateTime.now();
        lv.setApplyDate(dtf.format(now));
        ldao.save(lv);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status","success");
        return hashMap;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAllLeaves")
    public List<Map<String, String>> GetAllLeaves(){
        return ldao.GetAllLeaves();
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/pendingleaves")
    public List<Map<String,String>> getpendingleaves(){
        return (List<Map<String,String>>) ldao.GetPendingLeaves();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/getEmployeeLeaves", produces = "application/json", consumes = "application/json")
    public List<EmpLeave> GetEmployeeLeaves(@RequestBody Employee emp){
        return ldao.GetEmployeeLeaves(Integer.valueOf(emp.getId()));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/updateLeaves", produces = "application/json", consumes = "application/json")
    public HashMap<String, String> UpdateLeaves(@RequestBody EmpLeave lv){
        ldao.UpdateLeaves(lv.getId(), lv.getLeaveStatus());
        String leaveType = lv.getLeaveType();
        System.out.println(lv.getFromDate());
        System.out.println(lv.getToDate());
        LocalDate dateBefore = LocalDate.parse(lv.getFromDate());
        LocalDate dateAfter = LocalDate.parse(lv.getToDate());
        int daysOfLeave = (int) ChronoUnit.DAYS.between(dateBefore, dateAfter)+1;
        List<com.example.NestDigitalApp.model.Leaves> l1 = (List<com.example.NestDigitalApp.model.Leaves>) l1dao.GetLeaveDetails(lv.getEmpId());
        System.out.println(l1.get(0).getCasualLeave()-daysOfLeave);
        if(leaveType.equals("casualLeave")){
            l1dao.UpdateLeave(lv.getEmpId(),(l1.get(0).getCasualLeave()-daysOfLeave),l1.get(0).getSickLeave(),l1.get(0).getSpecialLeave());
        } else if (leaveType.equals("sickLeave")) {
            l1dao.UpdateLeave(lv.getEmpId(),l1.get(0).getCasualLeave(),(l1.get(0).getSickLeave()-daysOfLeave),l1.get(0).getSpecialLeave());
        }else if (leaveType.equals("specialLeave")){
            l1.get(0).setSpecialLeave(l1.get(0).getSickLeave()-daysOfLeave);
            l1dao.UpdateLeave(lv.getEmpId(),l1.get(0).getCasualLeave(),l1.get(0).getSickLeave(),(l1.get(0).getSickLeave()-daysOfLeave));
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status","success");
        return hashMap;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/getEmployeeLeavesbalance", produces = "application/json", consumes = "application/json")
    public List<Leaves> GetLeaveDetails(@RequestBody Employee emp){
        return l1dao.GetLeaveDetails(Integer.valueOf(emp.getId()));
    }

}
