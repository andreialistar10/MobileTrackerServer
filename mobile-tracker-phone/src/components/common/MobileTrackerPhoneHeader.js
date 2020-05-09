import React from 'react';
import {Text, StyleSheet, Image, View} from 'react-native';
import LinearGradient from 'react-native-linear-gradient';
import {HEADER_HEIGHT} from '../style';

const backgroundImage = require('../../assets/logo.png');
const MobileTrackerPhoneHeader = () => {
  return (
    <LinearGradient
      style={styles.headerContainer}
      colors={['rgba(207, 241, 238,0.4)', 'rgba(175,228,241, 1)']}
      start={{x: 0, y: 0}}
      end={{x: 1, y: 0}}>
      <Image source={backgroundImage} style={styles.logo} />
      <Text style={styles.textContainer}>
        <Text style={styles.prefix}>Mobile</Text>
        <Text style={styles.suffix}>Tracker</Text>
      </Text>
    </LinearGradient>
  );
};

const styles = StyleSheet.create({
  headerContainer: {
    backgroundColor: '#fff',
    alignItems: 'center',
    width: '100%',
    height: HEADER_HEIGHT,
    flex: 1,
    flexDirection: 'row',
    elevation: 6,

    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 3,
    },
    shadowOpacity: 0.27,
    shadowRadius: 4.65,
  },
  logo: {
    // opacity: 0.9,
    overflow: 'visible',
    marginLeft: 10,
    width: 65,
    height: 65,
  },
  textContainer: {
    marginLeft: 8,
    fontSize: 32,
    fontWeight: '500',
    fontFamily: 'Rubik-Regular',
  },
  prefix: {
    color: 'rgba(15, 10, 190, 1)',
  },
  suffix: {
    color: 'rgb(5, 152, 230)',
  },
});

export default MobileTrackerPhoneHeader;
