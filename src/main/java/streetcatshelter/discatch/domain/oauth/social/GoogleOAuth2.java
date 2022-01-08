package streetcatshelter.discatch.domain.oauth.social;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@Component
public class GoogleOAuth2 {

    @Value("${google.clientId}")
    private String googleClientId;
    @Value("${google.secret}")
    private String googleSecret;


    public GoogleUserInfo getUserInfo(String code){

        String accessToken = getAccessToken(code);
        return getUserInfoByToken(accessToken);
    }

    private GoogleUserInfo getUserInfoByToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        RestTemplate rt = new RestTemplate();

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("access_token", accessToken);

        HttpEntity<MultiValueMap<String, String>> googleProfileRequest = new HttpEntity<>(params,headers);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://www.googleapis.com/userinfo/v2/me",
                HttpMethod.GET,
                googleProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        GoogleUserInfo googleUserInfo = new GoogleUserInfo();

        try {
            googleUserInfo = objectMapper.readValue(response.getBody(), GoogleUserInfo.class);
            return googleUserInfo;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getAccessToken(String code){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String ,String > params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleSecret);
//        params.add("redirect_uri", "http://localhost:3000/user/google/callback");
        params.add("redirect_uri", "https://discatch.site/user/google/callback");
        params.add("grant_type","authorization_code");

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String ,String >> googleTokenRequest =
                new HttpEntity<>(params,headers);

        ResponseEntity<String > response = rt.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                googleTokenRequest,
                String.class
        );

        String tokenJson = response.getBody();
        JSONObject rjson = new JSONObject(tokenJson);
        String accessToken = rjson.getString("access_token");

        return accessToken;
    }





}
