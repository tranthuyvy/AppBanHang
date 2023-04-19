package com.example.lezada.retrofit;

import com.example.lezada.model.DonHangModel;
import com.example.lezada.model.LoaiSpModel;
import com.example.lezada.model.MessageModel;
import com.example.lezada.model.SanPhamMoiModel;
import com.example.lezada.model.ThongKeModel;
import com.example.lezada.model.User;
import com.example.lezada.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiBanHang {
    //GET DATA
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();

    @GET("thongke.php")
    Observable<ThongKeModel> getThongKe();

    @GET("thongtincanhan.php")
    Call<User> getUserInfo(@Query("id") int id);

    //POST DATA
    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
      @Field("page") int page,
      @Field("loai") int loai
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
      @Field("email") String email,
      @Field("pass") String pass,
      @Field("username") String username,
      @Field("mobile") String mobile,
      @Field("uid") String uid
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
      @Field("email") String email,
      @Field("pass") String pass
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<MessageModel> createOder(
      @Field("email") String email,
      @Field("sdt") String sdt,
      @Field("tongtien") String tongtien,
      @Field("iduser") int id,
      @Field("diachi") String diachi,
      @Field("soluong") int soluong,
      @Field("chitiet") String chitiet
    );

    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> search(
            @Field("search") String search
    );

    @POST("xoa.php")
    @FormUrlEncoded
    Observable<MessageModel> xoaSanPham(
            @Field("id") int id
    );

    @POST("insert.php")
    @FormUrlEncoded
    Observable<MessageModel> insertSp(
            @Field("tensp") String tensp,
            @Field("gia") String gia,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int id
    );

    @POST("update.php")
    @FormUrlEncoded
    Observable<MessageModel> updateSp(
            @Field("tensp") String tensp,
            @Field("gia") String gia,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int idloai,
            @Field("id") int id
    );

    @POST("updatetoken.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("id") int id,
            @Field("token") String token
    );

//    @POST("updatemomo.php")
//    @FormUrlEncoded
//    Observable<MessageModel> updateMomo(
//            @Field("id") int id,
//            @Field("token") String token
//    );

    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<UserModel> gettoken(
            @Field("status") int status
    );

    @POST("updatedonhang.php")
    @FormUrlEncoded
    Observable<MessageModel> updateDonHang(
            @Field("id") int id,
            @Field("trangthai") int trangthai
    );

    @Multipart
    @POST("upload.php")
    Call<MessageModel> uploadFile(@Part MultipartBody.Part file);
}
