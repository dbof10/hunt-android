import {NativeModules} from 'react-native';
import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {addNavigationHelpers, StackNavigator} from 'react-navigation';
import AskDetailScreen from '../container/askDetail';
import AskScreen from '../container/ask';


const AskBridge = NativeModules.AskBridge;

export function shareUrl(url) {
    AskBridge.shareUrl(url)
}

export const AppNavigator = StackNavigator({
    AskDetail: {screen: AskDetailScreen},
    Ask: {screen: AskScreen},
}, {
    headerMode: 'none'
});

const AppNavigationState = ({dispatch, nav}) => (
    <AppNavigator navigation={
        addNavigationHelpers({dispatch, state: nav})
    }/>
);

AppNavigationState.propTypes = {
    dispatch: PropTypes.func.isRequired,
    nav: PropTypes.object.isRequired,
};

const mapStateToProps = state => ({
    nav: state.askNav,
});


export default connect(mapStateToProps)(AppNavigationState);
