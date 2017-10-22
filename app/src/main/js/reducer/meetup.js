import {
    FETCH_MEETUP_SUCCESS, FETCH_MEETUP_LOADING, FETCH_MEETUP_FAIL,
} from '../action/meetup'

const initialState = {
    isLoading: false,
    error: null,
    dataSource: []
};

export default function meetup(state = initialState, action) {
    switch (action.type) {
        case FETCH_MEETUP_SUCCESS:
            return {
                ...state,
                isLoading: false,
                dataSource: action.payload.edges.map((item) => item.node),
            };

        case FETCH_MEETUP_LOADING:
            return {
                ...state,
                error: null,
                isLoading: true,
            };

        case FETCH_MEETUP_FAIL:
            return {
                ...state,
                isLoading: false,
                error: action.payload
            };
        default:
            return state;
    }
}