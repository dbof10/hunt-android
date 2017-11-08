import React, {Component} from 'react';
import {Image, StyleSheet, Text, TouchableWithoutFeedback, View} from 'react-native';
import {getAvatarUrl, getImageUrl, trimHtml} from "../util/stringUtils";
import TextStyle from '../style/textStyle'
import PropTypes from 'prop-types';
import Styles from '../style/style';
import {colors, dimens} from "../style/resource";

export const ACTION_NAVIGATE = "NAVIGATE";

export default class AskRecommendItem extends Component {

    render() {
        const recommend = this.props.recommend;
        const posts = recommend.product.posts.edges.map(item => item.node);
        const description = posts.length > 0 ? posts[0].tagline : "";
        const descriptionView = description !== "" ? <Text style={[TextStyle.body, {
            paddingTop: dimens.space_medium,
            paddingBottom: dimens.space_medium
        }]}>
            {description}
        </Text> : null;

        const comments = recommend.recommendations.edges
            .map(item => item.node);

        const commentsView = comments.length > 0 ? <RecommendComment payload={comments}/> : null;

        return (
            <View style={styles.verticalLayout}>
                <View style={styles.mainContainer}>
                    <View style={styles.verticalLayout}>
                        <TouchableWithoutFeedback
                            onPress={this.props.onActionClick.bind(this, {
                                type: ACTION_NAVIGATE,
                                payload: recommend.product.id
                            })}>
                            <Image style={styles.product}
                                   source={{uri: getImageUrl(recommend.product.thumbnail_media.image_uuid, 384)}}
                            />
                        </TouchableWithoutFeedback>

                        {descriptionView}
                    </View>

                    <View style={Styles.separator}/>

                    <View style={[styles.horizontalLayout, styles.footer]}>
                        <View style={[styles.horizontalLayout, styles.footerAction]}>

                            <Image style={styles.footerIcon}
                                   source={{uri: 'ic_like'}}/>

                            <Text style={styles.indicator}>
                                {recommend.votes_count}
                            </Text>
                        </View>

                        <TouchableWithoutFeedback>

                            <Image style={[styles.footerAction, styles.footerIcon]}
                                   source={{uri: 'ic_comment'}}/>

                        </TouchableWithoutFeedback>

                    </View>

                </View>

                {commentsView}

            </View>
        )
    }
}


class RecommendComment extends Component {

    render() {
        const first = this.props.payload[0];
        const user = first.user;
        return (
            <View style={[styles.horizontalLayout, styles.commentContainer]}>
                <Image style={styles.avatar}
                       source={{uri: getAvatarUrl(user.id, 64)}}
                />

                <View style={[styles.verticalLayout, {marginLeft: 8}]}>
                    <Text style={TextStyle.userName}>
                        {user.name}
                    </Text>
                    <Text numberOfLines={4}
                          ellipsizeMode="tail">
                        {trimHtml(first.body_html)}
                    </Text>
                </View>
            </View>
        )
    }
}

AskRecommendItem.propTypes = {
    recommend: PropTypes.object,
    onActionClick: PropTypes.func
};

RecommendComment.propTypes = {
    payload: PropTypes.array,
};

const styles = StyleSheet.create({

    commentContainer: {
        backgroundColor: colors.commentSectionBackground,
        paddingTop: 8,
        paddingBottom: 8,
        paddingLeft: 16,
        paddingRight: 16
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

    product: {
        flex: 1,
        resizeMode: 'cover',
        height: 220,
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
