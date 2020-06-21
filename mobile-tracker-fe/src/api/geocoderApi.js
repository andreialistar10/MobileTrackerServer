import axios from "axios";

const NOMINATIM_GEOCODER_API_PROVIDER = "https://nominatim.openstreetmap.org";
const LOCATIONIQ_GEOCODER_API_PROVIDER = "https://eu1.locationiq.com/v1";
const axiosInstance = axios.create();
export const getLocationiqAddressByLatitudeAndLongitude = (
  latitude,
  longitude
) => {
  return axiosInstance
    .get(
      `${LOCATIONIQ_GEOCODER_API_PROVIDER}/reverse.php?key=pk.d6fad39966cb6dfbaeba4de849417e5a&lat=${latitude}&lon=${longitude}&format=json`
    )
    .then((response) => {
      const display_name = response.data.display_name;
      const finalResponse = {
        display_name,
      };
      return Promise.resolve(finalResponse);
    });
};

export const getNominatimAddressByLatitudeAndLongitude = (
  latitude,
  longitude,
  addressDetails = false
) => {
  const addressInfo = addressDetails ? 1 : 0;
  return axiosInstance
    .get(
      `${NOMINATIM_GEOCODER_API_PROVIDER}/reverse?lat=${latitude}&lon=${longitude}&zoom=-4&addressdetails=${addressInfo}&format=json`
    )
    .then((response) => {
      const display_name = response.data.display_name;
      const address = response.data.address;
      const finalResponse = {
        display_name,
      };
      if (addressDetails) {
        finalResponse.address = address;
      }
      return Promise.resolve(finalResponse);
    });
};

export const getAddressesByLatitudeAndLongitude = (
  locations,
  addressInfo = false
) => {
  return Promise.all(
    locations.map(({ latitude, longitude }) =>
      // getNominatimAddressByLatitudeAndLongitude(latitude, longitude, addressInfo)
      getLocationiqAddressByLatitudeAndLongitude(
        latitude,
        longitude,
        addressInfo
      )
    )
  );
};

function sleep(ms = 500) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

export const getAddressesByLatitudeAndLongitudeDelay = async (locations) => {

  const addresses = [];
  for (let i=0; i<locations.length; ++i){
    const {latitude, longitude} = locations[i];
    const address = await getLocationiqAddressByLatitudeAndLongitude(latitude,longitude);
    await sleep();
    addresses.push(address);
  }
  return addresses;
};
