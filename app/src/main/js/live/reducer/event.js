/**
 * Created by daniel on 8/5/17.
 */

import {
    FETCH_EVENT_SUCCESS, FETCH_EVENT_LOADING, FETCH_EVENT_FAIL,
    FETCH_MORE_EVENT_SUCCESS, FETCH_MORE_EVENT_LOADING, FETCH_MORE_EVENT_FAIL
} from '../action/action'
import _ from 'lodash/core';

const initialState = {
    isLoading: false,
    error: null,
    offset: 0,
    isLoadingMore: false,
    moreError: null,
    dataSource: [],
};

export default function event(state = initialState, action) {
    switch (action.type) {
        case FETCH_EVENT_SUCCESS:
            return {
                ...state,
                isLoading: false,
                dataSource: action.payload,
            };

        case FETCH_EVENT_LOADING:
            return {
                ...state,
                isLoading: true,
            };

        case FETCH_EVENT_FAIL:
            return {
                ...state,
                isLoading: false,
                error: action.payload
            };
        case FETCH_MORE_EVENT_SUCCESS:
            const oldDataSource = state.dataSource;
            const newDataSource = action.payload;

            const combine = _.concat(oldDataSource, newDataSource);

            return {
                ...state,
                isLoadingMore: false,
                dataSource: combine,
            };

        case FETCH_MORE_EVENT_LOADING:
            return {
                ...state,
                isLoadingMore: true,
            };

        case FETCH_MORE_EVENT_FAIL:
            return {
                ...state,
                isLoadingMore: false,
                moreError: action.payload
            };

        default:
            return state;
    }
}