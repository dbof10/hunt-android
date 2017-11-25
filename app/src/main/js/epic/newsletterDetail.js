import {
    FETCH_LETTER_DETAIL_LOADING,
    FETCH_LETTER_DETAIL_SUCCESS,
    FETCH_LETTER_DETAIL_FAIL,
    LOAD,
    LoadResult
}from "../action/newsletterDetail"

import * as apiClient from "../model/apiClient";
import 'rxjs/add/operator/mergeMap';
import Rx from "rxjs";

export const fetchNewsLetterDetailEpic = action$ =>
    action$.filter(action => action.type === LOAD)
        .mergeMap(action => {
            return apiClient.getNewsLetterDetail(action.id)
                .map(response => response.newsletter)
                .map(content => LoadResult(FETCH_LETTER_DETAIL_SUCCESS, content))
                .catch(error => Rx.Observable.of({
                    type: FETCH_LETTER_DETAIL_FAIL,
                    payload: error
                }))
                .startWith(LoadResult(FETCH_LETTER_DETAIL_LOADING, null))
        });