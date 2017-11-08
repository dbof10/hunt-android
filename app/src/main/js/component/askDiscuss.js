import React, {Component} from 'react';
import {Image, StyleSheet, Text, TouchableWithoutFeedback, View} from 'react-native';
import {getAvatarUrl} from "../util/stringUtils";
import TextStyle from '../style/textStyle'
import PropTypes from 'prop-types';
import Styles from '../style/style';
import {colors} from "../style/resource";
import HTMLView from 'react-native-htmlview';
import ParsedText from 'react-native-parsed-text';

export default class AskDiscussItem extends Component {

    renderHtml = (node) => {
        return (
            <ParsedText parse={
                [
                    {pattern: /@(\w+)/, style: TextStyle.mention},
                    {pattern: /#(\w+)/, style: TextStyle.mention}
                ]
            } style={TextStyle.body}>
                {node.children}
            </ParsedText>);
    };


    render() {
        const discuss = this.props.discuss;
        const user = discuss.user;
        const replies = discuss.replies.edges.map(item => item.node);
        const repliesView = replies.length > 0 ? <DiscussComment payload={replies}/> : null;

        return (
            <View style={styles.verticalLayout}>
                <View style={styles.mainContainer}>
                    <View style={styles.horizontalLayout}>
                        <TouchableWithoutFeedback>
                            <Image style={styles.avatar}
                                   source={{uri: getAvatarUrl(user.id, 64)}}
                            />
                        </TouchableWithoutFeedback>

                        <View style={[styles.verticalLayout, {marginLeft: 8}]}>
                            <Text style={TextStyle.userName}>
                                {user.name}
                            </Text>
                            <Text style={TextStyle.tagline}>
                                {user.headline}
                            </Text>
                        </View>
                    </View>

                    <HTMLView
                        TextComponent={this.renderHtml}
                        textComponentProps={{style: TextStyle.body}}
                        style={styles.body}
                        value={discuss.body}
                    />

                    <View style={Styles.separator}/>

                    <View style={[styles.horizontalLayout, styles.footer]}>
                        <View style={[styles.horizontalLayout, styles.footerAction]}>

                            <Image style={styles.footerIcon}
                                   source={{uri: 'ic_like'}}/>

                            <Text style={styles.indicator}>
                                {discuss.votes_count}
                            </Text>
                        </View>

                        <TouchableWithoutFeedback>

                            <Image style={[styles.footerAction, styles.footerIcon]}
                                   source={{uri: 'ic_comment'}}/>

                        </TouchableWithoutFeedback>

                    </View>

                </View>

                {repliesView}

            </View>
        )
    }
}


class DiscussComment extends Component {

    renderPage = (item, index) => (

        <View style={[styles.horizontalLayout, styles.commentContainer]}
              key={index}
        >
            <Image style={styles.avatar}
                   source={{uri: getAvatarUrl(item.user.id, 64)}}
            />

            <View style={[styles.verticalLayout, {marginLeft: 8}]}>
                <Text style={TextStyle.userName}>
                    {item.user.name}
                </Text>
                <ParsedText numberOfLines={4}
                            parse={
                                [
                                    {pattern: /@(\w+)/, style: TextStyle.mention},
                                    {pattern: /#(\w+)/, style: TextStyle.mention}
                                ]
                            }
                            ellipsizeMode="tail">
                    {item.body}
                </ParsedText>
            </View>
        </View>
    );

    render() {
        return (
            this.renderPage(this.props.payload[0], 0)
        )
    }
}

AskDiscussItem.propTypes = {
    discuss: PropTypes.object,
    onActionClick: PropTypes.func
};

DiscussComment.propTypes = {
    payload: PropTypes.array,
};

const styles = StyleSheet.create({

    commentContainer: {
        backgroundColor: colors.commentSectionBackground,
        paddingTop: 8,
        paddingBottom: 8,
        paddingLeft: 16,
        paddingRight: 16,
    },

    mainContainer: {
        backgroundColor: 'white',
        paddingTop: 8,
        paddingBottom: 8,
        paddingLeft: 16,
        paddingRight: 16
    },

    verticalLayout: {
        flex: 1,
        flexDirection: 'column',
    },

    horizontalLayout: {
        flexDirection: 'row',
    },

    avatar: {
        resizeMode: 'contain',
        height: 36,
        width: 36,
        borderRadius: 36
    },

    body: {
        marginTop: 8
    },

    product: {
        resizeMode: 'contain',
        height: 64,
        width: 64,
    },

    indicator: {
        paddingLeft: 4,
        textAlignVertical: 'bottom'
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
});
