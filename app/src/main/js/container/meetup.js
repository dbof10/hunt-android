import React, {Component} from 'react';
import {FlatList, StyleSheet, View} from 'react-native';

import {Load} from '../action/meetup';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import Loading from '../component/loading'
import Error from '../component/error'
import MeetupItem from '../component/meetup'
import {MenuContext} from 'react-native-popup-menu';

class Meetup extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.props.load("UPCOMING")
    }

    keyExtractor = (item, index) => item.id;

    renderItem = ({item, index}) => {
        return <MeetupItem
            id={item.id}
            meetup={item}
        />
    };


    render() {
        if (this.props.meetup.isLoading) {
            return <Loading/>;
        } else {

            if (this.props.meetup.error) {

                return <Error
                    errorMessage={this.props.meetup.error.errorMessage}
                    explainMessage={this.props.meetup.error.explainMessage}
                    onRetry={() => {
                        this.props.load()
                    }
                    }/>;

            } else {
                return (
                    <MenuContext>
                        <FlatList
                            ItemSeparatorComponent={() => (
                                <View style={styles.divider}/>
                            )}
                            data={this.props.meetup.dataSource}
                            keyExtractor={this.keyExtractor}
                            renderItem={this.renderItem}
                        />
                    </MenuContext>
                )
            }
        }
    }

}

const styles = StyleSheet.create({

    divider: {
        height: 8,
        flex: 1
    }

});


function mapStateToProps(state) {
    const meetup = state.meetup;
    return {
        meetup,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        load: bindActionCreators(Load, dispatch),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Meetup);