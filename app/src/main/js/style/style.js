import {StyleSheet} from 'react-native';
import {colors} from './resource';

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

    dialogBackground: {
        backgroundColor: colors.gray_50,
        borderRadius: 2,
        elevation: 4
    },

    dialogWindowBackground: {
        flex: 1,
        backgroundColor: colors.scrim
    }
});