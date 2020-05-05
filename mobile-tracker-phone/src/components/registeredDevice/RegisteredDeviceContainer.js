import React, {useContext} from 'react';
import {
  Button,
  Text,
  ScrollView,
  SafeAreaView,
  StyleSheet,
} from 'react-native';
import {MobileTrackerPhoneContext} from '../../context';
import MobileTrackerPhoneHeader from '../common/MobileTrackerPhoneHeader';

const RegisteredDeviceContainer = ({navigation}) => {
  const {deviceInformation} = useContext(MobileTrackerPhoneContext);
  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <MobileTrackerPhoneHeader />
        <Text>Registered device</Text>
        <Text>DeviceID: {deviceInformation.id}</Text>
        <Text>DeviceName: {deviceInformation.name}</Text>
        <Button
          title={'Go back'}
          onPress={() => {
            navigation.goBack();
          }}
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

export default RegisteredDeviceContainer;
