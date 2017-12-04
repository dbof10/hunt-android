package com.ctech.eaty.ui.user.state

import com.ctech.eaty.entity.Product
import com.ctech.eaty.entity.UserDetail

data class UserDetailState(val page: Int = 1,
                           val loading: Boolean = false,
                           val loadError: Throwable? = null,
                           val loadingRL: Boolean = false,
                           val loadRLError: Throwable? = null,
                           val following: Boolean? = null,
                           val loadingProduct: Boolean = false,
                           val loadProductError: Throwable? = null,
                           val loadingMoreProduct: Boolean = false,
                           val loadMoreProductError: Throwable? = null,
                           val products: List<Product> = emptyList(),
                           val user: UserDetail? = UserDetail.GUEST
)
