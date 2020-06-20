import { getApiAxiosInstance } from "./apiUtils";
import { BACKEND_URL } from "./commonConstants";

const DEVICES_URL = `${BACKEND_URL}/devices`;

const axiosInstance = getApiAxiosInstance();

export const getAllDevices = () => {
  return axiosInstance.get(DEVICES_URL);
};
