import React from "react";
import PropTypes from "prop-types";
import { CircleMarker, Popup } from "react-leaflet";
import { makeMapStyle } from "../../../../../style/activated-account/map";
const moment = require("moment");

const MobileTrackerMarker = ({
  deviceCode,
  name,
  timestamp,
  latitude: lat,
  longitude: lng,
  circleColor,
  selected,
  smallCircleRadius,
  smallCircleOpacity,
  mediumCircleRadius,
  mediumCircleOpacity,
  bigCircleRadius,
  bigCircleOpacity,
  onClick,
  ...props
}) => {
  const { popupText } = makeMapStyle();
  const date = moment.unix(timestamp / 1000);
  return (
    <>
      <CircleMarker
        center={{ lat, lng }}
        fillColor={circleColor}
        radius={smallCircleRadius}
        stroke={false}
        fillOpacity={smallCircleOpacity}
      />
      {selected && (
        <CircleMarker
          center={{ lat, lng }}
          fillColor={circleColor}
          radius={mediumCircleRadius}
          stroke={false}
          fillOpacity={mediumCircleOpacity}
        />
      )}

      <CircleMarker
        center={{ lat, lng }}
        fillColor={circleColor}
        radius={bigCircleRadius}
        stroke={false}
        fillOpacity={bigCircleOpacity}
        onClick={() => onClick(deviceCode)}
        {...props}
      >
        <Popup>
          <div className={popupText}>
            <strong>ID:{" "}</strong>
            {deviceCode}
          </div>
          <div className={popupText}>
            <strong>Name:{" "}</strong>
            {name}
          </div>
          <div className={popupText}>
            <strong>Date:{" "}</strong>
            {date.format("DD-MM-YYYY HH:mm")}
          </div>
        </Popup>
      </CircleMarker>
    </>
  );
};

MobileTrackerMarker.propTypes = {
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
  selected: PropTypes.bool,
  onClick: PropTypes.func,
};

MobileTrackerMarker.defaultProps = {
  circleColor: "red",
  smallCircleRadius: 6,
  smallCircleOpacity: 1,
  mediumCircleOpacity: 0.5,
  mediumCircleRadius: 16,
  bigCircleOpacity: 0.35,
  bigCircleRadius: 27,
  selected: false,
  onClick: () => {},
};

export default MobileTrackerMarker;
