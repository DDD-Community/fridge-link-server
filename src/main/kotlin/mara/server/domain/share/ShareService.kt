package mara.server.domain.share

import mara.server.domain.ingredient.IngredientDetailRepository
import mara.server.domain.user.UserService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class ShareService(
    private val shareRepository: ShareRepository,
    private val applyShareRepository: ApplyShareRepository,
    private val ingredientDetailRepository: IngredientDetailRepository,
    private val userService: UserService,
) {

    private val deleted = "deleted"

    fun createShare(shareRequest: ShareRequest): Long {
        val refrigIngrId = shareRequest.refrigIngrId
        val refrigIngr =
            ingredientDetailRepository.findIngredientDetailByIngredientDetailIdAndIsDeletedIsFalse(refrigIngrId)
                .orElseThrow { NoSuchElementException("해당 식재료가 존재하지 않습니다. ID: $refrigIngrId") }
        val user = userService.getCurrentLoginUser()
        // 생성 보단 조회가 빈번 할것 같아, 매번 조회 할 때마다, 일자와 시간을 분리하기 보단, 저장 할 때 각각 & 일자+시간 저장 하는 방식으로 진행
        val share = Share(
            user = user,
            ingredientDetail = refrigIngr,
            title = shareRequest.title,
            content = shareRequest.content,
            limitTime = shareRequest.limitTime,
            limitDate = shareRequest.limitDate,
            limitDatetime = shareRequest.limitDate.atTime(shareRequest.limitTime),
            limitPerson = shareRequest.limitPerson,
            personCnt = shareRequest.personCnt,
            location = shareRequest.location,
            status = ShareStatus.valueOf(shareRequest.status),
            thumbNailImage = shareRequest.thumbNailImage,
        )

        return shareRepository.save(share).id
    }

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

    fun updateShareInfo(shareId: Long, updateShareRequest: UpdateShareRequest): Boolean {
        val refrigIngrId = updateShareRequest.refrigIngrId
        val share = getShare(shareId)
        refrigIngrId?.let { ingrId ->
            val refrigIngr =
                ingredientDetailRepository.findIngredientDetailByIngredientDetailIdAndIsDeletedIsFalse(ingrId)
                    .orElseThrow { NoSuchElementException("해당 식재료가 존재하지 않습니다. ID: $ingrId") }
            share.updateIngredientDetail(refrigIngr)
        }
        share.updateShare(updateShareRequest)

        val savedShare = shareRepository.save(share)
        return savedShare.id == share.id
    }

    fun changeShareStatus(shareId: Long, updateShareStatusRequest: UpdateShareStatusRequest): Boolean {
        val share = getShare(shareId)
        share.updateStatus(ShareStatus.valueOf(updateShareStatusRequest.status))
        val savedShare = shareRepository.save(share)

        return savedShare.id == share.id
    }

    fun deleteShare(shareId: Long): String {
        val user = userService.getCurrentLoginUser()
        if (user.userId != getShare(shareId).user.userId) throw RuntimeException("잘못된 사용자로부터 전달된 요청입니다.")
        shareRepository.deleteById(shareId)
        return deleted
    }
    fun deleteApply(applyId: Long): String {
        val user = userService.getCurrentLoginUser()
        val applyShare = applyShareRepository.findById(applyId).orElseThrow { NoSuchElementException("해당 나눔 신청이 존재 하지 않습니다. ID: $applyId") }
        if (user.userId != applyShare.user.userId) throw RuntimeException("잘못된 사용자로부터 전달된 요청입니다.")
        applyShareRepository.deleteById(applyId)
        return deleted
    }
}
