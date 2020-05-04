import React, {useContext, useEffect} from 'react';
import {SafeAreaView, ScrollView, StyleSheet} from 'react-native';
import MobileTrackerPhoneHeader from '../common/MobileTrackerPhoneHeader';
import UnregisteredDeviceScreen from './UnregisteredDeviceScreen';
import {MobileTrackerPhoneContext} from '../../context';
import navService from '../../core/navService';

const UnregisteredDeviceContainer = ({navigation}) => {
  const {deviceCode, onRegisterDevice} = useContext(MobileTrackerPhoneContext);

  useEffect(() => {
    console.log(deviceCode);
  });

  const registerDevice = () => {
    onRegisterDevice();
    navigation.navigate('RegisteredDevice');
    // navService.navigate('RegisteredDevice');
  };

  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <MobileTrackerPhoneHeader />
        <UnregisteredDeviceScreen onRegisterDevice={registerDevice} />
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    // backgroundColor: Colors.lighter,
    // flex: 1,
    height: '100%',
    width: '100%',
  },
});

export default UnregisteredDeviceContainer;
