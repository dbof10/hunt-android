import React, {Component} from 'react';
import {FlatList, View} from 'react-native';
import Styles from '../style/style'
import {Load, LoadMore, NavigateDetail} from '../action/ask';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import Loading from '../component/loading'
import Error from '../component/error';
import AskItem from '../component/ask';

class Ask extends Component {

    constructor(props) {
        super(props);
        this.loadMore = this.loadMore.bind(this);
        this.onNavigateDetail = this.onNavigateDetail.bind(this);
    }

    onNavigateDetail(id) {
        this.props.navigateDetail(id);
    }

    componentDidMount() {
        this.props.load('FEATURED')
    }

    keyExtractor = (item, index) => item.id;

    renderItem = ({item, index}) => (
        <AskItem
            id={item.id}
            ask={item}
            onNavigate={this.onNavigateDetail}
        />
    );

    loadMore() {
        this.props.loadMore('FEATURED')
    }

    render() {
        if (this.props.ask.isLoading) {
            return <Loading/>;
        } else {

            if (this.props.ask.error) {

                return <Error
                    errorMessage={this.props.ask.error.errorMessage}
                    explainMessage={this.props.ask.error.explainMessage}
                    onRetry={() => {
                        this.props.load()
                    }
                    }/>;

            } else {
                return (
                    <FlatList
                        showsVerticalScrollIndicator={false}
                        ItemSeparatorComponent={() => (
                            <View style={Styles.divider}/>
                        )}
                        data={this.props.ask.dataSource}
                        keyExtractor={this.keyExtractor}
                        renderItem={this.renderItem}
                        onEndReached={this.loadMore}
                        onEndReachedThreshold={4}
                    />
                )
            }
        }
    }

}

function mapStateToProps(state) {
    const ask = state.ask;
    return {
        ask
    }
}

function mapDispatchToProps(dispatch) {
    return {
        load: bindActionCreators(Load, dispatch),
        loadMore: bindActionCreators(LoadMore, dispatch),
        navigateDetail: bindActionCreators(NavigateDetail, dispatch),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Ask);