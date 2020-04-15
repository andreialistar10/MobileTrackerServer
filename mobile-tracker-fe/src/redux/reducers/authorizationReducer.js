import * as types from "../actions/actionTypes";
import initialState from "./initialState";

export default function authorizationReducer(
  state = initialState.authorization,
  action
) {
  switch (action.type) {
    case types.LOGIN_SUCCESS:
      return { ...state };
    default:
      return state;
  }
}
