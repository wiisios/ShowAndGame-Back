package ShowAndGame.ShowAndGame.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TwitchService {
    @Value("${twitch.client-id}")
    private String clientId;

    @Value("${twitch.client-secret}")
    private String clientSecret;

    private static final String TWITCH_API_BASE_URL = "https://api.twitch.tv/helix/";

    private String accessToken;
    private long tokenExpiryTime = 0;

    public String getTwitchToken() {
        if (accessToken == null || System.currentTimeMillis() > tokenExpiryTime) {
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
            bodyParams.add("client_id", clientId);
            bodyParams.add("client_secret", clientSecret);
            bodyParams.add("grant_type", "client_credentials");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(bodyParams, headers);

            try {
                ResponseEntity<String> response = restTemplate.exchange(
                        "https://id.twitch.tv/oauth2/token",
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                );

                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);

                if (responseBody != null) {
                    accessToken = (String) responseBody.get("access_token");
                    int expiresIn = (int) responseBody.get("expires_in");
                    tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return accessToken;
    }

    public String getGameIdByName(String gameName) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(TWITCH_API_BASE_URL + "games")
                .queryParam("name", URLEncoder.encode(gameName, "UTF-8"))
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.set("Client-Id", clientId);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> body = response.getBody();

        if (body != null && body.containsKey("data")) {
            List<Map<String, Object>> data = (List<Map<String, Object>>) body.get("data");

            if (!data.isEmpty()) {
                return (String) data.get(0).get("id");
            }
        }
        return null;
    }

    public List<Map<String, Object>> getStreamsByGame(String gameId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(TWITCH_API_BASE_URL + "streams")
                .queryParam("game_id", gameId)
                .queryParam("first", 5)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.set("Client-Id", clientId);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> body = response.getBody();

        List<Map<String, Object>> streams = new ArrayList<>();

        // Verificar si la respuesta contiene data y mapearla a la estructura deseada
        if (body != null && body.containsKey("data")) {
            List<Map<String, Object>> data = (List<Map<String, Object>>) body.get("data");

            // Iterar sobre cada stream y crear un Map con los campos requeridos
            for (Map<String, Object> stream : data) {
                Map<String, Object> streamInfo = new HashMap<>();
                streamInfo.put("id", stream.get("id"));
                streamInfo.put("user_name", stream.get("user_name"));
                streamInfo.put("title", stream.get("title"));
                streamInfo.put("url", "https://www.twitch.tv/" + stream.get("user_name"));
                streams.add(streamInfo);
            }
        }

        return streams;
    }
}
