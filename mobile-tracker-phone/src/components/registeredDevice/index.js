import {createStackNavigator} from 'react-navigation-stack';
import RegisteredDeviceContainer from './RegisteredDeviceContainer';

export const RegisteredDevice = createStackNavigator(
  {
    RegisteredDevice: {
      screen: RegisteredDeviceContainer,
      navigationOptions: {headerShown: false},
    },
  },
  {initialRouteName: 'RegisteredDevice'},
);

export * from './RegisteredDeviceContainer';
