/**
 * Created by daniel on 8/5/17.
 */

import { combineEpics } from 'redux-observable';
import {fetchEventEpic, fetchMoreEventEpic} from './epic'

export const epic = combineEpics(
    fetchEventEpic, fetchMoreEventEpic
);