import React, {Component} from 'react';
import {StyleSheet} from 'react-native';
import PropTypes from 'prop-types';
import {View, Text} from 'react-native'
import {getMonth, getDate} from "../util/dateUtils";

export default class DateView extends Component {

    constructor(props) {
        super(props)
    }

    render() {
        let date = new Date(this.props.date);

        return (
            <View style={styles.container}>
                <Text style={styles.month}>{getMonth(date)}</Text>
                <Text style={styles.date}>{getDate(date)}</Text>
            </View>)
    }

}

DateView.propTypes = {
    date: PropTypes.string,
};

const styles = StyleSheet.create({

    container: {
        flexDirection: 'column',
        borderRadius: 4,
        borderWidth: 0.5,
        borderColor: '#B6B6B6',
    },

    month: {
        textAlign: 'center',
        color: 'white',
        backgroundColor: '#D32F2F',
        paddingLeft: 4,
        paddingRight: 4
    },

    date: {
        textAlign: 'center',
        color: 'black',
        fontSize: 18,
        fontWeight: 'bold'
    }
});