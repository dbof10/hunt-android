import React, {Component} from 'react';
import {FlatList, View} from 'react-native';
import {Load, LoadMore} from '../action/newsletter';
import {bindActionCreators} from 'redux';
import Loading from '../component/loading';
import Error from '../component/error';
import NewsLetterItem from '../component/newsletter';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import Styles from '../style/style';


class NewsLetter extends Component {

    constructor(props) {
        super(props);
        this.loadMore = this.loadMore.bind(this);
    }

    componentDidMount() {
        this.props.load(this.props.index, this.props.type)
    }

    keyExtractor = (item, index) => item.id;

    renderItem = ({item, index}) => {
        return <NewsLetterItem
            id={item.id}
            newsletter={item}
        />
    };

    loadMore() {
        this.props.loadMore(this.props.index, this.props.type)
    }

    render() {
        const tab = this.props.tab;
        if (tab.isLoading) {
            return <Loading/>;
        } else {

            if (tab.error) {

                return <Error
                    errorMessage={tab.error.errorMessage}
                    explainMessage={tab.error.explainMessage}
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
                        data={tab.dataSource}
                        keyExtractor={this.keyExtractor}
                        renderItem={this.renderItem}
                        onEndReached={this.loadMore}
                        onEndReachedThreshold={3}
                    />

                )
            }
        }
    }

}

NewsLetter.propTypes = {
    type: PropTypes.string.isRequired,
    tab: PropTypes.object.isRequired
};


NewsLetter.defaultProps = {
    tab: {
        pageInfo: null,
        isLoading: false,
        error: null,
        isLoadingMore: false,
        moreError: null,
        dataSource: []
    }
};


function mapDispatchToProps(dispatch) {
    return {
        load: bindActionCreators(Load, dispatch),
        loadMore: bindActionCreators(LoadMore, dispatch)
    }
}

function mapStateToProps(state) {
    return {
        state
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(NewsLetter);