package com.example.hospital.patient.wx.api.db.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Mapper
public interface DoctorWorkPlanScheduleDao {
    public ArrayList<HashMap> searchDoctorWorkPlanSchedule(Map param);

    public ArrayList<HashMap> searchEligibleSchedule(Map param);

    public int updateNumById(Map param);

    public int releaseNumByOutTradeNo(String outTradeNo);
}




