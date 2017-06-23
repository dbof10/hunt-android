package com.ctech.eaty.repository

import com.ctech.eaty.entity.Vote
import com.ctech.eaty.response.VoteResponse
import com.ctech.eaty.ui.vote.action.VoteBarCode
import com.nytimes.android.external.store2.base.impl.Store
import io.reactivex.Observable


class VoteRepository(private val store: Store<VoteResponse, VoteBarCode>,
                     private val apiClient: ProductHuntApi,
                     private val appSettingsManager: AppSettingsManager) {
    fun getVotes(barcode: VoteBarCode): Observable<List<Vote>> {
        return store.get(barcode).map {
            it.votes
        }.retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }
}