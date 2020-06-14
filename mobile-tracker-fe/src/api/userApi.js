import axios from "axios";
import { handleConnectionAndServerErrors } from "./apiUtils";
import {
  APPLICATION_JSON,
  AUTHORIZATION_HEADER,
  BACKEND_URL,
  CONTENT_TYPE_HEADER,
  PREFIX_AUTHORIZATION_HEADER_VALUE,
} from "./commonConstants";
import { getApiToken } from "../index";

const SIGN_UP_URL = `${BACKEND_URL}/users/sign-up`;
const RESEND_CONFIRMATION_EMAIL_URL = `${BACKEND_URL}/users/resend-registration-email`;
const USER_DETAILS_URL = `${BACKEND_URL}/users`;

const DUPLICATE_USER_RESPONSE_DATA_TEXT = "DUPLICATE_USER";

const axiosInstance = axios.create();
axiosInstance.interceptors.request.use((configs) => {
  configs.headers.common[CONTENT_TYPE_HEADER] = APPLICATION_JSON;
  if (configs.url !== SIGN_UP_URL) {
    const tokenApi = getApiToken();
    configs.headers.common[
      AUTHORIZATION_HEADER
    ] = `${PREFIX_AUTHORIZATION_HEADER_VALUE} ${tokenApi}`;
  }
  return configs;
});
const handleSignUpUserError = (error) => {
  handleConnectionAndServerErrors(error);
  const { response } = error;
  if (
    response.status === 400 &&
    response.data === DUPLICATE_USER_RESPONSE_DATA_TEXT
  ) {
    throw { username: "The username already exists!" };
  } else {
    throw error;
  }
};

const handleResendConfirmationEmailError = (error) => {
  handleConnectionAndServerErrors(error);
  const { response } = error;
  if (response.status === 401) {
    throw new Error(
      "Your confirmation mail was not sent again because your session has expire! Please sign in and try again!"
    );
  }
  throw error;
};

const handleUpdateUserDetailsError = (error) => {
  handleConnectionAndServerErrors(error);
  const { response } = error;
  if (response.status === 400) {
    throw new Error("Please fill correct the invalid fields!");
  }
  throw error;
};

const handleGetUserDetailsError = (error) => {
  handleConnectionAndServerErrors(error);
  throw error;
};

export const signUpUser = (newUser) => {
  return axiosInstance
    .post(SIGN_UP_URL, newUser)
    .then((response) => response.data)
    .catch((error) => handleSignUpUserError(error));
};

export const resendConfirmationEmailUser = () => {
  return axiosInstance
    .get(RESEND_CONFIRMATION_EMAIL_URL)
    .catch((error) => handleResendConfirmationEmailError(error));
};

export const getUserDetails = () => {
  return axiosInstance
    .get(USER_DETAILS_URL)
    .then((response) => response.data)
    .catch((error) => handleGetUserDetailsError(error));
};

export const updateUserDetails = (userDetails) => {
  return axiosInstance
    .put(USER_DETAILS_URL, userDetails)
    .then((response) => response.data)
    .catch((error) => handleUpdateUserDetailsError(error));
};
