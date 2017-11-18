import React, {Component} from 'react'
import {StyleSheet, Text, TouchableNativeFeedback, View} from 'react-native'
import PropTypes from 'prop-types';
import {colors} from "../style/resource";


export default class Button extends Component {

    constructor(props) {
        super(props)
    }

    render() {
        return (
            <View style={this.props.style}>
                <TouchableNativeFeedback
                    disabled={this.props.disabled}
                    background={TouchableNativeFeedback.SelectableBackground()}
                    onPress={this.props.onPress}>
                    <Text
                        style={styles.container}>
                        {
                            this.props.text.toUpperCase()
                        }
                    </Text>
                </TouchableNativeFeedback>
            </View>
        )
    }
}


Button.propTypes = {
    onPress: PropTypes.func,
    text: PropTypes.string,
    style: PropTypes.object,
    disabled: PropTypes.bool
};

const styles = StyleSheet.create({
        container: {
            elevation: 4,
            height: 38,
            backgroundColor: colors.accent,
            color: colors.white,
            paddingLeft: 8,
            paddingRight: 8,
            textAlign: 'center',
            textAlignVertical: 'center',
            borderRadius: 2,
            maxWidth: 96
        },
    });
