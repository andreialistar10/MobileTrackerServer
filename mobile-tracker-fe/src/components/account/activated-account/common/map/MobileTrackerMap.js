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
  popupProperties,
  markerIdName,
  ...props
}) => {
  const { mapRoot } = makeMapStyle();
  const [selectedLocationKey, setSelectedLocationKey] = useState(null);
  const getCenterByMarkers = () => {
    const numberOfMarkers = markers.length;
    if (numberOfMarkers === 0) {
      return [46, 26];
    }
    let lat = 0;
    let long = 0;
    markers.forEach(({ latitude, longitude }) => {
      lat += latitude;
      long += longitude;
    });
    return [lat / numberOfMarkers, long / numberOfMarkers];
  };
  const centerCoords = center ? center : getCenterByMarkers();

  const handleOnClick = (selectedLocationKey) => {
    setSelectedLocationKey(selectedLocationKey);
  };
  return (
    <Map
      center={centerCoords}
      zoom={zoom}
      className={mapRoot}
      {...props}
      onPopupClose={() => {
        handleOnClick(null);
      }}
    >
      <TileLayer
        attribution=""
        url="https://cartodb-basemaps-{s}.global.ssl.fastly.net/light_all/{z}/{x}/{y}.png"
      />
      {markers.map((marker, index) => {
        const {
          latitude,
          longitude,
          circleColor,
          smallCircleOpacity,
          smallCircleRadius,
          mediumCircleOpacity,
          mediumCircleRadius,
          bigCircleOpacity,
          bigCircleRadius,
          ...otherProps
        } = marker;
        const id = markerIdName ? marker[markerIdName] : index;
        return (
          <MobileTrackerMarker
            key={id}
            popupProperties={popupProperties}
            latitude={latitude}
            longitude={longitude}
            circleColor={circleColor}
            smallCircleOpacity={smallCircleOpacity}
            smallCircleRadius={smallCircleRadius}
            mediumCircleOpacity={mediumCircleOpacity}
            mediumCircleRadius={mediumCircleRadius}
            bigCircleOpacity={bigCircleOpacity}
            bigCircleRadius={bigCircleRadius}
            selected={clickableMarkers && selectedLocationKey === id}
            id={id}
            onClick={() => handleOnClick(id)}
            {...otherProps}
          />
        );
      })}
    </Map>
  );
};

MobileTrackerMap.propTypes = {
  center: PropTypes.arrayOf(PropTypes.number),
  zoom: PropTypes.number,
  clickableMarkers: PropTypes.bool,
  markers: PropTypes.arrayOf(
    PropTypes.shape({
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
  ),
  popupProperties: PropTypes.arrayOf(
    PropTypes.shape({
      propertyName: PropTypes.string.isRequired,
      propertyTitle: PropTypes.string.isRequired,
    })
  ),
  markerIdName: PropTypes.string,
};

MobileTrackerMap.defaultProps = {
  zoom: 14,
  clickableMarkers: true,
  popupProperties: [],
  markers: [],
};

export default MobileTrackerMap;
