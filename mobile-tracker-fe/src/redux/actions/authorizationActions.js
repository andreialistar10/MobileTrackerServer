import * as types from "./actionTypes";
import * as authorizationApi from "../../api/authorizationApi";

export function loginSuccess(authorization) {
  return { type: types.LOGIN_SUCCESS, payload: authorization };
}

export function logoutSuccess() {
  return { type: types.LOGOUT_SUCCESS };
}

export function login(user) {
  return (dispatch) => {
    return authorizationApi.loginUser(user).then((authorization) => {
      authorization.username = user.username;
      dispatch(loginSuccess(authorization));
    });
  };
}

export function logout() {
  return (dispatch) => {
    return authorizationApi.logoutUser().then(() => {
      dispatch(logoutSuccess());
    });
  };
}
