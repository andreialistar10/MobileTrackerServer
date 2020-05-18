import React, {useContext, useEffect, useState} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StyleSheet,
  ToastAndroid,
  Platform,
  AlertIOS,
} from 'react-native';
import MobileTrackerPhoneHeader from '../common/MobileTrackerPhoneHeader';
import UnregisteredDeviceScreen from './UnregisteredDeviceScreen';
import {MobileTrackerPhoneContext} from '../../context';
import Loading from '../common/Loading';

const UnregisteredDeviceContainer = ({navigation}) => {
  const {onRegisterDevice} = useContext(MobileTrackerPhoneContext);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    setLoading(false);
  }, []);

  const notifyError = (errorMessage) => {
    if (Platform.OS === 'android') {
      ToastAndroid.showWithGravityAndOffset(
        errorMessage,
        ToastAndroid.LONG,
        ToastAndroid.TOP,
        25,
        50,
      );
    } else {
      AlertIOS.alert(errorMessage);
    }
  };

  const registerDevice = (name) => {
    if (!deviceNameIsValid(name)) {
      return;
    }
    setLoading(true);
    onRegisterDevice({name})
      .then(() => {
        navigation.navigate('RegisteredDevice');
      })
      .catch((errorMessage) => {
        setLoading(false);
        notifyError(errorMessage);
      });
  };

  const deviceNameIsValid = (deviceName) => {
    return !stringIsBlank(deviceName);
  };

  const stringIsBlank = (str) => {
    return !str || /^\s*$/.test(str);
  };

  return loading ? (
    <Loading isLoading={loading} />
  ) : (
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
    height: '100%',
    width: '100%',
  },
});

export default UnregisteredDeviceContainer;
