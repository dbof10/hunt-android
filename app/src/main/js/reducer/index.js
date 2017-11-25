import {combineReducers} from 'redux'
import event from './event'
import job from './job'
import meetup from './meetup'

export default combineReducers({
    event, job, meetup
});
