import React, {useReducer, useEffect} from 'react';
import {Alert} from 'react-native';
import {Provider} from './index';
import {initialState} from './initialState';
import {confirmPairing, getPasswordDevice, registerDevice} from '../core/api';
import {
  clearLocalStorage,
  getDeviceInformation,
  getState,
  saveAuthorization,
  saveDeviceInformation,
  saveDeviceSettings,
} from '../core/localStorage';
import BackgroundGeolocation from '@mauron85/react-native-background-geolocation';
import {ROOT_BACKEND_URL_API} from '../core/constants';
import {connectToNotificationStompBroker} from '../core/stompClient';
import navService from "../core/navService";

let isMounted = false;

const SMALL_ICON_FILENAME = '../../assets/Logo_small.png';
const LARGE_ICON_FILENAME = '../../assets/Logo_large.png';

const SET_DEVICE_CODE = 'SET_DEVICE_CODE';
const INIT_STORE = 'INIT_STORE';
const SET_PASSWORD = 'SET_PASSWORD';
const UPDATE_DEVICE_INFORMATION = 'UPDATE_DEVICE_INFORMATION';

function reducer(state, action) {
  const {type, payload} = action;

  switch (type) {
    case SET_DEVICE_CODE: {
      const {id, name, token} = payload;
      const deviceInformation = {id, name};
      const authorization = {...state.authorization, token};
      return {...state, deviceInformation, authorization};
    }
    case INIT_STORE:
      return {...state, ...payload};
    case SET_PASSWORD:
      return {...state, password: payload};
    case UPDATE_DEVICE_INFORMATION:
      return {...state, deviceInformation: payload};
    default:
      return state;
  }
}

export const MobileTrackerPhoneStore = ({children}) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  const onRegisterDevice = deviceInformation => {
    return registerDevice(deviceInformation).then(responseDeviceInformation => {
      const {id, name, token} = responseDeviceInformation;
      return saveDeviceInformation({id, name})
        .then(() => saveAuthorization({...initialState.authorization, token}))
        .then(() => {
          dispatch({
            type: SET_DEVICE_CODE,
            payload: responseDeviceInformation,
          });
          return Promise.resolve(responseDeviceInformation);
        });
    });
  };

  const initStore = () => {
    return getState().then(storedState => {
      console.log(storedState);
      dispatch({type: INIT_STORE, payload: storedState});
      return Promise.resolve(storedState);
    });
  };

  const startPairing = () => {
    return getPasswordDevice().then(({password}) => {
      console.log(password);
      dispatch({type: SET_PASSWORD, payload: password});
      return Promise.resolve();
    });
  };

  const confirmDevicePairing = async (ownerUsername) => {
    const {
      tokenApi,
      timeInterval,
      refreshToken,
      deviceId,
      name,
    } = await confirmPairing(ownerUsername);
    const authorization = {refreshToken, token: tokenApi};
    const deviceInformation = {id: deviceId, name: name, ownerUsername};
    const deviceSettings = {timeInterval};
    await saveDeviceSettings(deviceSettings);
    await saveDeviceInformation(deviceInformation);
    await saveAuthorization(authorization);
    const newState = {
      authorization,
      deviceInformation,
      deviceSettings,
      password: initialState.password,
    };
    dispatch({
      type: INIT_STORE,
      payload: newState,
    });
    return newState;
  };

  const updateDeviceInfo = React.useCallback(async (deviceInfo) => {
    const {name} = deviceInfo;
    const deviceInformation = await getDeviceInformation();
    deviceInformation.name = name;
    await saveDeviceInformation(deviceInformation);
    if (isMounted) {
      dispatch({
        type: UPDATE_DEVICE_INFORMATION,
        payload: deviceInformation,
      });
    }
  }, []);

  const deleteDevice = React.useCallback(async () => {
    await clearLocalStorage();
    const newState = await getState();
    if (isMounted) {
      dispatch({
        type: INIT_STORE,
        payload: newState,
      });
      navService.navigate('AuthLoading');
    }
  }, []);

  useEffect(() => {
    if (
      state.authorization.token &&
      state.authorization.refreshToken &&
      state.deviceSettings.timeInterval
    ) {
      let stompClient;
      BackgroundGeolocation.configure({
        desiredAccuracy: BackgroundGeolocation.HIGH_ACCURACY,
        stationaryRadius: 50,
        distanceFilter: 50,
        notificationTitle: 'MobileTracker',
        notificationText: 'Syncing locations...',
        notificationIconSmall: SMALL_ICON_FILENAME,
        notificationIconLarge: LARGE_ICON_FILENAME,
        startOnBoot: false,
        stopOnTerminate: false,
        locationProvider: BackgroundGeolocation.ACTIVITY_PROVIDER,
        interval: state.deviceSettings.timeInterval / 2,
        fastestInterval: state.deviceSettings.timeInterval,
        activitiesInterval: 10000,
        stopOnStillActivity: false,
        url: `${ROOT_BACKEND_URL_API}/locations`,
        syncUrl: `${ROOT_BACKEND_URL_API}/locations`,
        httpHeaders: {
          Authorization: `Bearer ${state.authorization.token}`,
          'Content-Type': 'application/json',
        },
        postTemplate: {
          latitude: '@latitude',
          longitude: '@longitude',
          timestamp: '@time',
        },
      });

      BackgroundGeolocation.on('start', () => {
        BackgroundGeolocation.startTask((taskKey) => {
          stompClient = connectToNotificationStompBroker(
            state.deviceInformation.id,
            state.authorization.token,
            message => {
              if (message.type === 'UPDATE') {
                updateDeviceInfo(message.data);
              } else {
                deleteDevice();
                BackgroundGeolocation.stop();
                if (stompClient !== undefined) {
                  stompClient.deactivate();
                }
              }
            },
          );
          stompClient.activate();
        });
      });

      BackgroundGeolocation.on('error', (error) => {
        console.log('[ERROR] BackgroundGeolocation error:', error);
      });

      BackgroundGeolocation.on('authorization', (status) => {
        console.log(
          '[INFO] BackgroundGeolocation authorization status: ' + status,
        );
        if (status !== BackgroundGeolocation.AUTHORIZED) {
          setTimeout(
            () =>
              Alert.alert(
                'App requires location tracking permission',
                'Would you like to open app settings?',
                [
                  {
                    text: 'Yes',
                    onPress: () => BackgroundGeolocation.showAppSettings(),
                  },
                  {
                    text: 'No',
                    onPress: () => console.log('No Pressed'),
                    style: 'cancel',
                  },
                ],
              ),
            1000,
          );
        }
      });
      BackgroundGeolocation.on('http_authorization', () => {
        console.log('[INFO] App needs to authorize the http requests');
      });

      BackgroundGeolocation.checkStatus((status) => {
        if (!status.isRunning) {
          BackgroundGeolocation.start();
        }
      });
      return () => {
        BackgroundGeolocation.removeAllListeners();
      };
    }
  }, [
    state.authorization.refreshToken,
    state.authorization.token,
    state.deviceSettings.timeInterval,
    updateDeviceInfo,
    deleteDevice,
  ]);

  useEffect(() => {
    isMounted = true;
    return () => {
      isMounted = false;
    };
  }, []);

  const value = {
    ...state,
    onRegisterDevice,
    initStore,
    startPairing,
    confirmDevicePairing,
  };
  return <Provider value={value}>{children}</Provider>;
};
