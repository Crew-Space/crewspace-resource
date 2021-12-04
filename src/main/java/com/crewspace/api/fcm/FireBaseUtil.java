package com.crewspace.api.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FireBaseUtil {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/152795360259/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, Map<String, String> data, String title, String image) throws IOException {
        String message = makeMessage(targetToken, data, title, image);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
            .url(API_URL)
            .post(requestBody)
            .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
            .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
            .build();

        Response response = client.newCall(request)
            .execute();

        // 확인용
        log.info("message 전달 " + response.body().string());
    }

    private String makeMessage(String targetToken, Map<String, String> data, String title, String image) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
            .message(FcmMessage.Message.builder()
                .token(targetToken)
                .data(data)
                .notification(FcmMessage.Notification.builder()
                    .title(title)
                    .image(image)
                    .build()
                )
                .build()
            )
            .validate_only(false)
            .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "crew-space-admin-sdk.json";
        GoogleCredentials googleCredentials = GoogleCredentials
            .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
            .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
