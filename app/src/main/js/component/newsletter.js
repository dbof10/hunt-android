import React, {Component} from 'react';
import {Image, StyleSheet, Text, View} from 'react-native';
import PropTypes from 'prop-types';
import TextStyle from '../style/textStyle';
import {getImageUrl} from "../util/stringUtils";


export default class NewsLetterItem extends Component {

    constructor(props) {
        super(props);
    }


    render() {
        const newsletter = this.props.newsletter;

        return (
            <View style={[styles.container, styles.verticalLayout]}>
                <Text style={TextStyle.body}>
                    {newsletter.subject}
                </Text>
                <Image style={styles.photo}
                       source={{uri: getImageUrl(newsletter.primary_section.image_uuid, 256)}}
                />
                <View style={styles.subInfo}>
                    <Text style={TextStyle.subInfo}>
                        {newsletter.date}
                    </Text>
                </View>
            </View>
        )
    }
}

NewsLetterItem.propTypes = {
    newsletter: PropTypes.object,
};

const styles = StyleSheet.create({

        container: {
            flex: 1,
            paddingLeft: 16,
            paddingRight: 16,
            backgroundColor: 'white',
            paddingTop: 8,
            paddingBottom: 8,
        },

        photo: {
            resizeMode: 'contain',
            height: 188,
            marginTop: 4
        },

        subInfo: {
            alignItems: 'flex-end',
            justifyContent: 'flex-end'
        }
    })
;