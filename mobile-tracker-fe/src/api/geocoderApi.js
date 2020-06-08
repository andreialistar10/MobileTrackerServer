import axios from "axios";

const GEOCODER_API_PROVIDER = "https://nominatim.openstreetmap.org";
const axiosInstance = axios.create();
export const getAddressByLatitudeAndLongitude = (
  latitude,
  longitude,
  addressDetails = false
) => {
  const addressInfo = addressDetails ? 1 : 0;
  return axiosInstance
    .get(
      `${GEOCODER_API_PROVIDER}/reverse?lat=${latitude}&lon=${longitude}&zoom=-4&addressdetails=${addressInfo}&format=json`
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
      getAddressByLatitudeAndLongitude(latitude, longitude, addressInfo)
    )
  );
};
