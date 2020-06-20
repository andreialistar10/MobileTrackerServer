import * as types from "./actionTypes";
import { getAllDevices } from "../../api/deviceApi";

export function getAllUserDevicesSuccess(devices) {
  return { type: types.GET_ALL_DEVICES, payload: devices.devices };
}

export function updateDeviceSuccess(device) {
  return { type: types.UPDATE_DEVICE, payload: device };
}

export function addDeviceSuccess(device) {
  return { type: types.ADD_DEVICE, payload: device };
}

export function getAllUserDevices() {
  return (dispatch) => {
    return getAllDevices().then((response) =>
        dispatch(getAllUserDevicesSuccess(response))
    );
  };
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
