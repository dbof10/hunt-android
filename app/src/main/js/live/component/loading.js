import React, {Component} from 'react';
import {View, ActivityIndicator, StyleSheet} from 'react-native';

export default class LoadingView extends Component {

    constructor(props) {
        super(props)
    }

    render() {
        return (
            <View style={styles.root}>
                <ActivityIndicator
                    animating={true}
                    size="large"
                />
            </View>
        );
    }
}

const styles = StyleSheet.create({

    root: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    }

});