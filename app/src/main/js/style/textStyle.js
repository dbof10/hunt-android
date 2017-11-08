import {StyleSheet} from 'react-native';
import {colors} from './resource'

export default StyleSheet.create({

    body: {
        color: colors.textPrimaryColor,
    },

    title: {
        fontSize: 18,
        color: colors.textPrimaryColor
    },

    userName: {
        color: colors.textPrimaryColor,
        fontWeight: 'bold'
    },

    tagline: {
        fontSize: 12,
        color: colors.textSecondaryColor
    },

    subInfo: {
        fontSize: 12,
        color: 'rgba(0, 0, 0, 0.26)'
    },

    mention: {
        color: colors.accent
    },

});