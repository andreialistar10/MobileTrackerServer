import React, {useReducer} from 'react';
import {Provider} from './index';
import {initialState} from './initialState';
import {registerDevice} from '../core/api';
import {
  getState,
  saveAuthorization,
  saveDeviceInformation,
} from '../core/localStorage';

const SET_DEVICE_CODE = 'SET_DEVICE_CODE';
const INIT_STORE = 'INIT_STORE';

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
      return {...payload};
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

  const value = {...state, onRegisterDevice, initStore};
  return <Provider value={value}>{children}</Provider>;
};
