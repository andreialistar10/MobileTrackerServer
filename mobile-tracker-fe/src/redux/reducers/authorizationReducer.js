import * as types from "../actions/actionTypes";
import { defaultState } from "../stateManagement";

export default function authorizationReducer(
  state = defaultState.authorization,
  action
) {
  const { type, payload } = action;
  switch (type) {
    case types.LOGIN_SUCCESS:
      return { ...state, ...payload };
    default:
      return state;
  }
}
