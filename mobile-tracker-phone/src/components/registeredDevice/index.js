import {createStackNavigator} from 'react-navigation-stack';
import RegisteredDeviceContainer from './RegisteredDeviceContainer';
import {createMaterialTopTabNavigator} from 'react-navigation-tabs';
import DeviceInformationScreen from './DeviceInformationScreen';

export const RegisteredDevice = createStackNavigator(
  {
    RegisteredDevice: {
      screen: RegisteredDeviceContainer,
      navigationOptions: {headerShown: false},
    },
    DeviceInformation: {
      screen: DeviceInformationScreen,
    },
  },
  {initialRouteName: 'RegisteredDevice'},
);

export const Tab = createMaterialTopTabNavigator(
  {
    DeviceInformation: {
      screen: DeviceInformationScreen,
      navigationOptions: {gestureEnabled: false},
    },
    RegisteredDeviceContainer: {screen: RegisteredDeviceContainer},
  },
  {
    initialRouteName: 'RegisteredDeviceContainer',
    animationEnabled: true,
    tabBarOptions: {
      showLabel: false,
      showIcon: false,
      style: {height: 0},
    },
  },
);

export * from './RegisteredDeviceContainer';
