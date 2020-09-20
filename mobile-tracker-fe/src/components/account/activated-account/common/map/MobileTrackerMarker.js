import React from "react";
import PropTypes from "prop-types";
import { CircleMarker, Popup } from "react-leaflet";
import { makeMapStyle } from "../../../../../style/activated-account/map";

const MobileTrackerMarker = ({
  id,
  popupProperties,
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
        onClick={() => onClick(id)}
        {...props}
      >
        {popupProperties.length !== 0 && (
          <Popup>
            {popupProperties.map((property) => {
              return (
                <div className={popupText} key={property.propertyName}>
                  <strong>{`${property.propertyTitle} `}</strong>
                  {props[property.propertyName]}
                </div>
              );
            })}
          </Popup>
        )}
      </CircleMarker>
    </>
  );
};

MobileTrackerMarker.propTypes = {
  id: PropTypes.any.isRequired,
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
  popupProperties: PropTypes.arrayOf(
    PropTypes.shape({
      propertyName: PropTypes.string.isRequired,
      propertyTitle: PropTypes.string.isRequired,
    })
  ).isRequired,
};

MobileTrackerMarker.defaultProps = {
  circleColor: "rgb(35,125,210)",
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
