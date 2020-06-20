import * as types from "./actionTypes";
import { getAllDevices } from "../../api/deviceApi";
import { getDevicesLastUpdate } from "../../index";

const DEVICES_CACHE_TIME = 1000 * 60;

export function getAllUserDevicesSuccess(devices) {
  return { type: types.GET_ALL_DEVICES, payload: devices };
}

export function updateDeviceSuccess(device) {
  return { type: types.UPDATE_DEVICE, payload: device };
}

export function addDeviceSuccess(device) {
  return { type: types.ADD_DEVICE, payload: device };
}

export function getAllDevicesFromCache() {
  return { type: types.GET_ALL_DEVICES_FROM_CACHE };
}

export function getAllUserDevices() {
  const currentTime = new Date().getTime();
  const lastUpdate = getDevicesLastUpdate();
  if (currentTime - lastUpdate > DEVICES_CACHE_TIME) {
    return (dispatch) =>
      getAllDevices().then((response) => {
        const devices = {
          devicesList: response.devices,
          lastUpdate: new Date().getTime(),
        };
        dispatch(getAllUserDevicesSuccess(devices));
      });
  } else {
    return (dispatch) => {
      dispatch(getAllDevicesFromCache());
      return Promise.resolve();
    };
  }
}

export function addDevice(device) {
  return (dispatch) => {
    dispatch(addDeviceSuccess(device));
  };
}

export function updateDevice(device) {
  return (dispatch) => {
    dispatch(updateDeviceSuccess(device));
  };
}
