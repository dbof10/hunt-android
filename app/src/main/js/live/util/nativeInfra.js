import { NativeModules } from 'react-native';

const AppSettings = NativeModules.AppSettings;

export async function getClientToken() {
    let {token} = await AppSettings.getClientToken();
    return token
}

export async function getUserToken() {
    let {token} = AppSettings.getUserToken();
    return token
}