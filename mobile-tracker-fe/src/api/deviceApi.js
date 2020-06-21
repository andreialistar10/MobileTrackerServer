import { getApiAxiosInstance } from "./apiUtils";
import { BACKEND_URL } from "./commonConstants";

const DEVICES_URL = `${BACKEND_URL}/devices`;

const axiosInstance = getApiAxiosInstance();

export const getAllAvailableDevices = () => {
  return axiosInstance.get(DEVICES_URL);
};

export const getAllReplaceableDevices = () => {
  return axiosInstance
    .get(`${DEVICES_URL}?allow-deleted=true`)
    .then((response) => response.devices);
};

export const deleteOneDevice = (deviceCode) => {
  return axiosInstance.delete(`${DEVICES_URL}/${deviceCode}`);
};

export const updateOneDevice = (deviceCode, newDeviceInformation) => {
  return axiosInstance.put(`${DEVICES_URL}/${deviceCode}`, newDeviceInformation);
};
