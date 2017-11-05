import React, {Component} from 'react';
import {Image, StyleSheet, Text, TouchableWithoutFeedback, View} from 'react-native';
import PropTypes from 'prop-types';
import {getAvatarUrl, trimHtml} from "../util/stringUtils";
import TextStyle from '../style/textStyle';
import {formatRelativeDate} from "../util/dateUtils";
import * as navigator from '../navigator/ask';
import Styles from '../style/style';

const ACTION_SHARE = "SHARE";
const ACTION_NAVIGATE = "NAVIGATE";

export default class AskItem extends Component {

    constructor(props) {
        super(props);
    }

    onActionClick(action) {
        const ask = this.props.ask;

        switch (action) {
            case ACTION_SHARE:
                navigator.shareUrl(ask.url);
                break;
            case ACTION_NAVIGATE:
                this.props.onNavigate(ask._id);
                break;
        }
    }

    render() {

        const ask = this.props.ask;
        const user = ask.user !== null ? ask.user : {username: 'Anonymous'};
        const recommendedCount = ask.recommended_products_count;
        const recommendOrAnswer = recommendedCount > 0 ? recommendedCount : ask.comments_count;
        const recommendOrAnswerIcon = recommendedCount > 0 ? 'ic_recommend' : 'ic_answer';
        return (

            <View style={[styles.verticalLayout, styles.container]}>

                <View style={[styles.horizontalLayout, {alignItems: 'center'}]}>

                    <Image
                        style={styles.avatar}
                        source={{uri: getAvatarUrl(user.id, 64)}}/>

                    <View style={styles.verticalLayout}>
                        <Text style={[TextStyle.userName, {marginLeft: 8}]}>
                            {user.name}
                        </Text>

                        <Text style={[styles.subInfo, TextStyle.subInfo]}>
                            {formatRelativeDate(ask.created_at)}
                        </Text>
                    </View>

                </View>


                <Text
                    onPress={this.onActionClick.bind(this, ACTION_NAVIGATE)}
                    style={[TextStyle.title, styles.title]}
                    numberOfLines={2}
                    ellipsizeMode="tail">
                    {ask.title}
                </Text>


                <Text numberOfLines={3}
                      ellipsizeMode="tail">
                    {trimHtml(ask.body_html)}
                </Text>

                <View style={Styles.separator}/>

                <View style={[styles.horizontalLayout, styles.footer]}>
                    <View style={[styles.horizontalLayout, styles.footerAction]}>

                        <Image style={styles.footerIcon}
                               source={{uri: recommendOrAnswerIcon}}/>

                        <Text style={styles.indicator}>
                            {recommendOrAnswer}
                        </Text>
                    </View>

                    <TouchableWithoutFeedback
                        onPress={this.onActionClick.bind(this, ACTION_SHARE)}>

                        <Image style={[styles.footerAction, styles.footerIcon]}
                               source={{uri: 'ic_share_home'}}/>

                    </TouchableWithoutFeedback>

                </View>

            </View>
        )
    }
}

AskItem.propTypes = {
    ask: PropTypes.object,
    onNavigate: PropTypes.func
};

const styles = StyleSheet.create({

    container: {
        backgroundColor: 'white',
        paddingRight: 16,
        paddingLeft: 16,
        paddingTop: 8,
        paddingBottom: 8
    },

    verticalLayout: {
        flexDirection: 'column',
    },

    horizontalLayout: {
        flexDirection: 'row',
    },

    footer: {
        marginTop: 4,
    },

    footerAction: {
        flex: 1,
        justifyContent: 'center'
    },

    footerIcon: {
        resizeMode: 'contain',
        height: 20,
        width: 20,
    },

    avatar: {
        resizeMode: 'contain',
        height: 36,
        width: 36,
        borderRadius: 36
    },

    title: {
        marginTop: 8
    },

    subInfo: {
        marginLeft: 8,
    },

    indicator: {
        paddingLeft: 4,
        textAlignVertical: 'bottom'
    },

});