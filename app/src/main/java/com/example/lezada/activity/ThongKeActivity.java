package com.example.lezada.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.lezada.R;
import com.example.lezada.retrofit.ApiBanHang;
import com.example.lezada.retrofit.RetrofitClient;
import com.example.lezada.utils.Utils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongKeActivity extends AppCompatActivity {
    Toolbar toolbar;
    PieChart pieChart;
    //kết nối server lấy dữ liệu
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        ActionToolBar();
        getDataPieChart();
    }
    private void getDataPieChart(){
        List<PieEntry> data_pie = new ArrayList<>();
        compositeDisposable.add(apiBanHang.getThongKe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                   thongKeModel -> {
                        if (thongKeModel.isSuccess()){
                            for (int i = 0; i < thongKeModel.getResult().size(); i++){
                                String tensp = thongKeModel.getResult().get(i).getTensp();
                                int tong = thongKeModel.getResult().get(i).getTong();
                                data_pie.add(new PieEntry(tong, tensp));
                            }
                            PieDataSet pieDataSet = new PieDataSet(data_pie, "Thống Kê");
                            PieData data = new PieData();
                            data.setDataSet(pieDataSet);
                            //cỡ chữ
                            data.setValueTextSize(12f);
                            //set kiểu %
                            data.setValueFormatter(new PercentFormatter(pieChart));
                            //màu
                            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                            pieChart.setData(data);
                            //hiệu ứng xoay xoay 1.5s
                            pieChart.animateXY(1500,1500);
                            pieChart.setUsePercentValues(true);
                            pieChart.getDescription().setEnabled(false);
                            pieChart.invalidate();
                        }
                   },
                   throwable -> {
                       Log.d("logggg", throwable.getMessage());
                   }
                ));
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        pieChart = findViewById(R.id.piechart);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}