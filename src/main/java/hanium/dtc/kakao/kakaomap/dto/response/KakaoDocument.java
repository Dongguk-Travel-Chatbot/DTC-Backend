package hanium.dtc.kakao.kakaomap.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoDocument {
    @JsonProperty("y")
    private String latitude;

    @JsonProperty("x")
    private String longitude;
}
