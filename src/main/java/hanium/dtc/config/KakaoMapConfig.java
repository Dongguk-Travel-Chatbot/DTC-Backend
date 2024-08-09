package hanium.dtc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;

@Configuration
public class KakaoMapConfig {

    @Value("${kakao.client_id}")
    private String kakaoClientId;
    @Value("${kakao.base-url}")
    private String baseUrl;

    @Bean
    public WebClient kakaoWebClient(){
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoClientId)
                .build();
    }
}
