import React, { useState } from "react";
import { Map, TileLayer } from "react-leaflet";
import { makeMapStyle } from "../../../../../style/activated-account/map";
import MobileTrackerMarker from "./MobileTrackerMarker";
import PropTypes from "prop-types";

const MobileTrackerMap = ({
  clickableMarkers,
  center,
  zoom,
  markers,
  ...props
}) => {
  const { mapRoot } = makeMapStyle();
  const [selectedDevice, setSelectedDevice] = useState(null);
  const getCenterByMarkers = () => {
    let lat = 0;
    let long = 0;
    markers.forEach(({ latitude, longitude }) => {
      lat += latitude;
      long += longitude;
    });
    const numberOfMarkers = markers.length;
    return [lat / numberOfMarkers, long / numberOfMarkers];
  };
  const centerCoords = center ? center : getCenterByMarkers();

  const handleOnClick = (deviceCode) => {
    setSelectedDevice(deviceCode);
  };
  return (
    <Map
      center={centerCoords}
      zoom={zoom}
      className={mapRoot}
      {...props}
      onPopupClose={() => {handleOnClick(null)}}
    >
      <TileLayer
        attribution=""
        url="https://cartodb-basemaps-{s}.global.ssl.fastly.net/light_all/{z}/{x}/{y}.png"
      />
      {markers.map(
        ({
          deviceCode,
          name,
          timestamp,
          latitude,
          longitude,
          circleColor,
          smallCircleOpacity,
          smallCircleRadius,
          mediumCircleOpacity,
          mediumCircleRadius,
          bigCircleOpacity,
          bigCircleRadius,
        }) => (
          <MobileTrackerMarker
            key={deviceCode}
            deviceCode={deviceCode}
            name={name}
            timestamp={timestamp}
            latitude={latitude}
            longitude={longitude}
            circleColor={circleColor}
            smallCircleOpacity={smallCircleOpacity}
            smallCircleRadius={smallCircleRadius}
            mediumCircleOpacity={mediumCircleOpacity}
            mediumCircleRadius={mediumCircleRadius}
            bigCircleOpacity={bigCircleOpacity}
            bigCircleRadius={bigCircleRadius}
            selected={clickableMarkers && deviceCode === selectedDevice}
            onClick={handleOnClick}
          />
        )
      )}
    </Map>
  );
};

MobileTrackerMap.propTypes = {
  center: PropTypes.arrayOf(PropTypes.number),
  zoom: PropTypes.number,
  clickableMarkers: PropTypes.bool,
  markers: PropTypes.arrayOf(
    PropTypes.shape({
      deviceCode: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
      timestamp: PropTypes.number.isRequired,
      latitude: PropTypes.number.isRequired,
      longitude: PropTypes.number.isRequired,
      circleColor: PropTypes.string,
      smallCircleOpacity: PropTypes.number,
      smallCircleRadius: PropTypes.number,
      mediumCircleOpacity: PropTypes.number,
      mediumCircleRadius: PropTypes.number,
      bigCircleOpacity: PropTypes.number,
      bigCircleRadius: PropTypes.number,
    })
  ).isRequired,
};

MobileTrackerMap.defaultProps = {
  zoom: 14,
  clickableMarkers: true,
};

export default MobileTrackerMap;
