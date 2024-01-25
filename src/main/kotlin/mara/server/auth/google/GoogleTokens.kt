package mara.server.auth.google

import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleTokens(
    @JsonProperty("access_token")
    var accessToken: String = "",

    @JsonProperty("token_type")
    var tokenType: String = "",

    @JsonProperty("refresh_token")
    var refreshToken: String = "",

    @JsonProperty("expires_in")
    var expiresIn: String = "",

    @JsonProperty("id_token")
    var idToken: String = "",

    @JsonProperty("scope")
    var scope: String = "",
)
