package com.example.lezada.retrofit;

import com.example.lezada.model.NotificationResponse;
import com.example.lezada.model.NotificationSendData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNotification {
    @Headers(
            {
                    "Content-Type: application/json", "Authorization: key=AAAANe4frJw:APA91bGn6mH6-jQ4DUmE0s2mlRB86ZKMNnNXyDdDjwwf9aFqW1eaJbXKGlH11-H12h2Ba0yIRu_wJ2C0xALJnnPzGrWfLSv1x7u5zvKDmNNglun-Mh1q7abLQaIqZNN2jKsU-HFzprNZ"
            }
    )
    @POST("fcm/send")
    Observable<NotificationResponse> sendNotification(@Body NotificationSendData data);
}
