import React, {Component} from 'react'

import {bindActionCreators} from 'redux';
import {BackHandler, FlatList, ScrollView, View, StyleSheet, Text, Image} from 'react-native';
import {Load, NavigateBack} from '../action/askDetail';
import {connect} from 'react-redux';
import Loading from '../component/loading'
import Error from '../component/error';
import AskRecommendItem, {ACTION_NAVIGATE} from '../component/askRecommend';
import AskDiscussItem from '../component/askDiscuss';
import Styles from '../style/style';
import TextStyle from '../style/textStyle';
import {getAvatarUrl} from "../util/stringUtils";
import {formatRelativeDate} from "../util/dateUtils";
import HTMLView from 'react-native-htmlview';
import {navigateProduct} from "../navigator/ask";

const TYPE_DISCUSS = 'Discuss';
const TYPE_RECOMMEND = 'Recommend';
const TYPE_UNKNOWN = 'Unknown';

class AskDetail extends Component {

    constructor(props) {
        super(props);
        this.onBackPress = this.onBackPress.bind(this);
        this.onActionClick = this.onActionClick.bind(this);
        this.type = TYPE_UNKNOWN;
    }

    onActionClick(action) {

        switch (action.type) {
            case ACTION_NAVIGATE:
                navigateProduct(action.payload);
                break;
        }
    }

    componentWillUnmount() {
        BackHandler.removeEventListener("hardwareBackPress", this.onBackPress);
    }

    onBackPress() {
        this.props.navigateBack();
        return true
    }

    componentDidMount() {
        BackHandler.addEventListener("hardwareBackPress", this.onBackPress);
        let askId = this.props.navigation.state.params;
        this.props.load(askId)

    }

    keyExtractor = (item, index) => item.id;

    renderItem = ({item, index}) => {
        if (this.type === TYPE_RECOMMEND)
            return (
                <AskRecommendItem
                    id={item.id}
                    recommend={item}
                    onActionClick={this.onActionClick}/>);
        else if (this.type === TYPE_DISCUSS) {
            return (
                <AskDiscussItem
                    id={item.id}
                    discuss={item}/>
            );
        } else {
            return <View/>
        }
    };

    render() {
        const askProps = this.props.ask;

        if (askProps.isLoading || askProps.dataSource === null) {
            return <Loading/>;
        } else {

            if (askProps.error) {

                return <Error
                    errorMessage={askProps.error.errorMessage}
                    explainMessage={askProps.error.explainMessage}
                    onRetry={() => {
                        this.props.load()
                    }
                    }/>;

            } else {
                const ask = askProps.dataSource;
                const user = ask.user !== null ? ask.user : {username: 'Anonymous'};
                this.type = ask.is_advice ? TYPE_DISCUSS : TYPE_RECOMMEND;

                const listSource = this.type === TYPE_RECOMMEND ?
                    ask.recommended_products.edges.map(item => item.node) :
                    ask.threads.edges.map(item => item.node);

               const itemCountView = this.type === TYPE_RECOMMEND ?
                   <Text>{`${ask.recommended_products_count} Recommendations`} </Text> :
                   <Text>{`${ask.comments_count} Comments`} </Text>;

                return (

                    <ScrollView
                        removeClippedSubviews={false}
                        contentContainerStyle={styles.root}
                        showsVerticalScrollIndicator={false}>

                        <View style={styles.verticalLayout}>
                            <View style={styles.headerContainer}>
                                <Text
                                    style={TextStyle.title}>
                                    {ask.title}
                                </Text>

                                <HTMLView
                                    style={styles.body}
                                    value={ask.body_html}
                                />


                                <View style={[styles.horizontalLayout,
                                    {
                                        justifyContent: 'flex-end',
                                        marginTop: 8
                                    }]}>

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

                                {
                                    itemCountView
                                }
                                <View style={Styles.separator}/>

                            </View>

                            <FlatList
                                removeClippedSubviews={false}
                                showsVerticalScrollIndicator={false}
                                ItemSeparatorComponent={() => (
                                    <View style={Styles.divider}/>
                                )}
                                data={listSource}
                                keyExtractor={this.keyExtractor}
                                renderItem={this.renderItem}
                                onEndReached={this.loadMore}
                                onEndReachedThreshold={4}
                            />
                        </View>

                    </ScrollView>
                )
            }
        }
    }
}

const styles = StyleSheet.create({

    root: {
        paddingBottom: 8
    },

    headerContainer: {
        backgroundColor: 'white',
        paddingRight: 16,
        paddingLeft: 16,
        paddingBottom: 8,
        paddingTop: 8
    },

    verticalLayout: {
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

    subInfo: {
        marginLeft: 8,
    },


});

function mapStateToProps(state) {
    const ask = state.askDetail;
    return {
        ask,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        load: bindActionCreators(Load, dispatch),
        navigateBack: bindActionCreators(NavigateBack, dispatch)
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(AskDetail);