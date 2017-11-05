import {StyleSheet} from 'react-native';
import {colors} from './color';

export default StyleSheet.create({

    divider: {
        height: 8,
        flex: 1
    },

    separator: {
        marginTop: 8,
        backgroundColor: colors.divider,
        height: 1,
        flex: 1
    },
});