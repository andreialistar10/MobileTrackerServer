import { getApiAxiosInstance } from "./apiUtils";
import { BACKEND_URL } from "./commonConstants";
import { getAddressesByLatitudeAndLongitudeDelay } from "./geocoderApi";

const axiosInstance = getApiAxiosInstance();

const LOCATIONS_URL = `${BACKEND_URL}/locations`;
const LATEST_LOCATIONS_URL = `${LOCATIONS_URL}/latest`;

export const getLatestLocations = () => {
  return axiosInstance
    .get(LATEST_LOCATIONS_URL)
    .then((locations) => locations.locations);
};

export const getLocationsByDeviceCodeAndTimeInterval = (
  deviceCode,
  start,
  end
) => {
  return axiosInstance
    .get(`${LOCATIONS_URL}/${deviceCode}?start=${start}&end=${end}`)
    .then((response) => response.locations)
    // .then((locations) => {
    //   return getAddressesByLatitudeAndLongitudeDelay(locations).then(
    //     (addresses) => {
    //       addresses.forEach(
    //         (address, i) => (locations[i].address = address.display_name)
    //       );
    //       return Promise.resolve(locations);
    //     }
    //   );
    // });
};
