package hanium.dtc.kakao.kakaomap.service;

import hanium.dtc.exception.CommonException;
import hanium.dtc.exception.ErrorCode;
import hanium.dtc.kakao.kakaomap.dto.response.KakaoMapResponse;
import hanium.dtc.travel.domain.RecordDetail;
import hanium.dtc.travel.repository.RecordDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KakaoMapService {
    private final WebClient kakaoWebClient;
    private final RecordDetailRepository recordDetailRepository;

    @Autowired
    public KakaoMapService(WebClient kakaoWebClient, RecordDetailRepository recordDetailRepository) {
        this.kakaoWebClient = kakaoWebClient;
        this.recordDetailRepository = recordDetailRepository;
    }

    public KakaoMapResponse getCoordinates(Long detailId) {
        RecordDetail recordDetail = recordDetailRepository.getById(detailId);

        return kakaoWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/address.json")
                        .queryParam("query", recordDetail.getDetailAddress())
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new CommonException(ErrorCode.BAD_REQUEST_JSON)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CommonException(ErrorCode.INTERNAL_SERVER_ERROR)))
                .bodyToMono(KakaoMapResponse.class)
                .block();
    }
}
