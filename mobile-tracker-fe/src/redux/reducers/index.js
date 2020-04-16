import { combineReducers } from "redux";
import authorization from "./authorizationReducer";
import user from "./userReducer";
import * as types from "../actions/actionTypes";
import { defaultState } from "../stateManagement";

const appReducer = combineReducers({
  authorization,
  user,
});

const rootReducer = (state, action) => {
  if (action.type === types.LOGOUT_SUCCESS) {
    state = { ...defaultState };
  }
  return appReducer(state, action);
};

export default rootReducer;
