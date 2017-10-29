import {
    FETCH_ASK_DETAIL_SUCCESS, FETCH_ASK_DETAIL_FAIL, FETCH_ASK_DETAIL_LOADING, LOAD, LOAD_MORE, LoadResult,
    FETCH_MORE_ASK_DETAIL_LOADING, FETCH_MORE_ASK_DETAIL_SUCCESS, FETCH_MORE_ASK_DETAIL_FAIL
} from "../action/askDetail";
import * as apiClient from "../model/apiClient";
import 'rxjs/add/operator/mergeMap';
import Rx from "rxjs";

export const fetchAskDetailEpic = action$ =>
    action$.filter(action => action.type === LOAD)
        .mergeMap(action =>
            apiClient.getAskDetail(action.id, null)
                .map(response =>response.node)
                .map(content => LoadResult(FETCH_ASK_DETAIL_SUCCESS, content))
                .catch(error => Rx.Observable.of({
                    type: FETCH_ASK_DETAIL_FAIL,
                    payload: error
                }))
                .startWith(LoadResult(FETCH_ASK_DETAIL_LOADING, null))
        );

export const fetchMoreRecommendAskEpic = (action$, store) =>
    action$.filter(action => action.type === LOAD_MORE)
        .mergeMap(action => {
            let pageInfo = store.getState().ask.pageInfo;

            if (pageInfo.hasNextPage) {
                let nextOffset = pageInfo.endCursor;
                return apiClient.getAskDetail(action.id, nextOffset)
                    .map(response => response.product_requests)
                    .map(content => LoadResult(FETCH_MORE_ASK_DETAIL_SUCCESS, content))
                    .catch(error => Rx.Observable.of({
                        type: FETCH_MORE_ASK_DETAIL_FAIL,
                        payload: error
                    }))
                    .startWith(LoadResult(FETCH_MORE_ASK_DETAIL_LOADING, null))
            } else {
                return Rx.Observable.empty()
            }
        });