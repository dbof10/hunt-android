import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {addNavigationHelpers, StackNavigator} from 'react-navigation';
import NewsLetterDetailScreen from '../container/newsletterDetail';
import NewsLetterScreen from '../container/newsletterContainer';


export const AppNavigator = StackNavigator({
    NewsLetterDetail: {screen: NewsLetterDetailScreen},
    NewsLetter: {screen: NewsLetterScreen},
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
    nav: state.newsletterNav,
});


export default connect(mapStateToProps)(AppNavigationState);
