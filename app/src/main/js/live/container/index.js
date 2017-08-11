import React, {Component} from 'react';
import {ActivityIndicator, AppRegistry, StyleSheet, FlatList,View} from 'react-native';

import {Load, LoadMore} from '../action/action';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import Loading from '../component/loading'
import Error from '../component/error'
import EventItem from '../component/event'
import {formatCurrentDate} from "../util/dateUtils"

class Main extends Component {

    constructor(props) {
        super(props);
        this.loadMore = this.loadMore.bind(this);

    }

    componentDidMount() {
        this.props.load(formatCurrentDate(new Date().getTime()))
    }

    keyExtractor = (item, index) => item.id;

    renderItem = ({item, index}) => (
        <EventItem
            id={item.id}
            event={item}
        />
    );

    loadMore(){
        this.props.loadMore(formatCurrentDate(new Date().getTime()))
    }

    render() {
        if (this.props.event.isLoading) {
            return <Loading/>;
        } else {

            if (this.props.event.error) {

                return <Error
                    errorMessage={this.props.event.error.errorMessage}
                    explainMessage={this.props.event.error.explainMessage}
                    onRetry={() => {
                        this.props.load()
                    }
                    }/>;

            } else {
                return (
                    <FlatList
                        ItemSeparatorComponent={ () => (
                            <View style={styles.divider} />
                            )}
                        data={this.props.event.dataSource}
                        keyExtractor={this.keyExtractor}
                        renderItem={this.renderItem}
                        onEndReached={this.loadMore}
                        onEndReachedThreshold={2}
                    />
                )
            }
        }
    }

}

const styles = StyleSheet.create({

    divider: {
        height: 8,
        flex:1
    }

});


function mapStateToProps(state) {
    const event = state.event;
    return {
        event,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        load: bindActionCreators(Load, dispatch),
        loadMore: bindActionCreators(LoadMore,dispatch)
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Main);