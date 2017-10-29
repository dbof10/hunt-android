import React, {Component} from 'react'

import {bindActionCreators} from 'redux';
import {BackHandler, FlatList, ScrollView, View, StyleSheet, Text, Image} from 'react-native';
import {Load, NavigateBack} from '../action/askDetail';
import {connect} from 'react-redux';
import Loading from '../component/loading'
import Error from '../component/error';
import AskRecommendItem from '../component/askRecommend';
import Styles from '../style/style';
import TextStyle from '../style/textStyle';
import {getAvatarUrl} from "../util/stringUtils";
import {formatRelativeDate} from "../util/dateUtils";


class AskDetail extends Component {

    constructor(props) {
        super(props);
        this.onBackPress = this.onBackPress.bind(this)
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

    renderItem = ({item, index}) => (
        <AskRecommendItem
        />
    );

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
                return (

                    <ScrollView>
                        <View style={styles.verticalLayout}>
                            <Text
                                style={[TextStyle.title, styles.title]}>
                                {ask.title}
                            </Text>

                            <Text>
                                {ask.body_html}
                            </Text>
                        </View>

                        <View style={[styles.horizontalLayout, {alignItems: 'center'}]}>

                            <Image
                                style={styles.avatar}
                                source={{uri: getAvatarUrl(user.id, 64)}}/>

                            <View style={styles.verticalLayout}>
                                <Text style={styles.name}>
                                    {
                                        user.username
                                    }
                                </Text>

                                <Text style={[styles.subInfo, TextStyle.subInfo]}>
                                    {
                                        formatRelativeDate(ask.created_at)
                                    }
                                </Text>
                            </View>

                        </View>

                        <FlatList
                            showsVerticalScrollIndicator={false}
                            ItemSeparatorComponent={() => (
                                <View style={Styles.divider}/>
                            )}
                            data={askProps.dataSource}
                            keyExtractor={this.keyExtractor}
                            renderItem={this.renderItem}
                            onEndReached={this.loadMore}
                            onEndReachedThreshold={4}
                        />
                    </ScrollView>
                )
            }
        }
    }
}

const styles = StyleSheet.create({

    container: {
        backgroundColor: 'white',
        paddingRight: 16,
        paddingLeft: 16,
        paddingTop: 8,
        paddingBottom: 8
    },

    verticalLayout: {
        flex: 1,
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
        height: 32,
        width: 32,
        borderRadius: 32
    },

    name: {
        marginLeft: 8,
        color: 'rgba(0, 0, 0, 0.87)'
    },

    title: {
        marginTop: 8
    },

    subInfo: {
        marginLeft: 8,
    },

    indicator: {
        marginLeft: 4,
        textAlignVertical: 'bottom'
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