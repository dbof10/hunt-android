import React, {Component} from 'react';
import {StyleSheet, FlatList, View} from 'react-native';

import {Load, LoadMore} from '../action/job';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import Loading from '../component/loading'
import Error from '../component/error'
import JobItem from '../component/job'

class Job extends Component {

    constructor(props) {
        super(props);
        this.loadMore = this.loadMore.bind(this);

    }

    componentDidMount() {
        this.props.load()
    }

    keyExtractor = (item, index) => item.id;

    renderItem = ({item, index}) => {
        return <JobItem
            id={item.id}
            job={item}
        />
    };

    loadMore() {
        this.props.loadMore()
    }

    render() {
        if (this.props.job.isLoading) {
            return <Loading/>;
        } else {

            if (this.props.job.error) {

                return <Error
                    errorMessage={this.props.job.error.errorMessage}
                    explainMessage={this.props.job.error.explainMessage}
                    onRetry={() => {
                        this.props.load()
                    }
                    }/>;

            } else {
                return (
                    <FlatList
                        ItemSeparatorComponent={() => (
                            <View style={styles.divider}/>
                        )}
                        data={this.props.job.dataSource}
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
        flex: 1
    }

});


function mapStateToProps(state) {
    const job = state.job;
    return {
        job,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        load: bindActionCreators(Load, dispatch),
        loadMore: bindActionCreators(LoadMore, dispatch)
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Job);