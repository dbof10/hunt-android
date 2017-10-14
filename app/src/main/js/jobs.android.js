import React, {Component} from 'react';
import {
    AppRegistry
} from 'react-native';

import {Provider} from 'react-redux';
import configureStore from './store/configStore';
import Job from './container/job';

const store = configureStore();

class Jobs extends Component {

    render() {
        return (
            <Provider store={store}>
                <Job/>
            </Provider>

        );
    }
}


AppRegistry.registerComponent('Jobs', () => Jobs);