export const FETCH_ASK_LOADING = 'FETCH_ASK_LOADING';
export const FETCH_ASK_SUCCESS = 'FETCH_ASK_SUCCESS';
export const FETCH_ASK_FAIL = 'FETCH_ASK_FAILURE';

export const FETCH_MORE_ASK_LOADING = 'FETCH_MORE_ASK_LOADING';
export const FETCH_MORE_ASK_SUCCESS = 'FETCH_MORE_ASK_SUCCESS';
export const FETCH_MORE_ASK_FAIL = 'FETCH_MORE_ASK_FAILURE';

export const NAVIGATE_ASK_DETAIL_SCREEN = 'Ask_Detail_Screen';

export const LOAD = 'Load_Ask';
export const LOAD_MORE = 'Load_More_Ask';

export const Load = (tab) => ({type: LOAD, tab});
export const LoadMore = (tab) => ({type: LOAD_MORE});
export const NavigateDetail = (payload) => ({type: NAVIGATE_ASK_DETAIL_SCREEN, payload});

export const LoadResult = (type, payload) => ({type, payload});