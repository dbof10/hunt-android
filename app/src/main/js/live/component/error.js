import React, {Component} from 'react';
import {Image, StyleSheet, Text, TouchableWithoutFeedback, View} from 'react-native';
import TextStyle from '../style/textStyle';

export default class Error extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <TouchableWithoutFeedback
                onPress={this.props.onRetry}>
                <View style={styles.root}>
                    <Image
                        resizeMode='contain'
                        source={{uri: 'network_error'}}
                        style={{width: 160, height: 160, marginTop: 32}}/>
                    <Text style={[{marginTop: 24}, TextStyle.actionSecondary]}>{`Unexpected error`}</Text>
                    <Text style={[{marginTop: 8}, TextStyle.actionSecondary]}>{`Tap to retry`}</Text>

                </View>
            </TouchableWithoutFeedback>
        );
    }
}

const styles = StyleSheet.create({
    root: {
        alignItems: 'center',
        justifyContent: 'center',
        flex: 1
    },

});

Error.propTypes = {
    onRetry: React.PropTypes.func,
    errorMessage: React.PropTypes.string,
    explainMessage: React.PropTypes.string
};