import {NativeModules} from 'react-native';

const MeetupBridge = NativeModules.MeetupBridge;

export function navigateEventDetail(url) {
    MeetupBridge.navigateEventDetail(url)
}

export function shareEvent(url) {
    MeetupBridge.shareEvent(url)
}

export function addEventToCalendar(event) {
    MeetupBridge.addEventToCalendar(event)
}