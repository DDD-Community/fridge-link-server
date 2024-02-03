package mara.server.domain.s3

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
class S3Controller(
    private val s3Service: S3Service
) {

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun fileUpload(
        @RequestParam("image") multipartFile: MultipartFile,
        @RequestParam("dir", required = false) customDir: String?
    ): CommonResponse<String> {
        return success(s3Service.upload(multipartFile, customDir))
    }
}
