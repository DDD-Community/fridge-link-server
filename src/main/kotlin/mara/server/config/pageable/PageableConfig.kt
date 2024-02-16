package mara.server.config.pageable

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

@Configuration
class PageableConfig {

    @Bean
    @Qualifier("userPageable")
    fun userPageable(): Pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "ingredientAddDate")

    @Bean
    @Qualifier("ingredientPageable")
    fun ingredientPageable(): Pageable = PageRequest.of(0, 4, Sort.Direction.DESC, "addDate")
}
