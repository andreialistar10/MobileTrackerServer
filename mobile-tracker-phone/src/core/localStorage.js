import AsyncStorage from '@react-native-community/async-storage';
import {initialState} from '../context/initialState';

const DEVICE_INFORMATION_KEY = 'DEVICE_INFORMATION';

export const saveDeviceInformation = async (deviceInformation) => {
  try {
    await AsyncStorage.setItem(
      DEVICE_INFORMATION_KEY,
      JSON.stringify(deviceInformation),
    );
  } catch (e) {}
};

export const getDeviceInformation = async () => {
  try {
    const deviceInfo = await AsyncStorage.getItem(DEVICE_INFORMATION_KEY);
    let deviceInformation = JSON.parse(deviceInfo);
    if (!deviceInformation) {
      deviceInformation = initialState.deviceInformation;
    }
    return deviceInformation;
  } catch (e) {
    const {deviceInformation} = initialState;
    return {deviceInformation};
  }
};

export const getState = async () => {
  const deviceInformation = await getDeviceInformation();
  return {deviceInformation};
};

export const removeDeviceInformation = async () => {
  return AsyncStorage.removeItem(DEVICE_INFORMATION_KEY);
};

export const clearLocalStorage = async () => {
  return AsyncStorage.clear();
};
