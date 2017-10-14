import {
    FETCH_JOB_SUCCESS, FETCH_JOB_LOADING, FETCH_JOB_FAIL,
    FETCH_MORE_JOB_SUCCESS, FETCH_MORE_JOB_LOADING, FETCH_MORE_JOB_FAIL
} from '../action/job'
import _ from 'lodash/core';

const initialState = {
    pageInfo: null,
    isLoading: false,
    error: null,
    isLoadingMore: false,
    moreError: null,
    dataSource: []
};

export default function job(state = initialState, action) {
    switch (action.type) {
        case FETCH_JOB_SUCCESS:
            return {
                ...state,
                isLoading: false,
                dataSource: action.payload.edges.map((item) => item.node),
                pageInfo: action.payload.pageInfo
            };

        case FETCH_JOB_LOADING:
            return {
                ...state,
                error: null,
                isLoading: true,
            };

        case FETCH_JOB_FAIL:
            return {
                ...state,
                isLoading: false,
                error: action.payload
            };
        case FETCH_MORE_JOB_SUCCESS:
            const oldDataSource = state.dataSource;
            const newDataSource = action.payload.edges.map((item) => item.node);

            const combine = _.concat(oldDataSource, newDataSource);


            return {
                ...state,
                isLoadingMore: false,
                dataSource: combine,
                pageInfo: action.payload.pageInfo
            };

        case FETCH_MORE_JOB_LOADING:
            return {
                ...state,
                error: null,
                moreError: null,
                isLoadingMore: true,
            };

        case FETCH_MORE_JOB_FAIL:
            return {
                ...state,
                isLoadingMore: false,
                moreError: action.payload
            };

        default:
            return state;
    }
}