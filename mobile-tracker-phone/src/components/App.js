/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';

import {createAppContainer, createSwitchNavigator} from 'react-navigation';

import {MobileTrackerPhoneStore} from '../context/MobileTrackerPhoneStore';
import UnregisteredDeviceContainer from './unregisteredDevice/UnregisteredDeviceContainer';
import AuthLoading from './AuthLoading';
import {RegisteredDeviceTabNavigator} from './registeredDevice';

const MainNavigator = createSwitchNavigator(
  {
    AuthLoading,
    UnregisteredDevice: UnregisteredDeviceContainer,
    RegisteredDevice: RegisteredDeviceTabNavigator,
  },
  {initialRouteName: 'AuthLoading'},
);

const AppContainer = createAppContainer(MainNavigator);

const App: () => React$Node = () => {
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
