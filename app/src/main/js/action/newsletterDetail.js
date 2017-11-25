
export const FETCH_LETTER_DETAIL_LOADING = 'FETCH_LETTER_DETAIL_LOADING';
export const FETCH_LETTER_DETAIL_SUCCESS = 'FETCH_LETTER_DETAIL_SUCCESS';
export const FETCH_LETTER_DETAIL_FAIL = 'FETCH_LETTER_DETAIL_FAILURE';

export const NAVIGATE_BACK_LETTER_SCREEN = 'Back_Letter_Detail_Screen';

export const LOAD = 'Load_Letter_Detail';

export const Load = (id) => ({type: LOAD, id});
export const LoadResult = (type, payload) => ({type, payload});
export const NavigateBack = () => ({type: NAVIGATE_BACK_LETTER_SCREEN});