import * as types from "./actionTypes";

export function loginSuccess(authorization) {
  return { types: types.LOGIN_SUCCESS, authorization };
}

export function login(user) {
  console.log(user);
  const authorization = {
    jwt: "jwt",
    refreshToken: "refresh-token",
  };
  return (dispatch) => {
    dispatch(loginSuccess(authorization))
  }
}
