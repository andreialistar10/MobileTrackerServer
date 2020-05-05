import React, {useContext} from 'react';
import {View, Button, Text} from 'react-native';
import {MobileTrackerPhoneContext} from '../../context';

const RegisteredDeviceContainer = ({navigation}) => {
  const {deviceInformation} = useContext(MobileTrackerPhoneContext);
  return (
    <View>
      <Text>Registered device</Text>
      <Text>DeviceID: {deviceInformation.id}</Text>
      <Text>DeviceName: {deviceInformation.name}</Text>
      <Button
        title={'Go back'}
        onPress={() => {
          navigation.goBack();
        }}
      />
    </View>
  );
};

export default RegisteredDeviceContainer;
