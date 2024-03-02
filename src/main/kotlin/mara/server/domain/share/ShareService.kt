package mara.server.domain.share

import mara.server.auth.security.getCurrentLoginUserId
import mara.server.domain.ingredient.IngredientDetailRepository
import mara.server.domain.user.UserService
import mara.server.exception.IllegalAccessShareException
import mara.server.exception.IllegalAccessShareException.Companion.CREATED_BY_LOGIN_USER
import mara.server.exception.IllegalAccessShareException.Companion.DIFFERENT_USER
import mara.server.exception.IllegalAccessShareException.Companion.DUPLICATED_APPLY
import mara.server.exception.ShareException.Companion.NO_SUCH_INGREDIENT
import mara.server.exception.ShareException.Companion.NO_SUCH_SHARE
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
            ingredientDetailRepository.findById(ingredientDetailId)
                .orElseThrow { NoSuchElementException("$NO_SUCH_INGREDIENT Id: $ingredientDetailId") }
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
            thumbnailImage = shareRequest.thumbnailImage,
        )

        return shareRepository.save(share).id
    }

    @Transactional
    fun applyShare(applyShareRequest: ApplyShareRequest): Long {
        val share = getShare(applyShareRequest.shareId)
        val user = userService.getCurrentLoginUser()
        if (share.user.userId == user.userId) throw IllegalAccessShareException(CREATED_BY_LOGIN_USER)
        if (applyShareRepository.existsByUserAndShare(user, share)) throw IllegalAccessShareException(DUPLICATED_APPLY)
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
        share.plusPeopleCount()
        return shareRepository.save(share).id
    }

    fun getShare(shareId: Long): Share {
        return shareRepository.findById(shareId)
            .orElseThrow { NoSuchElementException("$NO_SUCH_SHARE Id: $shareId") }
    }

    fun getShareInfo(shareId: Long): ShareDetailResponse {
        val share = getShare(shareId)
        val hasMatchingUser = share.applyShareList.any { applyShare ->
            applyShare.user.userId == getCurrentLoginUserId()
        }
        return ShareDetailResponse(share, getCurrentLoginUserId() == share.user.userId, hasMatchingUser)
    }

    fun getAllShareList(pageable: Pageable, status: String): Page<ShareResponse> {
        val currentLoginUser = userService.getCurrentLoginUser()
        val shareList = shareRepository.findAllMyFriendsShare(pageable, ShareStatus.valueOf(status), currentLoginUser).map { share ->
            val hasMatchingUser = share.applyShareList.any { applyShare ->
                applyShare.user.userId == currentLoginUser.userId
            }
            ShareResponse(share, hasMatchingUser)
        }
        return shareList
    }

    fun getAllMyCreatedShareList(pageable: Pageable, status: String): Page<ShareResponse> {
        return shareRepository.findAllMyCreatedShare(pageable, ShareStatus.valueOf(status), userService.getCurrentLoginUser())
            .toShareResponseListPage()
    }

    fun getAllApplyUserList(shareId: Long): List<AppliedUserResponse>? {
        val share = getShare(shareId)
        return share.applyShareList.toApplyShareResponseList()
    }

    fun getAllMyAppliedShareList(pageable: Pageable, status: String): Page<ShareResponse>? {
        return shareRepository.findAllMyAppliedShare(pageable, ShareStatus.valueOf(status), userService.getCurrentLoginUser())
            .toShareResponseListPage()
    }

    fun getAllMyShareList(pageable: Pageable, status: String): Page<ShareResponse>? {
        return shareRepository.findAllMyShare(pageable, ShareStatus.valueOf(status), userService.getCurrentLoginUser())
            .toShareResponseListPage()
    }

    @Transactional
    fun updateShareInfo(shareId: Long, updateShareRequest: UpdateShareRequest): Boolean {
        val ingredientDetailId = updateShareRequest.ingredientDetailId
        val share = getShare(shareId)
        if (ingredientDetailId != share.ingredientDetail.ingredientDetailId) {
            val ingredientDetail =
                ingredientDetailRepository.findById(
                    ingredientDetailId
                )
                    .orElseThrow { NoSuchElementException("$NO_SUCH_INGREDIENT Id: $ingredientDetailId") }
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
        if (user.userId != getShare(shareId).user.userId) throw IllegalAccessShareException(DIFFERENT_USER)
        shareRepository.deleteById(shareId)
        return deleted
    }

    @Transactional
    fun deleteApplyShare(shareId: Long): String {
        val user = userService.getCurrentLoginUser()
        val share = shareRepository.findById(shareId).orElseThrow { NoSuchElementException("$NO_SUCH_SHARE Id: $shareId") }
        val applyShare = applyShareRepository.findByUserAndShare(user, share)
        /**
         신청을 취소하면 사람 수 차감
         **/
        applyShare.share.minusPeopleCount()
        applyShareRepository.delete(applyShare)
        return deleted
    }
}
