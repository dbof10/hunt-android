/**
 * Created by daniel on 8/5/17.
 */

import axios from "axios";
import Rx from "rxjs";
import 'rxjs/add/operator/mergeMap';
import {getClientToken, getUserToken} from "../util/nativeInfra"


const clientToken = Rx.Observable.fromPromise(getClientToken());
const userToken = Rx.Observable.fromPromise(getUserToken());

const configSource = Rx.Observable.combineLatest(
    clientToken,
    userToken, (client, user) => {
        if (user) {
            return user
        } else {
            return client
        }
    })
    .map(token => {
        return {
            baseURL: 'https://api.producthunt.com',
            timeout: 30000,
            headers: {
                'Accept': 'application/json',
                "Authorization": `Bearer ${token}`
            }
        }
    });

export function getEvents(date, offset) {
    return configSource
        .mergeMap(
            (config) => {
                return Rx.Observable.fromPromise(axios.get(`/v1/live?newer=${date}&offset=${offset}`, config))
                    .map(response => response.data)
            }
        )

}
