import React, {Component} from 'react';
import {
    Dimensions,
    Modal,
    StyleSheet,
    Text,
    TextInput,
    ToastAndroid,
    TouchableWithoutFeedback,
    View,
    ViewPagerAndroid
} from 'react-native';
import {Subscribe} from '../action/newsletter';
import {bindActionCreators} from 'redux';

import NewsLetter from './newsletter';
import FloatingActionButton from '../widget/floatingActionButton';
import {colors} from '../style/resource'
import Styles from '../style/style';
import TextStyle from '../style/textStyle';

import Button from '../widget/button';
import {connect} from 'react-redux';
import {isEmailValid} from "../util/stringUtils";
import TabbedViewPager from '../widget/tabbedViewPager';

const {height, width} = Dimensions.get('window');

class NewsLetterContainer extends Component {


    constructor(props) {
        super(props);
        this.state = {
            dialogShowing: false,
            dialogText: "",
            subscribeEnabled: false,
            inputError: "",
            tabs: ["DAILY", "WEEKLY"]
        };
        this.onSubscribeClick = this.onSubscribeClick.bind(this);
        this.onDialogDismiss = this.onDialogDismiss.bind(this);

    }

    onDialogDismiss() {
        this.setState({
            dialogShowing: false,
            subscribeEnabled: false,
            inputError: ""
        });
    }

    onSubscribeClick() {
        if (this.state.subscribeEnabled) {
            this.props.subscribe(this.state.dialogText, "Daily (Mon - Fri)");
            this.setDialogVisible(false)
        }
    }

    setDialogVisible(visible) {
        this.setState({dialogShowing: visible});
    }


    render() {
        const newsletter = this.props.newsletter;
        const floatingActionView = !newsletter.subscribed ?
            <FloatingActionButton
                onPress={this.setDialogVisible.bind(this, true)}
                marginBottom={16}
                marginRight={16}
                iconRes="ic_subscribe"
                backgroundColor={colors.accent}/> : null;
        const toast = newsletter.subscribed ? ToastAndroid.show(`You're subscribed`, ToastAndroid.SHORT) :
            newsletter.subscribedError !== null && !this.state.dialogShowing ?
                ToastAndroid.show(newsletter.subscribedError.toString(), ToastAndroid.SHORT) : null;

        const isValid = this.state.inputError === "";

        const inputError = !isValid ?
            <Text style={[TextStyle.error, {marginLeft: 4}]}>
                {this.state.inputError}
            </Text> : null;
        const underlineColor = isValid ? colors.accent : colors.primary;

        return (
            <View style={{flex: 1}}>
                <Modal
                    animationType="slide"
                    transparent={true}
                    visible={this.state.dialogShowing}
                    onRequestClose={this.onDialogDismiss}
                >
                    <TouchableWithoutFeedback
                        onPress={this.onDialogDismiss}>
                        <View style={Styles.dialogWindowBackground}>
                            <View style={[Styles.dialogBackground, styles.dialogBackground]}>

                                <TextInput
                                    onChangeText={(text) => {
                                        const valid = isEmailValid(text);
                                        const error = valid ? "" : "Invalid email";
                                        this.setState({
                                            dialogText: text,
                                            inputError: error,
                                            subscribeEnabled: isValid
                                        })
                                    }
                                    }
                                    underlineColorAndroid={underlineColor}
                                    onSubmitEditing={this.onSubscribeClick}
                                    returnKeyType='done'
                                    keyboardType='email-address'
                                    placeholder="Email"/>

                                {
                                    inputError
                                }

                                <Button
                                    onPress={this.onSubscribeClick}
                                    style={{marginTop: 8, alignItems: 'center'}}
                                    text="Subscribe"/>

                            </View>
                        </View>
                    </TouchableWithoutFeedback>
                </Modal>

                <TabbedViewPager
                    tabMode="fixed"
                    tabGravity="center"
                    tabBackground={colors.primary}
                    tabIndicatorColor={colors.white}
                    tabIndicatorHeight={4}
                    tabTextColor={colors.text_tertiary_dark}
                    tabSelectedTextColor={colors.white}
                    tabElevation={4}
                    tabNames={this.state.tabs}
                    style={{flex: 1}}>
                    <View key={0}>
                        <NewsLetter
                            index={0}
                            tab={newsletter.tabs[0]}
                            type={this.state.tabs[0]}
                        />
                    </View>
                    <View key={1}>
                        <NewsLetter
                            index={1}
                            tab={newsletter.tabs[1]}
                            type={this.state.tabs[1]}
                        />
                    </View>
                </TabbedViewPager>
                {
                    floatingActionView
                }
                {
                    toast
                }
            </View>
        )
    }

}

const styles = StyleSheet.create({

        dialogBackground: {
            marginTop: height / 3,
            marginRight: width / 6,
            marginLeft: width / 6,
            paddingRight: 16,
            paddingLeft: 16,
            paddingTop: 8,
            paddingBottom: 16,
        }
    })
;

function mapStateToProps(state) {
    const newsletter = state.newsletter;
    return {
        newsletter,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        subscribe: bindActionCreators(Subscribe, dispatch),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(NewsLetterContainer);