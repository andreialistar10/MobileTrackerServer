/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {useEffect} from 'react';

import {createAppContainer, createSwitchNavigator} from 'react-navigation';

import {MobileTrackerPhoneStore} from '../context/MobileTrackerPhoneStore';
import UnregisteredDeviceContainer from './unregisteredDevice/UnregisteredDeviceContainer';
import RegisteredDeviceContainer from './registeredDevice/RegisteredDeviceContainer';
import {createStackNavigator} from 'react-navigation-stack';
import AuthLoading from './AuthLoading';

// const MainNavigator = createSwitchNavigator(
//   {
//     UnregisteredDevice: UnregisteredDeviceContainer,
//     RegisteredDevice: RegisteredDeviceContainer,
//   },
//   {initialRouteName: 'UnregisteredDevice'},
// );
//
// const AppContainer = createAppContainer(MainNavigator);

// const stackNavigator = createStackNavigator(
//   {
//     UnregisteredDevice: {
//       screen: UnregisteredDeviceContainer,
//       navigationOptions: {headerShown: false},
//     },
//     RegisteredDevice: RegisteredDeviceContainer,
//   },
//   {
//     initialRouteName: 'AuthLoading',
//   },
//   {
//     headerMode: 'none',
//     navigationOptions: {
//       headerVisible: false,
//     },
//   },
// );

const MainNavigator = createSwitchNavigator({
  AuthLoading,
  UnregisteredDevice: UnregisteredDeviceContainer,
  RegisteredDevice: RegisteredDeviceContainer,
});

const AppContainer = createAppContainer(MainNavigator);

const App: () => React$Node = () => {
  useEffect(() => {
    console.log('Da');
    return () => {
      console.log('NU');
    };
  });
  return (
    <MobileTrackerPhoneStore>
      <AppContainer
      // ref={(navigationRef) => {
      //   navService.setTopLevelNavigator(navigationRef);
      // }}
      />
    </MobileTrackerPhoneStore>
  );
};
export default App;
