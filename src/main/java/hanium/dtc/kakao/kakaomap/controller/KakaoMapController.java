package hanium.dtc.kakao.kakaomap.controller;

import hanium.dtc.global.ResponseDto;
import hanium.dtc.kakao.kakaomap.service.KakaoMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoMapController {
    private final KakaoMapService kakaoMapService;
    @GetMapping("/api/coordinary/{detail_id}")
    public ResponseDto<?> getCoordinary(@PathVariable Long detail_id){
        return ResponseDto.ok(kakaoMapService.getCoordinates(detail_id));
    }
}
