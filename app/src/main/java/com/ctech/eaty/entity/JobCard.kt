package com.ctech.eaty.entity

data class JobCard(val jobs: List<Job>) : HomeCard {
    override val type: String
        get() = JOBS
}