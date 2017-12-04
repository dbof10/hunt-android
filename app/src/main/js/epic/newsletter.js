import {
    FETCH_LETTER_FAIL,
    FETCH_LETTER_LOADING,
    FETCH_LETTER_SUCCESS,
    FETCH_MORE_LETTER_FAIL,
    FETCH_MORE_LETTER_LOADING,
    FETCH_MORE_LETTER_SUCCESS,
    LOAD,
    LOAD_MORE,
    SUBSCRIBE,
    SUBSCRIBE_LETTER_FAILURE,
    SUBSCRIBE_LETTER_SUCCESS,
    LoadResult
} from "../action/newsletter";


import * as apiClient from "../model/apiClient";
import 'rxjs/add/operator/mergeMap';
import Rx from "rxjs";

export const fetchNewsLetterEpic = action$ =>
    action$.filter(action => action.type === LOAD)
        .mergeMap(action => {
            let key = action.key;
            return apiClient.getNewsLetter(action.tab, null)
                .map(response => response.newsletters)
                .map(content => LoadResult(key, FETCH_LETTER_SUCCESS, content))
                .catch(error => Rx.Observable.of({
                    type: FETCH_LETTER_FAIL,
                    payload: error
                }))
                .startWith(LoadResult(key, FETCH_LETTER_LOADING, null))
        });

export const fetchMoreNewsLetterEpic = (action$, store) =>
    action$.filter(action => action.type === LOAD_MORE)
        .mergeMap(action => {

            let key = action.key;
            let pageInfo = store.getState().newsletter.tabs[key].pageInfo;

            if (pageInfo.hasNextPage) {
                let nextOffset = pageInfo.endCursor;
                return apiClient.getNewsLetter(action.tab, nextOffset)
                    .map(response => response.newsletters)
                    .map(content => LoadResult(key, FETCH_MORE_LETTER_SUCCESS, content))
                    .catch(error => Rx.Observable.of({
                        type: FETCH_MORE_LETTER_FAIL,
                        payload: error
                    }))
                    .startWith(LoadResult(key, FETCH_MORE_LETTER_LOADING, null))
            } else {
                return Rx.Observable.empty()
            }
        });



export const subscribeNewsLetterEpic = (action$, store) =>
    action$.filter(action => action.type === SUBSCRIBE)
        .mergeMap(action => {
            return apiClient.subscribeNewsLetter(action.email, action.status)
                .map(ignored => {
                        return {type: SUBSCRIBE_LETTER_SUCCESS}
                    }
                )
                .catch(error => Rx.Observable.of({
                    type: SUBSCRIBE_LETTER_FAILURE,
                    payload: error
                }))
        });