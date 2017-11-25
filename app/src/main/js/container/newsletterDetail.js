import React, {Component} from 'react'
import {bindActionCreators} from 'redux';
import {BackHandler, Image, Linking, ScrollView, StyleSheet, Text, View} from 'react-native';
import {Load, NavigateBack} from '../action/newsletterDetail';
import {connect} from 'react-redux';
import Loading from '../component/loading'
import Error from '../component/error';
import TextStyle from '../style/textStyle';
import {getImageUrl} from "../util/stringUtils";
import HTMLView from 'react-native-htmlview';
import {colors} from "../style/resource"
import Button from "../widget/button";

class NewsLetterDetail extends Component {

    constructor(props) {
        super(props);
        this.onBackPress = this.onBackPress.bind(this);
    }

    onActionClick(url) {
        Linking.canOpenURL(url).then(supported => {
            if (!supported) {
                console.log('Can\'t handle url: ' + url);
            } else {
                return Linking.openURL(url);
            }
        }).catch(err => console.error('An error occurred', err));
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
        let letterId = this.props.navigation.state.params;
        this.props.load(letterId)
    }

    render() {
        const letterProps = this.props.newsletterDetail;
        let letterId = this.props.navigation.state.params;

        if (letterProps.isLoading || letterProps.dataSource === null) {
            return <Loading/>;
        } else {

            if (letterProps.error) {

                return <Error
                    errorMessage={letterProps.error.errorMessage}
                    explainMessage={letterProps.error.explainMessage}
                    onRetry={() => {
                        this.props.load(letterId)
                    }}/>;

            } else {
                const newsletter = letterProps.dataSource;

                return (

                    <ScrollView
                        removeClippedSubviews={false}
                        contentContainerStyle={styles.root}
                        showsVerticalScrollIndicator={false}>

                        <View style={styles.verticalLayout}>

                            <Text style={[TextStyle.title, styles.title]}>
                                {
                                    newsletter.subject
                                }
                            </Text>

                            <View style={{backgroundColor: colors.white}}>
                                <Image style={styles.photo}
                                       source={{uri: getImageUrl(newsletter.sections[0].image_uuid, 196)}}/>
                                <View style={styles.body}>
                                    <HTMLView
                                        textComponentProps={{style: TextStyle.body}}
                                        value={newsletter.sections[0].content}
                                    />
                                    <Button
                                        onPress={this.onActionClick.bind(this, newsletter.sections[0].url)}
                                        style={{marginTop: 8, alignItems: 'center'}}
                                        text={newsletter.sections[0].title}/>

                                </View>
                            </View>

                            <View style={{
                                marginTop: 8,
                                backgroundColor: colors.white
                            }}>
                                <HTMLView
                                    textComponentProps={{style: TextStyle.body}}
                                    style={styles.body}
                                    value={newsletter.sections[1].content}
                                />
                            </View>

                        </View>


                    </ScrollView>
                )
            }
        }
    }
}

const styles = StyleSheet.create({

    title: {
        paddingLeft: 8,
        paddingRight: 8,
        backgroundColor: colors.white,
        paddingTop: 8,
        paddingBottom: 8,
        textAlign: 'center'

    },
    body: {
        paddingLeft: 16,
        paddingRight: 16,
        paddingTop: 8,
        paddingBottom: 8
    },

    verticalLayout: {
        flexDirection: 'column',
    },

    horizontalLayout: {
        flexDirection: 'row',
    },

    photo: {
        height: 188,
        resizeMode: 'contain'
    }

});


function mapStateToProps(state) {
    const newsletterDetail = state.newsletterDetail;
    return {
        newsletterDetail,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        load: bindActionCreators(Load, dispatch),
        navigateBack: bindActionCreators(NavigateBack, dispatch)
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(NewsLetterDetail);