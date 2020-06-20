import * as types from "../actions/actionTypes";
import { defaultState } from "../stateManagement";

export default function deviceReducer(state = defaultState.devices, action) {
  const { type, payload } = action;
  switch (type) {
    case types.GET_ALL_DEVICES:
      return {...payload};
    case types.ADD_DEVICE:
      return state.concat(payload);
    case types.UPDATE_DEVICE: {
      return state.map((currentDevice) => {
        if (currentDevice.id === payload.id) {
          return payload;
        }
        return currentDevice;
      });
    }
    default:
      return state;
  }
}
