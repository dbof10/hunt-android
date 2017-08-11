package com.ctech.eaty.repository

import com.ctech.eaty.entity.Comment
import com.ctech.eaty.entity.Comments
import com.ctech.eaty.ui.comment.action.CommentBarCode
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Observable

class CommentRepository(private val store: Store<Comments, CommentBarCode>,
                        private val apiClient: ProductHuntApi,
                        private val appSettingsManager: AppSettingsManager) {
    fun getComments(barcode: CommentBarCode): Observable<List<Comment>> {
        return store.get(barcode)
                .map {
                    it.comments
                }
                .toObservable()
                .retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }
}