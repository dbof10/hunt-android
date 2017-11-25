import {
    FETCH_ASK_FAIL,
    FETCH_ASK_LOADING,
    FETCH_ASK_SUCCESS,
    FETCH_MORE_ASK_FAIL,
    FETCH_MORE_ASK_LOADING,
    FETCH_MORE_ASK_SUCCESS,
    NAVIGATE_ASK_DETAIL_SCREEN
} from '../action/ask';

import {
    FETCH_ASK_DETAIL_FAIL,
    FETCH_ASK_DETAIL_LOADING,
    FETCH_ASK_DETAIL_SUCCESS,
    FETCH_MORE_ASK_DETAIL_FAIL,
    FETCH_MORE_ASK_DETAIL_LOADING,
    FETCH_MORE_ASK_DETAIL_SUCCESS,
    NAVIGATE_BACK_ASK_SCREEN
} from '../action/askDetail';

import _ from 'lodash/core';
import {NavigationActions} from 'react-navigation';
import {AppNavigator} from '../navigator/ask';

const main = AppNavigator.router.getActionForPathAndParams('Ask'); //Reference navigator/ask

const initialAskState = {
    pageInfo: null,
    isLoading: false,
    error: null,
    isLoadingMore: false,
    moreError: null,
    dataSource: [],

};

const initialNavState = AppNavigator.router.getStateForAction(
    main,
);

export function askNav(state = initialNavState, action) {
    let nextState;

    switch (action.type) {
        case NAVIGATE_ASK_DETAIL_SCREEN:

            nextState = AppNavigator.router.getStateForAction(
                NavigationActions.navigate({routeName: 'AskDetail', params: action.payload}),
                state
            );
            break;
        case NAVIGATE_BACK_ASK_SCREEN:
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

export function ask(state = initialAskState, action) {
    switch (action.type) {
        case FETCH_ASK_SUCCESS:
            return {
                ...state,
                isLoading: false,
                dataSource: action.payload.edges.map((item) => item.node),
                pageInfo: action.payload.pageInfo
            };

        case FETCH_ASK_LOADING:
            return {
                ...state,
                error: null,
                isLoading: true,
            };

        case FETCH_ASK_FAIL:
            return {
                ...state,
                isLoading: false,
                error: action.payload
            };
        case FETCH_MORE_ASK_SUCCESS:
            const oldDataSource = state.dataSource;
            const newDataSource = action.payload.edges.map((item) => item.node);

            const combine = _.concat(oldDataSource, newDataSource);


            return {
                ...state,
                isLoadingMore: false,
                dataSource: combine,
                pageInfo: action.payload.pageInfo
            };

        case FETCH_MORE_ASK_LOADING:
            return {
                ...state,
                error: null,
                moreError: null,
                isLoadingMore: true,
            };

        case FETCH_MORE_ASK_FAIL:
            return {
                ...state,
                isLoadingMore: false,
                moreError: action.payload
            };

        default:
            return state;
    }
}


const initialAskDetailState = {
    pageInfo: null,
    isLoading: false,
    error: null,
    isLoadingMore: false,
    moreError: null,
    dataSource: null,
};

export function askDetail(state = initialAskDetailState, action) {
    switch (action.type) {
        case FETCH_ASK_DETAIL_SUCCESS:
            return {
                ...state,
                isLoading: false,
                dataSource: action.payload,
                pageInfo: action.payload.pageInfo
            };

        case FETCH_ASK_DETAIL_LOADING:
            return {
                ...state,
                error: null,
                isLoading: true,
            };

        case FETCH_ASK_DETAIL_FAIL:
            return {
                ...state,
                isLoading: false,
                error: action.payload
            };
        case FETCH_MORE_ASK_DETAIL_SUCCESS:
            const oldDataSource = state.dataSource;
            const newDataSource = action.payload.edges.map((item) => item.node);

            const combine = _.concat(oldDataSource, newDataSource);


            return {
                ...state,
                isLoadingMore: false,
                dataSource: combine,
                pageInfo: action.payload.pageInfo
            };

        case FETCH_MORE_ASK_DETAIL_LOADING:
            return {
                ...state,
                error: null,
                moreError: null,
                isLoadingMore: true,
            };

        case FETCH_MORE_ASK_DETAIL_FAIL:
            return {
                ...state,
                isLoadingMore: false,
                moreError: action.payload
            };
        default:
            return state;
    }
}