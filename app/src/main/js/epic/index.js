

import { combineEpics } from 'redux-observable';
import {fetchEventEpic, fetchMoreEventEpic} from './live'
import {fetchJobEpic, fetchMoreJobEpic} from './job'

export const epic = combineEpics(
    fetchEventEpic, fetchMoreEventEpic,
    fetchJobEpic, fetchMoreJobEpic
);