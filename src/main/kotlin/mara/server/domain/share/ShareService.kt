package mara.server.domain.share

import mara.server.domain.ingredient.IngredientDetailRepository
import mara.server.domain.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
class ShareService(
    private val shareRepository: ShareRepository,
    private val applyShareRepository: ApplyShareRepository,
    private val ingredientDetailRepository: IngredientDetailRepository,
    private val userService: UserService,
) {

    private val deleted = "deleted"

    @Transactional
    fun createShare(shareRequest: ShareRequest): Long {
        val ingredientDetailId = shareRequest.ingredientDetailId
        val ingredientDetail =
            ingredientDetailRepository.findIngredientDetailByIngredientDetailId(ingredientDetailId)
                .orElseThrow { NoSuchElementException("해당 식재료가 존재하지 않습니다. ID: $ingredientDetailId") }
        val user = userService.getCurrentLoginUser()
        // 생성 보단 조회가 빈번 할것 같아, 매번 조회 할 때마다, 일자와 시간을 분리하기 보단, 저장 할 때 각각 & 일자+시간 저장 하는 방식으로 진행
        val share = Share(
            user = user,
            ingredientDetail = ingredientDetail,
            title = shareRequest.title,
            content = shareRequest.content,
            shareDate = shareRequest.shareDate,
            shareTime = shareRequest.shareTime,
            shareDatetime = shareRequest.shareDate.atTime(shareRequest.shareTime),
            limitPerson = shareRequest.limitPerson,
            location = shareRequest.location,
            status = ShareStatus.valueOf(shareRequest.status),
            thumbNailImage = shareRequest.thumbNailImage,
        )

        return shareRepository.save(share).id
    }

    @Transactional
    fun applyShare(applyShareRequest: ApplyShareRequest): Long {
        val share = getShare(applyShareRequest.shareId)
        val user = userService.getCurrentLoginUser()
        if (share.user.userId == user.userId) throw IllegalAccessException("본인이 올린 나눔 글에는 신청을 할 수 없습니다.")
        if (applyShareRepository.existsByUserAndShare(user, share)) throw IllegalAccessException("이미 신청한 나눔 입니다.")
        val applyShare = ApplyShare(
            user = user,
            share = share
        )
        share.addApplyShareList(applyShare)
        /**
         신청자 수가 제한 한 신청자 수와 같아지는지 확인을 위한 기능
         기획에 따로 해당 상황일 때 신청을 막는 기능은 없지만
         혹시 모를 상황에 대비해 추가 함
         **/
        share.plusPeopleCnt()
        return shareRepository.save(share).id
    }

    fun getShare(shareId: Long): Share {
        return shareRepository.findById(shareId)
            .orElseThrow { NoSuchElementException("해당 나눔 게시물이 존재하지 않습니다. ID: $shareId") }
    }

    fun getShareInfo(shareId: Long): ShareResponse {
        return ShareResponse(getShare(shareId))
    }

    fun getAllShareList(): List<ShareResponse>? {
        val shareList = shareRepository.findAll()
        return shareList.toShareResponseList()
    }

    fun getAllMyShareList(): List<ShareResponse>? {
        return shareRepository.findAllByUser(userService.getCurrentLoginUser())?.toShareResponseList()
    }

    fun getAllApplyUserList(shareId: Long): List<String>? {
        val share = getShare(shareId)
        val applyShareList = share.applyShareList
        return applyShareList.map { it.user.name }.toList()
    }

    fun getAllMyApplyShareList(): List<ShareResponse>? {
        return applyShareRepository.findAllByUser(userService.getCurrentLoginUser())
            ?.map { ShareResponse(it.share) }
            ?.toList()
    }

    @Transactional
    fun updateShareInfo(shareId: Long, updateShareRequest: UpdateShareRequest): Boolean {
        val ingredientDetailId = updateShareRequest.ingredientDetailId
        val share = getShare(shareId)
        if (ingredientDetailId != share.ingredientDetail.ingredientDetailId) {
            val ingredientDetail =
                ingredientDetailRepository.findIngredientDetailByIngredientDetailId(
                    ingredientDetailId
                )
                    .orElseThrow { NoSuchElementException("해당 식재료가 존재하지 않습니다. ID: $ingredientDetailId") }
            share.updateIngredientDetail(ingredientDetail)
        }

        share.updateShare(updateShareRequest)

        val savedShare = shareRepository.save(share)
        return savedShare.id == share.id
    }

    @Transactional
    fun changeShareStatus(shareId: Long, updateShareStatusRequest: UpdateShareStatusRequest): Boolean {
        val share = getShare(shareId)
        share.updateStatus(ShareStatus.valueOf(updateShareStatusRequest.status))
        val savedShare = shareRepository.save(share)

        return savedShare.id == share.id
    }

    @Transactional
    fun deleteShare(shareId: Long): String {
        val user = userService.getCurrentLoginUser()
        if (user.userId != getShare(shareId).user.userId) throw RuntimeException("잘못된 사용자로부터 전달된 요청입니다.")
        shareRepository.deleteById(shareId)
        return deleted
    }

    @Transactional
    fun deleteApplyShare(applyId: Long): String {
        val user = userService.getCurrentLoginUser()
        val applyShare = applyShareRepository.findById(applyId)
            .orElseThrow { NoSuchElementException("해당 나눔 신청이 존재 하지 않습니다. ID: $applyId") }
        if (user.userId != applyShare.user.userId) throw RuntimeException("잘못된 사용자로부터 전달된 요청입니다.")
        /**
         신청을 취소하면 사람 수 차감
         **/
        applyShare.share.minusPeopleCnt()
        applyShareRepository.deleteById(applyId)
        return deleted
    }
}
