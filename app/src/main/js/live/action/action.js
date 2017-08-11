/**
 * Created by daniel on 8/5/17.
 */

export const FETCH_EVENT_LOADING = 'FETCH_EVENT_LOADING';
export const FETCH_EVENT_SUCCESS = 'FETCH_EVENT_SUCCESS';
export const FETCH_EVENT_FAIL = 'FETCH_EVENT_FAILURE';

export const FETCH_MORE_EVENT_LOADING = 'FETCH_MORE_EVENT_LOADING';
export const FETCH_MORE_EVENT_SUCCESS = 'FETCH_MORE_EVENT_SUCCESS';
export const FETCH_MORE_EVENT_FAIL = 'FETCH_MORE_EVENT_FAILURE';

export const LOAD = 'Load';
export const LOAD_MORE = 'Load_More';

export const Load = (date) => ({date, type: LOAD});
export const LoadMore = (date) => ({date, type: LOAD_MORE});


export const LoadResult = (type, payload) => ({type, payload});