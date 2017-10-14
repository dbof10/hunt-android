import {
    FETCH_JOB_SUCCESS, FETCH_JOB_FAIL, FETCH_JOB_LOADING, LOAD, LOAD_MORE, LoadResult,
    FETCH_MORE_JOB_LOADING, FETCH_MORE_JOB_SUCCESS, FETCH_MORE_JOB_FAIL
} from "../action/job";
import * as apiClient from "../model/apiClient";
import 'rxjs/add/operator/mergeMap';
import Rx from "rxjs";

export const fetchJobEpic = action$ =>
    action$.filter(action => action.type === LOAD)
        .mergeMap(action =>
            apiClient.getJobs(null)
                .map(response => response.job_board.jobs)
                .map(content => LoadResult(FETCH_JOB_SUCCESS, content))
                .catch(error => Rx.Observable.of({
                    type: FETCH_JOB_FAIL,
                    payload: error
                }))
                .startWith(LoadResult(FETCH_JOB_LOADING, null))
        );

export const fetchMoreJobEpic = (action$, store) =>
    action$.filter(action => action.type === LOAD_MORE)
        .mergeMap(action => {
            let pageInfo = store.getState().job.pageInfo;

            if (pageInfo.hasNextPage) {
                let nextOffset = pageInfo.endCursor;
                return apiClient.getJobs(nextOffset)
                    .map(response => response.job_board.jobs)
                    .map(content => LoadResult(FETCH_MORE_JOB_SUCCESS, content))
                    .catch(error => Rx.Observable.of({
                        type: FETCH_MORE_JOB_FAIL,
                        payload: error
                    }))
                    .startWith(LoadResult(FETCH_MORE_JOB_LOADING, null))
            }else {
                return Rx.Observable.empty()
            }
        });