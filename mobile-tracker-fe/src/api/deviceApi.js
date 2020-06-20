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

const DEVICES_URL = `${BACKEND_URL}/devices`;

const axiosInstance = axios.create();
axiosInstance.interceptors.request.use((configs) => {
  if (configs.method !== "GET" || configs.method !== "DELETE") {
    configs.headers.common[CONTENT_TYPE_HEADER] = APPLICATION_JSON;
  }
  const tokenApi = getApiToken();
  configs.headers.common[
    AUTHORIZATION_HEADER
  ] = `${PREFIX_AUTHORIZATION_HEADER_VALUE} ${tokenApi}`;
  return configs;
});

axiosInstance.interceptors.response.use(response => response.data, error => {
    handleConnectionAndServerErrors(error);
    if (error.response.status === 401){
      throw new Error("Your session was expired! Please login again!");
    }
    throw new Error("Something went wrong! Please try again later")
});

export const getAllDevices = () => {
  return axiosInstance.get(DEVICES_URL);
};

