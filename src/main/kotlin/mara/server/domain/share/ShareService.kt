package mara.server.domain.share

import mara.server.domain.ingredient.IngredientDetailRepository
import mara.server.domain.user.UserRepository
import mara.server.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class ShareService(
    private val shareRepository: ShareRepository,
    private val applyShareRepository: ApplyShareRepository,
    private val ingredientDetailRepository: IngredientDetailRepository,
    private val userRepository: UserRepository,
    private val userService: UserService,
) {

    fun createShare(shareRequest: ShareRequest): Long {
        val refrigIngr =
            ingredientDetailRepository.findIngredientDetailByIngredientDetailIdAndIsDeletedIsFalse(shareRequest.refrigIngrId)
                .orElseThrow()
        val user = userRepository.findById(shareRequest.userId).orElseThrow()
        val share = Share(
            user = user,
            ingredientDetail = refrigIngr,
            title = shareRequest.title,
            content = shareRequest.content,
            limitDatetime = shareRequest.limitDate.atTime(shareRequest.limitTime),
            limitPerson = shareRequest.limitPerson,
            personCnt = shareRequest.personCnt,
            location = shareRequest.location,
            status = shareRequest.status,
            thumbNailImage = shareRequest.thumbNailImage,
        )

        return shareRepository.save(share).id
    }

    fun applyShare(applyShareRequest: ApplyShareRequest): Long {
        val share = shareRepository.findById(applyShareRequest.shareId).orElseThrow()
        val user = userRepository.findById(applyShareRequest.userId).orElseThrow()
        val applyShare = ApplyShare(
            user = user,
            share = share
        )
        share.addApplyShareList(applyShare)
        return shareRepository.save(share).id
    }

    fun getShareInfo(shareId: Long): ShareResponse {
        return ShareResponse(shareRepository.findById(shareId).orElseThrow())
    }

    fun getAllShareList(): List<ShareResponse> {
        return shareRepository.findAll().toShareResponseList()
    }

    fun getAllMyShareList(): List<ShareResponse> {
        return shareRepository.findAllByUser(userService.getCurrentLoginUser().userId).toShareResponseList()
    }

    fun getAllApplyUserList(shareId: Long): List<String> {
        return shareRepository.findById(shareId).get().applyShareList.stream().map { a -> a.user.name }.toList()
    }

    fun getAllMyuApplyShareList(): List<ShareResponse> {
        val user = userService.getCurrentLoginUser()
        return applyShareRepository.findAllByUser(user.userId).stream().map { a -> ShareResponse(a.share) }.toList()
    }

    fun updateShareInfo() {
    }

    fun changeShareStatus() {
    }

    fun deleteApply() {
    }
    fun deleteShare() {
    }
}
