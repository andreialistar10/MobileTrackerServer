import React, { useState } from "react";
import PropTypes from "prop-types";
import MobileTrackerModal from "../../common/modals/MobileTrackerModal";
import MaterialTable from "material-table";
import { FONT_FAMILY } from "../../../../../style/activated-account/constants";
import { makeLocationsStyle } from "../../../../../style/activated-account/pages/locations";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core";
import { compose, setDisplayName } from "recompose";
import MobileTrackerMap from "../../common/map/MobileTrackerMap";

const popupProperties = [
  {
    propertyName: "date",
    propertyTitle: "Date:",
  },
  {
    propertyName: "address",
    propertyTitle: "Address:",
  },
];

const columns = [
  {
    title: "Date",
    field: "date",
    sorting: false,
    editable: "never",
    searchable: false,
  },
  {
    title: "location (latitude, longitude)",
    field: "locationCoordinates",
    editable: "never",
    searchable: false,
    sorting: false,
  },
  {
    title: "location address",
    field: "address",
    editable: "never",
    sorting: false,
    searchable: false,
  },
];

const headerStyle = {
  color: "#ffffff",
  textTransform: "capitalize",
  fontWeight: "bold",
  fontFamily: FONT_FAMILY,
  textAlign: "center",
  fontSize: "1.1rem",
  backgroundColor: "#156082",
};
const cellStyle = {};

const theme = createMuiTheme({
  palette: {
    secondary: {
      main: "#767c82",
    },
  },
  overrides: {
    MuiDialogContent: {
      root: {
        minWidth: "100%",
      },
    },
    MuiTableCell: {
      alignLeft: {
        fontFamily: FONT_FAMILY,
        fontWeight: "500",
        fontSize: "1.1rem",
        textAlign: "center",
      },
    },
    MuiTableRow: {
      root: {
        color: "#ffffff",
        backgroundColor: "#156082",
      },
    },
    MuiToolbar: {
      gutters: {
        paddingRight: "57px !important",
      },
    },
  },
});

const LocationsModal = ({ locations, open, deviceName, onClose }) => {
  const { locationModal } = makeLocationsStyle();
  const [selectedLocations, setSelectedLocations] = useState([]);
  const [openMap, setOpenMap] = useState(false);
  const onShowMapClick = (event, locations) => {
    const finalLocations = Array.isArray(locations)
      ? [...locations]
      : [locations];
    setSelectedLocations(finalLocations);
    setOpenMap(true);
  };
  return (
    <MuiThemeProvider theme={theme}>
      <MobileTrackerModal
        handleOnClose={onClose}
        title={`${deviceName}'s phone locations`}
        isOpen={open}
        className={locationModal}
      >
        <MaterialTable
          columns={columns}
          data={locations}
          options={{
            search: false,
            minBodyWidth: 350,
            minBodyHeight: "50vh",
            maxBodyHeight: "50vh",
            emptyRowsWhenPaging: false,
            draggable: false,
            showTitle: false,
            selection: true,
            actionsColumnIndex: -1,
            headerStyle,
            cellStyle,
            paging: false,
            actionsCellStyle: {
              minWidth: "100px",
            },
          }}
          localization={{
            body: {
              emptyDataSourceMessage: "No location found",
            },
            header: {
              actions: "See on map",
            },
            toolbar: {
              nRowsSelected: "{0} location(s) selected",
            },
          }}
          actions={[
            {
              icon: compose(setDisplayName("SeeAllLocation"))(() => {
                return (
                  <div>
                    <img
                      src="http://localhost:3002/images/gps.svg"
                      style={{ width: "30px", height: "30px" }}
                      alt="See all selected locations on map"
                    />
                  </div>
                );
              }),
              tooltip: "See all selected locations on the map",
              onClick: onShowMapClick,
            },
            {
              icon: compose(setDisplayName("SeeOneLocation"))(() => {
                return (
                  <div>
                    <img
                      src="http://localhost:3002/images/gps.svg"
                      style={{ width: "25px", height: "25px" }}
                      alt="See on map"
                    />
                  </div>
                );
              }),
              tooltip: "See location on the map",
              onClick: onShowMapClick,
              position: "row",
            },
          ]}
        />
      </MobileTrackerModal>
      <MobileTrackerModal
        handleOnClose={() => {
          setOpenMap(false);
          setSelectedLocations([]);
        }}
        title={`${deviceName}'s phone locations on the map`}
        isOpen={openMap}
        className={locationModal}
      >
        <MobileTrackerMap
          markers={selectedLocations}
          popupProperties={popupProperties}
        />
      </MobileTrackerModal>
    </MuiThemeProvider>
  );
};

LocationsModal.propTypes = {
  deviceName: PropTypes.string.isRequired,
  open: PropTypes.bool.isRequired,
  locations: PropTypes.arrayOf(
    PropTypes.shape({
      latitude: PropTypes.number.isRequired,
      longitude: PropTypes.number.isRequired,
      date: PropTypes.string.isRequired,
      address: PropTypes.string.isRequired,
    })
  ).isRequired,
  onClose: PropTypes.func.isRequired,
};

export default LocationsModal;
