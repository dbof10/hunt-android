package com.ctech.eaty.ui.profile.state

import com.ctech.eaty.entity.UserDetail

data class ProfileState(val loading: Boolean = false,
                        val submitError: Throwable? = null,
                        val emailError: Throwable? = null,
                        val nameError: Throwable? = null,
                        val headlineError: Throwable? = null,
                        val content: UserDetail = UserDetail.GUEST)
