import React, {useContext, useState, useEffect} from 'react';
import {ScrollView, SafeAreaView, StyleSheet} from 'react-native';
import {MobileTrackerPhoneContext} from '../../context';
import MobileTrackerPhoneHeader from '../common/MobileTrackerPhoneHeader';
import RegisteredDeviceScreen from './RegisteredDeviceScreen';
import {notifyError} from '../../core/alert';
import {connectToPairingStompBroker} from '../../core/stompClient';

const RegisteredDeviceContainer = ({navigation}) => {
  const {startPairing, deviceInformation, authorization} = useContext(
    MobileTrackerPhoneContext,
  );
  const [pairing, setPairing] = useState(false);
  const [loading, setLoading] = useState(false);
  const [phoneIsVisibleForPairing, setPhoneIsVisibleForPairing] = useState(
    false,
  );

  const openPairingMode = React.useCallback(() => {
    startPairing()
      .then(() => {
        setPhoneIsVisibleForPairing(true);
        navigation.setParams({pairing: true});
      })
      .catch((errorMessage) => {
        setPairing(false);
        notifyError(errorMessage);
      })
      .finally(() => setLoading(false));
  }, [navigation, startPairing]);

  const onStompClientConnectionError = React.useCallback(() => {
    setPairing(false);
    setLoading(false);
    setPhoneIsVisibleForPairing(false);
    notifyError('Could not connect to server! Please try again later!');
  }, []);

  useEffect(() => {
    if (pairing === true) {
      const client = connectToPairingStompBroker(
        deviceInformation.id,
        onStompClientConnectionError,
        openPairingMode,
        () => {},
        authorization.token,
      );
      client.activate();
      return () => {
        client.deactivate();
      };
    }
  }, [pairing]);

  useEffect(() => {
    console.log('AICI');
    return () => {
      console.log('IES');
    };
  }, []);

  const handleTurnOnPress = () => {
    setPairing(true);
    setLoading(true);
  };
  const handleTurnOffPress = () => {
    setPairing(false);
    setPhoneIsVisibleForPairing(false);
    navigation.navigate('RegisteredDeviceContainer', {pairing: false});
  };
  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <MobileTrackerPhoneHeader />
        <RegisteredDeviceScreen
          onTurnOnPress={handleTurnOnPress}
          onTurnOffPress={handleTurnOffPress}
          pairing={phoneIsVisibleForPairing}
          loading={loading}
        />
      </ScrollView>
    </SafeAreaView>
  );
};

RegisteredDeviceContainer.navigationOptions = ({navigation}) => {
  return {
    swipeEnabled:
      navigation.state.params === undefined
        ? false
        : navigation.state.params.pairing,
  };
};

const styles = StyleSheet.create({
  scrollView: {
    height: '100%',
    width: '100%',
  },
});

export default RegisteredDeviceContainer;
