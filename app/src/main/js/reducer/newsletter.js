import {
    FETCH_LETTER_FAIL,
    FETCH_LETTER_LOADING,
    FETCH_LETTER_SUCCESS,
    FETCH_MORE_LETTER_FAIL,
    FETCH_MORE_LETTER_LOADING,
    FETCH_MORE_LETTER_SUCCESS,
    SUBSCRIBE_LETTER_FAILURE,
    SUBSCRIBE_LETTER_SUCCESS,
    NAVIGATE_LETTER_DETAIL_SCREEN
}
    from '../action/newsletter';

import {
    FETCH_LETTER_DETAIL_FAIL,
    FETCH_LETTER_DETAIL_LOADING,
    FETCH_LETTER_DETAIL_SUCCESS,
    NAVIGATE_BACK_LETTER_SCREEN
} from '../action/newsletterDetail';

import _ from 'lodash/core';
import {NavigationActions} from 'react-navigation';
import {AppNavigator} from '../navigator/newsletter';

const main = AppNavigator.router.getActionForPathAndParams('NewsLetter'); //Reference navigator/ask

const initialNavState = AppNavigator.router.getStateForAction(
    main,
);

export function newsletterNav(state = initialNavState, action) {
    let nextState;

    switch (action.type) {
        case NAVIGATE_LETTER_DETAIL_SCREEN:

            nextState = AppNavigator.router.getStateForAction(
                NavigationActions.navigate({routeName: 'NewsLetterDetail', params: action.payload}),
                state
            );
            break;
        case NAVIGATE_BACK_LETTER_SCREEN:
            nextState = AppNavigator.router.getStateForAction(
                NavigationActions.back(),
                state
            );
            break;
        default:
            nextState = AppNavigator.router.getStateForAction(action, state);
    }


    return nextState || state;


}

const initialLetterState = {
    tabs: [],
    subscribed: false,
    subscribedError: null
};

export function newsletter(state = initialLetterState, action) {
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

const initialLetterDetailState = {
    isLoading: false,
    error: null,
    dataSource: null,
};

export function newsletterDetail(state = initialLetterDetailState, action) {
    switch (action.type) {
        case FETCH_LETTER_DETAIL_SUCCESS:
            return {
                ...state,
                isLoading: false,
                dataSource: action.payload,
            };

        case FETCH_LETTER_DETAIL_LOADING:
            return {
                ...state,
                error: null,
                isLoading: true,
            };

        case FETCH_LETTER_DETAIL_FAIL:
            return {
                ...state,
                isLoading: false,
                error: action.payload
            };
        default:
            return state;
    }
}
