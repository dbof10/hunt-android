import {
    FETCH_MEETUP_SUCCESS, FETCH_MEETUP_FAIL, FETCH_MEETUP_LOADING, LOAD, LoadResult,
} from "../action/meetup";
import * as apiClient from "../model/apiClient";
import 'rxjs/add/operator/mergeMap';
import Rx from "rxjs";

export const fetchMeetupEpic = action$ =>
    action$.filter(action => action.type === LOAD)
        .mergeMap(action =>
            apiClient.getMeetups(action.payload)
                .map(response => response.meetups)
                .map(content => LoadResult(FETCH_MEETUP_SUCCESS, content))
                .catch(error => Rx.Observable.of({
                    type: FETCH_MEETUP_FAIL,
                    payload: error
                }))
                .startWith(LoadResult(FETCH_MEETUP_LOADING, null))
        );

