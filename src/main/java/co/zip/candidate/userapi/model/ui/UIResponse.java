package co.zip.candidate.userapi.model.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * Model class for API response
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UIResponse {

    /**
     * success / failure
     */
    @JsonProperty("success")
    private Boolean success;

    /**
     * userId
     */
    @JsonProperty("userId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userId;

    /**
     * accountId
     */
    @JsonProperty("accountId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accountId;

    /**
     * error code populated only for exception
     */
    @JsonProperty("errorCode")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;

    /**
     * error description populated only for exception
     */
    @JsonProperty("errorDesc")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorDesc;

    /**
     * response objects
     */
    @JsonProperty("resultList")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Object> resultList;
}
