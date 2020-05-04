import React from 'react';
import {View, Button, Text} from 'react-native';

const RegisteredDeviceContainer = ({navigation}) => {
  return (
    <View>
      <Text>Registered device</Text>
      <Button
        title={'Go back'}
        onPress={() => {
          console.log('daddd');
          navigation.goBack();
        }}
      />
    </View>
  );
};

export default RegisteredDeviceContainer;
