import React, {Component} from 'react';
import {
    AppRegistry
} from 'react-native';

import {Provider} from 'react-redux';
import configureStore from './store/configStore';
import Meetup from './container/meetup';

const store = configureStore();

class Meetups extends Component {

    render() {
        return (
            <Provider store={store}>
                <Meetup/>
            </Provider>

        );
    }
}


AppRegistry.registerComponent('Meetups', () => Meetups);