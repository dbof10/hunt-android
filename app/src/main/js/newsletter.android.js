import React, {Component} from 'react';
import {
    AppRegistry
} from 'react-native';

import {Provider} from 'react-redux';
import configureStore from './store/configStore';
import NewsLetter from './container/newsletterContainer';

const store = configureStore();

class NewsLetters extends Component {

    render() {
        return (
            <Provider store={store}>
                <NewsLetter/>
            </Provider>

        );
    }
}


AppRegistry.registerComponent('NewsLetters', () => NewsLetters);