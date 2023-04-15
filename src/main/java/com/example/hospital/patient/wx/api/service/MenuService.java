package com.example.hospital.patient.wx.api.service;

import com.example.hospital.patient.wx.api.CustomMenuEntity.Menu;
import net.sf.json.JSONObject;

public interface MenuService {
    JSONObject getMenu(String accessToken);
    int createMenu(Menu menu, String accessToken);
    int deleteMenu(String accessToken);
}
