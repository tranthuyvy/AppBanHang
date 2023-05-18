package com.example.lezada.utils;

import com.example.lezada.model.GioHang;
import com.example.lezada.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    //ipconfig => lấy địa chỉ IPv4
//    public static final String BASE_URL="http://192.168.1.114:8080/lezada/";
    public static final String BASE_URL="http://192.168.1.38:8080/lezada/";
    public static List<GioHang> manggiohang;    //mang chua san pham khong check
    public static List<GioHang> mangmuahang = new ArrayList<>();    //mang chua san pham co check
    public static User user_current = new User();            //thông tin user
    //id của ng nhân
    public static String ID_RECEIVED;
    public static final String SENDID = "idsend";           //người gửi
    public static final String RECEIVEDID = "idreceived";   //người nhận
    public static final String MESS = "message";            //nội dung
    public static final String DATETIME = "datetime";       //ngày
    public static final String PATH_CHAT = "chat";

}
