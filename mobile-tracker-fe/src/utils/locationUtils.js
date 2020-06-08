import L from "leaflet";

export const getAddressByLatitudeAndLongitude = (lat, lng, action) => {
  const geocoder = L.Control.Geocoder.nominatim();
  geocoder.reverse({ lat: 46.766602, lng: 23.638059 }, 18, (results) => {
    let name = "";
    let html = "";
    if (results.length !== 0) {
      const result = results[0];
      if (result) {
        name = result.name;
        html = result.html;
        action({ name, html });
      }
    }
  });
};
