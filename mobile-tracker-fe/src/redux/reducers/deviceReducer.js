import * as types from "../actions/actionTypes";
import { defaultState } from "../stateManagement";

export default function deviceReducer(state = defaultState.devices, action) {
  const { type, payload } = action;
  switch (type) {
    case types.GET_ALL_DEVICES:
      return { ...payload };
    case types.ADD_DEVICE: {
      const newState = { ...state };
      newState.devicesList.push(payload);
      return newState;
    }
    case types.UPDATE_DEVICE: {
      const currentDevices = state.devicesList.map((currentDevice) => {
        if (currentDevice.id === payload.id) {
          return payload;
        }
        return currentDevice;
      });
      return {
        ...state,
        devicesList: currentDevices,
      };
    }
    case types.DELETE_DEVICE: {
      const currentDevices = state.devicesList.filter(
        (currentDevice) => currentDevice.id !== payload.id
      );
      return {
        ...state,
        devicesList: currentDevices,
      };
    }
    default:
      return state;
  }
}
