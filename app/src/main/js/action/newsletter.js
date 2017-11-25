export const FETCH_LETTER_LOADING = 'FETCH_LETTER_LOADING';
export const FETCH_LETTER_SUCCESS = 'FETCH_LETTER_SUCCESS';
export const FETCH_LETTER_FAIL = 'FETCH_LETTER_FAILURE';

export const FETCH_MORE_LETTER_LOADING = 'FETCH_MORE_LETTER_LOADING';
export const FETCH_MORE_LETTER_SUCCESS = 'FETCH_MORE_LETTER_SUCCESS';
export const FETCH_MORE_LETTER_FAIL = 'FETCH_MORE_LETTER_FAILURE';

export const SUBSCRIBE_LETTER_SUCCESS = 'SUBSCRIBE_LETTER_SUCCESS';
export const SUBSCRIBE_LETTER_FAILURE = 'SUBSCRIBE_LETTER_FAILURE';

export const NAVIGATE_LETTER_DETAIL_SCREEN = 'NewsLetter_Detail_Screen';

export const LOAD = 'Load_Letter';
export const LOAD_MORE = 'Load_More_Letter';
export const SUBSCRIBE = 'Subscribe_Letter';

export const Load = (key, tab) => ({key, type: LOAD, tab});
export const LoadMore = (key, tab) => ({key, type: LOAD_MORE, tab});
export const LoadResult = (key, type, payload) => ({key, type, payload});
export const Subscribe = (email, status) => ({type: SUBSCRIBE, email, status});
export const NavigateDetail = (payload) => ({type: NAVIGATE_LETTER_DETAIL_SCREEN, payload});
