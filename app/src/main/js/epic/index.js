import {combineEpics} from 'redux-observable';
import {fetchEventEpic, fetchMoreEventEpic} from './live'
import {fetchJobEpic, fetchMoreJobEpic} from './job'
import {fetchMeetupEpic} from "./meetup";
import {fetchAskEpic, fetchMoreAskEpic} from './ask'
import {fetchAskDetailEpic, fetchMoreRecommendAskEpic} from "./askDetail";
import {fetchMoreNewsLetterEpic, fetchNewsLetterEpic, subscribeNewsLetterEpic} from "./newsletter";

export const epic = combineEpics(
    fetchEventEpic, fetchMoreEventEpic,
    fetchJobEpic, fetchMoreJobEpic,
    fetchMeetupEpic,
    fetchAskEpic, fetchMoreAskEpic,
    fetchAskDetailEpic, fetchMoreRecommendAskEpic,
    fetchNewsLetterEpic, fetchMoreNewsLetterEpic,subscribeNewsLetterEpic
);