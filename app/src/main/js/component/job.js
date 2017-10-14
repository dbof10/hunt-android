import React, {Component} from 'react';
import {Image, StyleSheet, Text, TouchableNativeFeedback, View, ScrollView} from 'react-native';
import {formatDate} from "../util/dateUtils"
import PropTypes from 'prop-types';
import {formatUnit, getAvatarUrl, getImageUrl} from "../util/stringUtils";
import * as navigator from '../navigator/job';

export default class JobItem extends Component {

    constructor(props) {
        super(props);
        this.onJobClick = this.onJobClick.bind(this);
    }

    onJobClick(){
        let url = this.props.job.url;
        navigator.navigateJobDetail(url)
    }

    renderUser(users) {
        if (users.length > 0) {
            return (
                <View>
                    <ScrollView contentContainerStyle={styles.avatarContainer}
                                horizontal={true}>
                        {
                            users.map(item =>
                                <Image
                                    key={item.id}
                                    style={styles.avatar}
                                    source={{uri: getAvatarUrl(item.id, 64)}}/>
                            )
                        }


                    </ScrollView>
                    <Text style={styles.subInfo}>
                        working here
                    </Text>
                </View>)
        } else {
            return null
        }
    }

    render() {

        let displayCreatedAt;
        let createdAt = this.props.job.external_created_at;
        if (createdAt) {
            displayCreatedAt = `Posted on ${formatDate(createdAt)}`
        }

        let displaySalary;
        let salaryMax = this.props.job.salary_max;
        let salaryMin = this.props.job.salary_min;

        if (salaryMin || salaryMax) {
            displaySalary = `${formatUnit(salaryMin)} - ${formatUnit(salaryMax)} ${this.props.job.currency_code}`
        }

        let location = this.props.job.locations.join(", ");

        let remote = this.props.job.remote_ok ? <Text>Remote</Text> : null;


        let users = this.renderUser(this.props.job.makers);

        return (

            <TouchableNativeFeedback
                onPress={this.onJobClick}
                background={TouchableNativeFeedback.SelectableBackground()}
                >
                <View style={styles.container}>
                    <View style={styles.avatarHolder}>
                        <Image style={styles.jobAvatar}
                               source={{uri: getImageUrl(this.props.job.image_uuid, 128)}}
                        />
                    </View>

                    <View style={{
                        flex: 1,
                        flexDirection: 'column',
                        marginLeft: 16
                    }}>

                        <Text style={styles.title}>
                            {this.props.job.company_name}
                        </Text>

                        <Text style={styles.tagline}>
                            {this.props.job.job_title}
                        </Text>

                        <Text style={styles.info}>
                            {this.props.job.job_type}
                        </Text>
                        {remote}
                        <Text>
                            {location}
                        </Text>
                        <Text>
                            {displaySalary}
                        </Text>
                        {users}
                        <Text style={styles.subInfo}>
                            {displayCreatedAt}
                        </Text>

                    </View>

                </View>
            </TouchableNativeFeedback>
        )
    }
}

JobItem.propTypes = {
    job: PropTypes.object,
};

const styles = StyleSheet.create({

    container: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: 'white',
        paddingTop: 8,
        paddingBottom: 8,
    },

    info: {
        marginTop: 8
    },

    tagline: {
        marginTop: 4
    },

    avatarHolder: {
        justifyContent: 'center',
        alignItems: 'center',
        width: 96,
    },

    jobAvatar: {
        marginLeft: 16,
        resizeMode: 'contain',
        height: 88,
        width: 88,

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