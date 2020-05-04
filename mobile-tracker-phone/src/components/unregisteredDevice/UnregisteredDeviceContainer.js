import React, {useContext, useEffect} from 'react';
import {SafeAreaView, ScrollView, StyleSheet} from 'react-native';
import MobileTrackerPhoneHeader from '../common/MobileTrackerPhoneHeader';
import UnregisteredDeviceScreen from './UnregisteredDeviceScreen';
import {MobileTrackerPhoneContext} from '../../context';

const UnregisteredDeviceContainer = ({navigation}) => {
  const {deviceCode, onRegisterDevice} = useContext(MobileTrackerPhoneContext);

  useEffect(() => {
    console.log(deviceCode);
  });

  const registerDevice = (deviceInfo) => {
    onRegisterDevice();
    navigation.navigate('RegisteredDevice');
    // navService.navigate('RegisteredDevice');
  };

  const deviceNameIsValid = (deviceName) => {
    return !stringIsBlank(deviceName);
  };

  const stringIsBlank = (str) => {
    return !str || /^\s*$/.test(str);
  };

  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <MobileTrackerPhoneHeader />
        <UnregisteredDeviceScreen
          onRegisterDevice={registerDevice}
          deviceNameValidator={deviceNameIsValid}
        />
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
