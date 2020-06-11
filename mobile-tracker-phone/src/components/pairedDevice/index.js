import {createMaterialTopTabNavigator} from 'react-navigation-tabs';
import DeviceInformationScreen from '../registeredDevice/DeviceInformationScreen';
import PairedDeviceContainer from './PairedDeviceContainer';

export const PairedDeviceTabNavigator = createMaterialTopTabNavigator(
  {
    DeviceInformation: {
      screen: DeviceInformationScreen,
      // navigationOptions: {gestureEnabled: false},
    },
    PairedDeviceContainer: {
      screen: PairedDeviceContainer,
      navigationOptions: {
        swipeEnabled: false,
      },
    },
  },
  {
    initialRouteName: 'PairedDeviceContainer',
    animationEnabled: true,
    tabBarOptions: {
      showLabel: false,
      showIcon: false,
      style: {height: 0},
    },
  },
);
