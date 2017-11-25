import React, {Component} from 'react';
import {
    AppRegistry
} from 'react-native';

import {Provider} from 'react-redux';
import configureStore from './store/configNewsLetterStore';
import AppNavigationState from './navigator/newsletter';

const store = configureStore();

class NewsLetters extends Component {

    render() {
        return (
            <Provider store={store}>
                <AppNavigationState/>
            </Provider>

        );
    }
}


AppRegistry.registerComponent('NewsLetters', () => NewsLetters);