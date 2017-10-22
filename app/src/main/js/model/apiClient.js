/**
 * Created by daniel on 8/5/17.
 */

import axios from "axios";
import Rx from "rxjs";
import 'rxjs/add/operator/mergeMap';
import {getClientToken, getUserToken} from "../util/nativeInfra"
import * as queryBuilder from '../util/graphqlRequetsFactory'


const clientToken = Rx.Observable.fromPromise(getClientToken());
const userToken = Rx.Observable.fromPromise(getUserToken());
const TIME_OUT = 20000;

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
            timeout: TIME_OUT,
            headers: {
                'Accept': 'application/json',
                "Authorization": `Bearer ${token}`
            }
        }
    });

const instance = axios.create({
    baseURL: 'https://www.producthunt.com',
    timeout: TIME_OUT,
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

export function getJobs(offset) {
    let query = queryBuilder.buildJobRequest(offset);
    return Rx.Observable.fromPromise(instance.post('/frontend/graphql', query))
        .map(response => response.data.data)
}

export function getMeetups(type) {
    let query = queryBuilder.buildMeetupRequest(type);
    return Rx.Observable.fromPromise(instance.post('/frontend/graphql', query))
        .map(response => response.data.data)
}