import {combineReducers} from 'redux'
import event from './event'
import job from './job'
import meetup from './meetup'
import newsletter from './newsletter'

export default combineReducers({
    event, job, meetup, newsletter
});
