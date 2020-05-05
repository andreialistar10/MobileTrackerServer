import {createStackNavigator} from 'react-navigation-stack';
import RegisteredDeviceContainer from './RegisteredDeviceContainer';

export const RegisteredDevice = createStackNavigator(
  {RegisteredDevice: RegisteredDeviceContainer},
  {initialRouteName: 'RegisteredDevice'},
);

export * from './RegisteredDeviceContainer';
