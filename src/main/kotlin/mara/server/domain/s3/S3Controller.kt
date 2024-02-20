package mara.server.domain.s3

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/s3")
@RestController
@Tag(name = "AWS S3", description = "AWS S3 API")
class S3Controller(
    private val s3Service: S3Service
) {

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "S3 파일 업로드 API")
    fun fileUpload(
        @RequestParam("image") multipartFile: MultipartFile,
        @RequestParam("dir", required = false) customDir: String?
    ): CommonResponse<String> {
        return success(s3Service.upload(multipartFile, customDir))
    }
}
