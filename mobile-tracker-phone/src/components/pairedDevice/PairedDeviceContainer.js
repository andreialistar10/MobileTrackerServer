import React from 'react';
import {SafeAreaView, ScrollView, StyleSheet} from 'react-native';
import MobileTrackerPhoneHeader from '../common/MobileTrackerPhoneHeader';
import PairedDeviceScreen from './PairedDeviceScreen';

const PairedDeviceContainer = ({navigation}) => {
  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <MobileTrackerPhoneHeader />
        <PairedDeviceScreen navigation={navigation} />
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    height: '100%',
    width: '100%',
  },
});

export default PairedDeviceContainer;
