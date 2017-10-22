export const FETCH_MEETUP_LOADING = 'FETCH_MEETUP_LOADING';
export const FETCH_MEETUP_SUCCESS = 'FETCH_MEETUP_SUCCESS';
export const FETCH_MEETUP_FAIL = 'FETCH_MEETUP_FAILURE';

export const LOAD = 'Load_Meetup';

export const Load = (payload) => ({type: LOAD, payload});

export const LoadResult = (type, payload) => ({type, payload});