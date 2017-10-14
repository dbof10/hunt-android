
export const FETCH_JOB_LOADING = 'FETCH_JOB_LOADING';
export const FETCH_JOB_SUCCESS = 'FETCH_JOB_SUCCESS';
export const FETCH_JOB_FAIL = 'FETCH_JOB_FAILURE';

export const FETCH_MORE_JOB_LOADING = 'FETCH_MORE_JOB_LOADING';
export const FETCH_MORE_JOB_SUCCESS = 'FETCH_MORE_JOB_SUCCESS';
export const FETCH_MORE_JOB_FAIL = 'FETCH_MORE_JOB_FAILURE';

export const LOAD = 'Load_Job';
export const LOAD_MORE = 'Load_More_Job';

export const Load = () => ({type: LOAD});
export const LoadMore = () => ({type: LOAD_MORE});


export const LoadResult = (type, payload) => ({type, payload});