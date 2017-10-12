import React, {Component} from 'react';
import {
    AppRegistry
} from 'react-native';

import {Provider} from 'react-redux';
import configureStore from './store/configStore';
import Main from './container/index'

const store = configureStore();

class LiveEvent extends Component {

    render() {
        return (
            <Provider store={store}>
                <Main/>
            </Provider>

        );
    }
}


AppRegistry.registerComponent('LiveEvent', () => LiveEvent);