package com.example.lezada.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lezada.R;
import com.example.lezada.model.GioHang;
import com.example.lezada.model.NotificationSendData;
import com.example.lezada.retrofit.ApiBanHang;
import com.example.lezada.retrofit.ApiPushNotification;
import com.example.lezada.retrofit.RetrofitClient;
import com.example.lezada.retrofit.RetrofitClientNoti;
import com.example.lezada.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
//import vn.momo.momo_partner.AppMoMoLib;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien, txtsodienthoai, txtemail;
    EditText edtdiachi;
    AppCompatButton btndathang, btnmomo;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long tongtien;
    int totalItem;
    int iddonhang;

//    private String amount = "10000";
//    private String fee = "0";   //developer default
//    int environment = 0;//developer default
//    private String merchantName = "HoangNgoc";
//    private String merchantCode = "MOMOC2IC20220510";
//    private String merchantNameLabel = "Lezada";
//    private String description = "Mua Hàng Online";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
//        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION, môi trường phát triển
        initView();
        countItem();
        initControl();

    }

    private void countItem() {
        totalItem = 0;
        for (int i = 0; i < Utils.mangmuahang.size(); i++){
            totalItem += Utils.mangmuahang.get(i).getSoluong();
        }
    }

    //Get token through MoMo app
