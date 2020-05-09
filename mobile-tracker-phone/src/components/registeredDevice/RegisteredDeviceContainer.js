import React, {useContext, useState, useEffect} from 'react';
import {ScrollView, SafeAreaView, StyleSheet} from 'react-native';
import {MobileTrackerPhoneContext} from '../../context';
import MobileTrackerPhoneHeader from '../common/MobileTrackerPhoneHeader';
import RegisteredDeviceScreen from './RegisteredDeviceScreen';

const RegisteredDeviceContainer = ({navigation}) => {
  const {deviceInformation} = useContext(MobileTrackerPhoneContext);
  const [pairing, setPairing] = useState(false);

  useEffect(() => {
    console.log('AICI');
    return () => {
      console.log('IES');
    };
  }, []);

  const handleTurnOnPress = () => {
    setPairing(true);
    navigation.setParams({pairing: true});
  };
  const handleTurnOffPress = () => {
    setPairing(false);
    navigation.navigate('RegisteredDeviceContainer', {pairing: false});
  };
  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <MobileTrackerPhoneHeader />
        <RegisteredDeviceScreen
          onTurnOnPress={handleTurnOnPress}
          onTurnOffPress={handleTurnOffPress}
          pairing={pairing}
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
