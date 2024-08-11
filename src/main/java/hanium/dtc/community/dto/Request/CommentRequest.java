package hanium.dtc.community.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record CommentRequest(
        @JsonProperty("content")
        String content,

        @JsonProperty("is_reply")
        Boolean isReply,

        @JsonProperty("comment_id")
        Long commentId
) {
}