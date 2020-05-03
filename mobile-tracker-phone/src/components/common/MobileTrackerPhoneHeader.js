import React from 'react';
import {Text, StyleSheet, Image, View} from 'react-native';

const backgroundImage = require('../../assets/logo.png');
const MobileTrackerPhoneHeader = () => {
  return (
    <View style={styles.headerContainer}>
      <Image source={backgroundImage} style={styles.logo} />
      <Text style={styles.textContainer}>
        <Text style={styles.prefix}>Mobile</Text>
        <Text style={styles.suffix}>Tracker</Text>
      </Text>
    </View>
  );
};

const styles = StyleSheet.create({
  headerContainer: {
    alignItems: 'center',
    width: '100%',
    height: 115,
    flex: 1,
    flexDirection: 'row',
    backgroundColor: 'rgba(207, 241, 238,0.45)',
  },
  logo: {
    // opacity: 0.9,
    overflow: 'visible',
    marginLeft: 10,
    width: 72,
    height: 72,
  },
  textContainer: {
    marginLeft: 8,
    fontSize: 35,
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
