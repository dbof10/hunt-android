import { NativeModules } from 'react-native';

const JobBridge = NativeModules.JobBridge;

export function navigateJobDetail(url) {
    JobBridge.navigateJobDetail(url)
}

export function navigateUser(id) {
    JobBridge.navigateUser(id)
}