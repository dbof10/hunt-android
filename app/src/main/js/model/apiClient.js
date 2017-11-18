import axios from "axios";
import Rx from "rxjs";
import 'rxjs/add/operator/mergeMap';
import {getClientToken, getUserToken} from "../util/nativeInfra"
import {ApolloClient, createNetworkInterface, gql} from 'react-apollo';
import {QUERY_ASK, QUERY_ASK_DETAIL, QUERY_JOB, QUERY_MEETUP, QUERY_NEWS_LETTER} from "../query/index";

const clientToken = Rx.Observable.fromPromise(getClientToken());
const userToken = Rx.Observable.fromPromise(getUserToken());
const TIME_OUT = 20000;

const BASE_URL = 'https://www.producthunt.com';
const BASE_API_URL = 'https://api.producthunt.com';

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
            baseURL: BASE_API_URL,
            timeout: TIME_OUT,
            headers: {
                'Accept': 'application/json',
                "Authorization": `Bearer ${token}`
            }
        }
    });


const networkInterface = createNetworkInterface({
    uri: `${BASE_URL}/frontend/graphql`,
});

const apolloClient = new ApolloClient({
    networkInterface: networkInterface
});

const axiosInstance = axios.create({
    baseURL: BASE_URL,
    timeout: TIME_OUT,
});


export function getEvents(date, offset) {
    return configSource
        .mergeMap(
            (config) => {
                return Rx.Observable.fromPromise(axiosInstance.get(`/v1/live?newer=${date}&offset=${offset}`, config))
                    .map(response => response.data)
            }
        )

}

export function getJobs(offset) {
    return Rx.Observable.fromPromise(apolloClient.query({
        query: gql`${QUERY_JOB}`,
        variables: {
            skills: [],
            roles: [],
            locations: [],
            product_ids: [],
            promoted: true,
            cursor: offset
        },
        operationName: "JobsPage"
    }))
        .map(response => response.data)
}

export function getMeetups(type) {
    return Rx.Observable.fromPromise(apolloClient.query({
        query: gql`${QUERY_MEETUP}`,
        variables: {
            type: type
        },
        operationName: "MeetupsPage"
    }))
        .map(response => response.data)
}


export function getAsk(tab, offset) {
    let body = {
        query: QUERY_ASK,
        variables: {
            productRequestFilter: tab,
            cursor: offset
        },
        operationName: "ProductRequestsPage"
    };
    return Rx.Observable.fromPromise(axiosInstance.post('/frontend/graphql', body))
        .map(response => response.data.data)
}

export function getAskDetail(id, offset) {
    let body = {
        query: QUERY_ASK_DETAIL,
        variables: {
            id,
            recommendationLimit: 1,
            threadLimit: 20
        },
        operationName: "ProductRequestsPage"
    };
    return Rx.Observable.fromPromise(axiosInstance.post('/frontend/graphql', body))
        .map(response => response.data.data)
}

export function getNewsLetter(type, offset) {
    return Rx.Observable.fromPromise(apolloClient.query({
        query: gql`${QUERY_NEWS_LETTER}`,
        variables: {
            filter: type,
            cursor: offset
        },
        operationName: "NewslettersPage"
    }))
        .map(response => response.data)
}


export function subscribeNewsLetter(email, status) {
    let body = {
        email,
        status
    };
    return Rx.Observable.fromPromise(axiosInstance.post('/frontend/newsletter_subscriptions', body));
}