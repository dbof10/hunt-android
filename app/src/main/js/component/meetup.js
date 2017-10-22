import React, {Component} from 'react';
import {Image, StyleSheet, Text, TouchableNativeFeedback, View} from 'react-native';
import PropTypes from 'prop-types';
import {getAvatarUrl} from "../util/stringUtils";
import * as navigator from '../navigator/meetup';
import DateView from './dataview'
import {Menu, MenuOption, MenuOptions, MenuTrigger,} from 'react-native-popup-menu';

import TextStyle from '../style/textStyle';

const MENU_ID_SHARE = 10001;
const MENU_ID_ADD_CALENDAR = 10002;

export default class MeetupItem extends Component {

    constructor(props) {
        super(props);
    }

    onItemClick() {
        let url = this.props.meetup.event_url;
        navigator.navigateEventDetail(url)
    }

    onMenuItemClick(itemId) {
        const meetup = this.props.meetup;
        switch (itemId) {
            case MENU_ID_SHARE:
                navigator.shareEvent(meetup.event_url);
                break;
            case MENU_ID_ADD_CALENDAR:
                navigator.addEventToCalendar(meetup);
                break
        }
    };

    renderUser(users) {
        return (
            <View>
                <Text style={styles.subInfo}>
                    hosted by
                </Text>
                <View contentContainerStyle={styles.avatarContainer}
                      horizontal={true}>
                    {
                        users.map(item =>
                            <Image
                                key={item.id}
                                style={styles.avatar}
                                source={{uri: getAvatarUrl(item.id, 64)}}/>
                        )
                    }


                </View>
            </View>)
    }

    render() {
        return (

            <TouchableNativeFeedback
                onPress={this.onItemClick}
                background={TouchableNativeFeedback.SelectableBackground()}
            >
                <View style={styles.container}>

                    <View style={styles.iconHolder}>
                        <DateView
                            date={this.props.meetup.event_date}/>
                    </View>

                    <View style={{
                        flex: 1,
                        flexDirection: 'column',
                        marginLeft: 16
                    }}>
                        <Text style={styles.title}>
                            {this.props.meetup.title}
                        </Text>

                        <Text style={styles.tagline}>
                            {this.props.meetup.description}
                        </Text>
                        <View style={styles.info}>
                            {this.renderUser(this.props.meetup.hosts)}
                        </View>
                    </View>

                    <View>
                        <Menu>
                            <MenuTrigger
                                customStyles={{
                                    triggerTouchable: {background: TouchableNativeFeedback.SelectableBackgroundBorderless()},
                                }}
                            >
                                <Image
                                    style={styles.menuIcon}
                                    source={{uri: 'ic_more_vert_gray'}}/>
                            </MenuTrigger>
                            <MenuOptions>
                                <MenuOption onSelect={this.onMenuItemClick.bind(this, MENU_ID_ADD_CALENDAR)}
                                            value={TouchableNativeFeedback}>
                                    <Text style={[styles.menuItem, TextStyle.textColorPrimary]}>Add to calendar</Text>
                                </MenuOption>

                                <MenuOption onSelect={this.onMenuItemClick.bind(this, MENU_ID_SHARE)}
                                            value={TouchableNativeFeedback}>
                                    <Text style={[styles.menuItem, TextStyle.textColorPrimary]}>Share</Text>
                                </MenuOption>
                            </MenuOptions>
                        </Menu>

                    </View>

                </View>
            </TouchableNativeFeedback>
        )
    }
}

MeetupItem.propTypes = {
    meetup: PropTypes.object,
};

const styles = StyleSheet.create({

    container: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: 'white',
        paddingTop: 8,
        paddingBottom: 8,
    },

    iconHolder: {
        justifyContent: 'center',
        marginLeft: 16
    },

    menuIcon: {
        width: 24,
        height: 24,
        marginRight: 8
    },

    menuItem: {
        paddingTop: 8,
        paddingBottom: 8,
        marginLeft: 8
    },

    info: {
        marginTop: 4
    },

    tagline: {
        marginTop: 4
    },

    avatarContainer: {
        flex: 1,
        marginTop: 8
    },

    avatar: {
        marginRight: 4,
        resizeMode: 'contain',
        height: 32,
        width: 32,
        borderRadius: 32
    },

    title: {
        fontSize: 18,
        color: 'rgba(0, 0, 0, 0.87)'
    },

    subInfo: {
        marginTop: 4,
        fontSize: 12,
        color: 'rgba(0, 0, 0, 0.26)'
    }
});