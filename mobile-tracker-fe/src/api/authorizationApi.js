import axios from "axios";
import { handleConnectionAndServerErrors } from "./apiUtils";
import { getRefreshToken } from "../index";
import {
  APPLICATION_JSON,
  AUTHORIZATION_HEADER,
  BACKEND_URL,
  CONTENT_TYPE_HEADER,
  PREFIX_AUTHORIZATION_HEADER_VALUE,
  X_WWW_FORM_URL_ENCODED,
} from "./commonConstants";

const qs = require("querystring");

const LOGIN_URL = `${BACKEND_URL}/login`;
const LOGOUT_URL = `${BACKEND_URL}/logout`;

const INVALID_CREDENTIALS_RESPONSE_DATA_TEXT = "INVALID_CREDENTIALS";

const axiosInstance = axios.create();
axiosInstance.interceptors.request.use((config) => {
  if (config.url === LOGIN_URL) {
    config.headers.common[CONTENT_TYPE_HEADER] = X_WWW_FORM_URL_ENCODED;
  } else {
    const refreshToken = getRefreshToken();
    if (refreshToken) {
      config.headers.common[
        AUTHORIZATION_HEADER
      ] = `${PREFIX_AUTHORIZATION_HEADER_VALUE} ${refreshToken}`;
      config.headers.common[CONTENT_TYPE_HEADER] = APPLICATION_JSON;
    }
  }
  return config;
});

const handleLoginUserError = (error) => {
  handleConnectionAndServerErrors(error);
  const { response } = error;
  if (
    response.status === 404 &&
    response.data === INVALID_CREDENTIALS_RESPONSE_DATA_TEXT
  ) {
    throw new Error("Invalid username or password! Please try again!");
  } else {
    throw error;
  }
};

export const loginUser = (user) => {
  return axiosInstance
    .post(LOGIN_URL, qs.stringify(user))
    .then((response) => response.data)
    .catch((error) => handleLoginUserError(error));
};

export const logoutUser = () => {
  return axiosInstance.get(LOGOUT_URL).catch(() => {
    throw new Error("Something went wrong! Please try again later!");
  });
};
