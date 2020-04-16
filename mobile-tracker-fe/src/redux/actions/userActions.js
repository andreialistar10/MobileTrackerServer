import * as types from "./actionTypes";
import { resendConfirmationEmailUser, signUpUser } from "../../api/userApi";

export function signUpSuccess() {
  return { type: types.SIGN_UP_SUCCESS };
}

export function resendConfirmationEmailSuccess() {
  return { type: types.LOGOUT_SUCCESS };
}

export function signUp(newUser) {
  return (dispatch) => {
    return signUpUser(newUser).then((savedUser) => {
      dispatch(signUpSuccess());
      return Promise.resolve(savedUser);
    });
  };
}

export function resendConfirmationEmail() {
  return (dispatch) => {
    return resendConfirmationEmailUser().finally(() =>
      dispatch(resendConfirmationEmailSuccess())
    );
  };
}
