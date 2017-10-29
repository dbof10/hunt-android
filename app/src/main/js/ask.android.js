import React, {Component} from 'react';
import {AppRegistry} from 'react-native';

import {Provider} from 'react-redux';
import configAskStore from './store/configAskStore';
import AppNavigationState from './navigator/ask';

const store = configAskStore();

class Ask extends Component {

    render() {
        return (
            <Provider store={store}>
                <AppNavigationState/>
            </Provider>

        );
    }
}


AppRegistry.registerComponent('Ask', () => Ask);