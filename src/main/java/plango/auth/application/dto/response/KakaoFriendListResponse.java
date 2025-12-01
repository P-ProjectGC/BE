package plango.auth.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoFriendListResponse(

        @JsonProperty("elements")
        List<KakaoFriendSummary> elements

) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoFriendSummary(

            @JsonProperty("id")
            String id,

            @JsonProperty("uuid")
            String uuid,

            @JsonProperty("profile_nickname")
            String profileNickname

    ) {
    }
}
