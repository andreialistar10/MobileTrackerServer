import { defaultState } from "../stateManagement";
import * as types from "../actions/actionTypes";

export default function userReducer(state = defaultState.user, action) {
  const { type } = action;
  switch (type) {
    case types.SIGN_UP_SUCCESS:
      return state;
    default:
      return state;
  }
}
