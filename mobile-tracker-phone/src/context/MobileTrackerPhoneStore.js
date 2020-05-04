import React, {useReducer, useCallback} from 'react';
import {Provider} from './index';
import * as initialState from './initialState';

const SET_DEVICE_CODE = 'SET_DEVICE_CODE';

function reducer(state, action) {
  const {type, payload} = action;

  switch (type) {
    case SET_DEVICE_CODE: {
      console.log(SET_DEVICE_CODE);
      return {...state, deviceCode: payload};
    }
  }
}

export const MobileTrackerPhoneStore = ({children}) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  const onRegisterDevice = useCallback(async () => {
    dispatch({type: SET_DEVICE_CODE, payload: 'DAADDDD'});
  }, []);
  const value = {...state, onRegisterDevice};
  return <Provider value={value}>{children}</Provider>;
};
