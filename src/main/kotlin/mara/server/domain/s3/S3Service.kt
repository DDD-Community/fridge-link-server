package mara.server.domain.s3

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.util.UUID

@Service
class S3Service(
    private val s3Client: AmazonS3Client,
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,
    @Value("\${cloud.aws.s3.dir}")
    private val dir: String
) {
    @Transactional
    fun upload(file: MultipartFile, customDir: String? = dir): String {
        val fileName = UUID.randomUUID().toString() + "-" + file.originalFilename
        val objMeta = ObjectMetadata()

        val bytes = IOUtils.toByteArray(file.inputStream)
        objMeta.contentLength = bytes.size.toLong()

        val byteArrayIs = ByteArrayInputStream(bytes)

        s3Client.putObject(
            PutObjectRequest(bucket, dir + fileName, byteArrayIs, objMeta)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        )

        return s3Client.getUrl(bucket, dir + fileName).toString()
    }
}