//    private void requestPayment(String iddonhang) {
//        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
//        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
//
//        Map<String, Object> eventValue = new HashMap<>();
//        //client Required
//        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
//        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
//        eventValue.put("amount", amount); //Kiểu integer
//        eventValue.put("orderId", iddonhang); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
//        eventValue.put("orderLabel", iddonhang); //gán nhãn
//
//        //client Optional - bill info
//        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
//        eventValue.put("fee", "0"); //Kiểu integer
//        eventValue.put("description", description); //mô tả đơn hàng - short description
//
//        //client extra data
//        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
//        eventValue.put("partnerCode", merchantCode);
//        //Example extra data
//        JSONObject objExtraData = new JSONObject();
//        try {
//            objExtraData.put("site_code", "008");
//            objExtraData.put("site_name", "CGV Cresent Mall");
//            objExtraData.put("screen_code", 0);
//            objExtraData.put("screen_name", "Special");
//            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
//            objExtraData.put("movie_format", "2D");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        eventValue.put("extraData", objExtraData.toString());
//        eventValue.put("extra", "");
//        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
//
//
//    }
//    //Get token callback from MoMo app an submit to server side
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
//            if(data != null) {
//                if(data.getIntExtra("status", -1) == 0) {
//                    //TOKEN IS AVAILABLE
//                    Log.d("Thành Công", data.getStringExtra("message"));
//                    String token = data.getStringExtra("data"); //Token response
//                    compositeDisposable.add(apiBanHang.updateMomo(iddonhang, token)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(
//                                    messageModel -> {
//                                        if (messageModel.isSuccess()){
//                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//                                    },
//                                    throwable -> {
//                                        Log.d("error", throwable.getMessage());
//                                    }
//                            ));
//
//                    String phoneNumber = data.getStringExtra("phonenumber");
//                    String env = data.getStringExtra("env");
//                    if(env == null){
//                        env = "app";
//                    }
//
//                    if(token != null && !token.equals("")) {
//                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
//                        // IF Momo topup success, continue to process your order
//                    } else {
//                        Log.d("thanhcong", "Không Thành Công");
//                    }
//                } else if(data.getIntExtra("status", -1) == 1) {
//                    //TOKEN FAIL
//                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
//                    Log.d("thanhcong", "Không Thành Công");
//                } else if(data.getIntExtra("status", -1) == 2) {
//                    //TOKEN FAIL
//                    Log.d("thanhcong", "Không Thành Công");
//                } else {
//                    //TOKEN FAIL
//                    Log.d("thanhcong", "Không Thành Công");
//                }
//            } else {
//                Log.d("thanhcong", "Không Thành Công");
//            }
//        } else {
//            Log.d("thanhcong", "Không Thành Công");
//        }
//    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(tongtien));
        txtemail.setText(Utils.user_current.getEmail());
        txtsodienthoai.setText(Utils.user_current.getMobile());

        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn Chưa Nhập Địa Chỉ Nhận Hàng", Toast.LENGTH_SHORT).show();

                }else {
                    //post data
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));    //Kiem tra
                    compositeDisposable.add(apiBanHang.createOder(str_email, str_sdt, String.valueOf(tongtien), id, str_diachi, totalItem, new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        pushNotiToUser();
                                        Toast.makeText(getApplicationContext(), "Thành Công", Toast.LENGTH_SHORT).show();

                                        //
                                        for (int i = 0; i < Utils.mangmuahang.size(); i++){
                                            GioHang gioHang = Utils.mangmuahang.get(i);
                                            if (Utils.manggiohang.contains(gioHang)){
                                                Utils.manggiohang.remove(gioHang);
                                            }
                                        }
                                        Utils.mangmuahang.clear();
                                        Paper.book().write("giohang", Utils.manggiohang);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                            ));

                }
            }
        });

        btnmomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn Chưa Nhập Địa Chỉ Nhận Hàng", Toast.LENGTH_SHORT).show();

                }else {
                    //post data
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));    //Kiem tra
                    compositeDisposable.add(apiBanHang.createOder(str_email, str_sdt, String.valueOf(tongtien), id, str_diachi, totalItem, new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        pushNotiToUser();
                                        Toast.makeText(getApplicationContext(), "Thành Công", Toast.LENGTH_SHORT).show();
                                        Utils.mangmuahang.clear();
                                        iddonhang = Integer.parseInt(messageModel.getIddonhang());
//                                        requestPayment(messageModel.getIddonhang());

                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                            ));

                }
            }
        });
    }

    private void pushNotiToUser() {
        //gettoken
        compositeDisposable.add(apiBanHang.gettoken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                   userModel -> {
                       if (userModel.isSuccess()){
                           //này là token admin
//                            String token = "doONN_wZSXedRwvn53Bx_-:APA91bE7CTJIzxnIAedIiyJZMVKJl6dgLSlJ75QqwOo6ifNhQ-OXDb8NIfzG4D2aOger7-Bg8i1Mxw6z-hXOmRZ0sK0Nf6cJftf__un8hL_SIrYpm4FO6LFfvBo1QQAuPutcef3pDArt";
                           String token = "d3vlqjAtTwmtigjU6SCdrh:APA91bHv3BS-D2rsJxR2Zyicdmkz4sq4EoZxtnaMOBzmiPU17X7PUsmgZmk5fnNxyiBQKhbtnUASaLwdvHneho90Gm7cK_deeHBFcOyMp9GUEwC6ge20Z4r4wgbIKXee1ZY-IwO0y51j";
                           for (int i = 0; i < userModel.getResult().size(); i++){
                               Map<String, String> data = new HashMap<>();
                               data.put("title", "Thông Báo");
                               data.put("body", "Bạn Đã Đặt Hàng Thành Công");
                               NotificationSendData notificationSendData = new NotificationSendData(userModel.getResult().get(i).getToken(), data);
                               ApiPushNotification apiPushNotification = RetrofitClientNoti.getInstance().create(ApiPushNotification.class);
                               compositeDisposable.add(apiPushNotification.sendNotification(notificationSendData)
                                       .subscribeOn(Schedulers.io())
                                       .observeOn(AndroidSchedulers.mainThread())
                                       .subscribe(
                                               notificationResponse -> {

                                               },
                                               throwable -> {
                                                   Log.d("logg", throwable.getMessage());
                                               }
                                       ));
                           }
                       }

                   }, throwable -> {
                       Log.d("loggg", throwable.getMessage());

                        }
                ));
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toobar);
        txttongtien = findViewById(R.id.txttongtien);
        txtsodienthoai = findViewById(R.id.txtsodienthoai);
        txtemail = findViewById(R.id.txtemail);
        edtdiachi = findViewById(R.id.edtdiachi);
        btndathang = findViewById(R.id.btndathang);
        btnmomo = findViewById(R.id.btnmomo);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}