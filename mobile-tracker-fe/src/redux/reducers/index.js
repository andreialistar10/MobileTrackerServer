import { combineReducers } from "redux";
import authorization from "./authorizationReducer";
import user from "./userReducer";
import devices from './deviceReducer';
import * as types from "../actions/actionTypes";
import { defaultState } from "../stateManagement";

const appReducer = combineReducers({
  authorization,
  user,
  devices
});

const rootReducer = (state, action) => {
  if (action.type === types.LOGOUT_SUCCESS) {
    state = { ...defaultState };
  }
  return appReducer(state, action);
};

export default rootReducer;
