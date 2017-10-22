import {
    FETCH_EVENT_SUCCESS, FETCH_EVENT_LOADING, FETCH_EVENT_FAIL,
    FETCH_MORE_EVENT_SUCCESS, FETCH_MORE_EVENT_LOADING, FETCH_MORE_EVENT_FAIL
} from '../action/live'
import _ from 'lodash/core';

const initialState = {
    isLoading: false,
    error: null,
    offset: 0,
    isLoadingMore: false,
    moreError: null,
    dataSource: [],
    hasReachedEnd: false
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
                error: null,
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
            let hasReachedEnd = false;
            if (newDataSource.length === 0) {
                hasReachedEnd = true
            }
            const combine = _.concat(oldDataSource, newDataSource);

            return {
                ...state,
                isLoadingMore: false,
                dataSource: combine,
                offset: action.newOffset,
                hasReachedEnd
            };

        case FETCH_MORE_EVENT_LOADING:
            return {
                ...state,
                isLoadingMore: true,
                error: null,
                moreError: null,
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