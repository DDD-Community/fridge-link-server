package mara.server.auth.google
import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleInfoResponse(
    @JsonProperty("email")
    var email: String = "",
)
