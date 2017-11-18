import React, {Component} from 'react'
import {Image, StyleSheet, TouchableWithoutFeedback, View} from 'react-native'
import PropTypes from 'prop-types';


export default class FloatingActionButton extends Component {

    constructor(props) {
        super(props)
    }

    render() {
        return (
            <TouchableWithoutFeedback
                onPress={this.props.onPress}>
                <View
                    style={[styles.container, {
                        backgroundColor: this.props.backgroundColor,
                        bottom: this.props.marginBottom,
                        right: this.props.marginRight
                    }, this.props.styles]}
                >
                    <Image
                        style={{width: 24, height: 24}}
                        source={{uri: this.props.iconRes}}
                    />
                </View>
            </TouchableWithoutFeedback>
        )
    }
}


FloatingActionButton.propTypes = {
    onPress: PropTypes.func,
    backgroundColor: PropTypes.string,
    iconRes: PropTypes.string,
    styles: PropTypes.object,
    marginBottom: PropTypes.number,
    marginRight: PropTypes.number
};

const styles = StyleSheet.create({
        container: {
            elevation: 4,
            borderRadius: 56,
            width: 56,
            height: 56,
            position: 'absolute',
            alignItems: 'center',
            justifyContent: 'center'
        },
    })
;
