import {
    FETCH_LETTER_FAIL,
    FETCH_LETTER_LOADING,
    FETCH_LETTER_SUCCESS,
    FETCH_MORE_LETTER_FAIL,
    FETCH_MORE_LETTER_LOADING,
    FETCH_MORE_LETTER_SUCCESS,
    SUBSCRIBE_LETTER_FAILURE,
    SUBSCRIBE_LETTER_SUCCESS
} from '../action/newsletter'
import _ from 'lodash/core';

const initialState = {
    tabs: [],
    subscribed: false,
    subscribedError: null
};

export default function newsletter(state = initialState, action) {
    const key = action.key;
    let tabs = state.tabs;

    switch (action.type) {
        case FETCH_LETTER_SUCCESS:
            tabs[key] = {
                ...state.tabs[key],
                isLoading: false,
                dataSource: action.payload.edges.map((item) => item.node),
                pageInfo: action.payload.pageInfo
            };
            return {
                ...state,
                tabs
            };

        case FETCH_LETTER_LOADING:
            tabs[key] = {
                ...state.tabs[key],
                error: null,
                isLoading: true,
            };

            return {
                ...state,
                tabs
            };
        case FETCH_LETTER_FAIL:
            tabs[key] = {
                ...state.tabs[key],
                isLoading: false,
                error: action.payload
            };
            return {
                ...state,
                tabs
            };
        case FETCH_MORE_LETTER_SUCCESS:
            const oldDataSource = state.dataSource;
            const newDataSource = action.payload.edges.map((item) => item.node);

            const combine = _.concat(oldDataSource, newDataSource);


            tabs[key] = {
                ...state.tabs[key],
                isLoadingMore: false,
                dataSource: combine,
                pageInfo: action.payload.pageInfo
            };

            return {
                ...state,
                tabs
            };

        case FETCH_MORE_LETTER_LOADING:
            tabs[key] = {
                ...state.tabs[key],
                error: null,
                moreError: null,
                isLoadingMore: true,
            };

            return {
                ...state,
                tabs
            };

        case FETCH_MORE_LETTER_FAIL:
            tabs[key] = {
                ...state.tabs[key],
                isLoadingMore: false,
                moreError: action.payload
            };

            return {
                ...state,
                tabs
            };
        case SUBSCRIBE_LETTER_SUCCESS:
            return {
                ...state,
                subscribed: true,
                subscribedError: null
            };
        case SUBSCRIBE_LETTER_FAILURE:
            return {
                ...state,
                subscribedError: action.payload
            };
        default:
            return state;
    }
}