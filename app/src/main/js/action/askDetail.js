export const FETCH_ASK_DETAIL_LOADING = 'FETCH_ASK_DETAIL_LOADING';
export const FETCH_ASK_DETAIL_SUCCESS = 'FETCH_ASK_DETAIL_SUCCESS';
export const FETCH_ASK_DETAIL_FAIL = 'FETCH_ASK_DETAIL_FAILURE';

export const FETCH_MORE_ASK_DETAIL_LOADING = 'FETCH_MORE_ASK_DETAIL_LOADING';
export const FETCH_MORE_ASK_DETAIL_SUCCESS = 'FETCH_MORE_ASK_DETAIL_SUCCESS';
export const FETCH_MORE_ASK_DETAIL_FAIL = 'FETCH_MORE_ASK_FAILURE';

export const NAVIGATE_BACK_ASK_SCREEN = 'Back_Ask_Detail_Screen';

export const LOAD = 'Load_Ask_Detail';
export const LOAD_MORE = 'Load_More_Ask_Detail';

export const Load = (id) => ({type: LOAD, id});
export const LoadMore = (id) => ({type: LOAD_MORE});
export const NavigateBack = () => ({type: NAVIGATE_BACK_ASK_SCREEN});
export const LoadResult = (type, payload) => ({type, payload});