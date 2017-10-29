import {
    FETCH_ASK_SUCCESS, FETCH_ASK_FAIL, FETCH_ASK_LOADING, LOAD, LOAD_MORE, LoadResult,
    FETCH_MORE_ASK_LOADING, FETCH_MORE_ASK_SUCCESS, FETCH_MORE_ASK_FAIL
} from "../action/ask";
import * as apiClient from "../model/apiClient";
import 'rxjs/add/operator/mergeMap';
import Rx from "rxjs";

export const fetchAskEpic = action$ =>
    action$.filter(action => action.type === LOAD)
        .mergeMap(action =>
            apiClient.getAsk(action.tab, null)
                .map(response => response.product_requests)
                .map(content => LoadResult(FETCH_ASK_SUCCESS, content))
                .catch(error => Rx.Observable.of({
                    type: FETCH_ASK_FAIL,
                    payload: error
                }))
                .startWith(LoadResult(FETCH_ASK_LOADING, null))
        );

export const fetchMoreAskEpic = (action$, store) =>
    action$.filter(action => action.type === LOAD_MORE)
        .mergeMap(action => {
            let pageInfo = store.getState().ask.pageInfo;

            if (pageInfo.hasNextPage) {
                let nextOffset = pageInfo.endCursor;
                return apiClient.getAsk(action.tab,nextOffset)
                    .map(response => response.product_requests)
                    .map(content => LoadResult(FETCH_MORE_ASK_SUCCESS, content))
                    .catch(error => Rx.Observable.of({
                        type: FETCH_MORE_ASK_FAIL,
                        payload: error
                    }))
                    .startWith(LoadResult(FETCH_MORE_ASK_LOADING, null))
            } else {
                return Rx.Observable.empty()
            }
        });