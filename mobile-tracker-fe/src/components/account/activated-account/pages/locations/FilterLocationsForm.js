import React from "react";
import SelectDeviceDialog from "../devices/SelectDeviceDialog";
import MobileTrackerButton from "../../common/form/MobileTrackerButton";
import { makeLocationsStyle } from "../../../../../style/activated-account/pages/locations";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import PropTypes from "prop-types";
import { DateTimePicker, MuiPickersUtilsProvider } from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core";
import { COLOR_TITLE_PAGE } from "../../../../../style/activated-account/constants";

const theme = createMuiTheme({
  palette: {
    primary: { main: COLOR_TITLE_PAGE },
  },
  overrides: {
    MuiInputLabel: {
      root: {
        color: COLOR_TITLE_PAGE,
        fontWeight: "bold",
        fontSize: "1.2rem",
      },
    },
    MuiPickersToolbar: {
      toolbar: {
        backgroundColor: "rgb(30, 114, 162)",
      },
    },
    MuiInput: {
      input: {
        color: "#f1e9ea",
      },
    },
  },
  pickersToolbar: {
    toolbar: {
      backgroundColor: "rgba(76,76,76,1)",
    },
  },
});
const FilterLocationForm = ({
  onSubmit,
  onDeviceSelected,
  devices,
  startDate,
  endDate,
  onStartDateChange,
  onEndDateChange,
  disableSubmit,
}) => {
  const { inlineFormButton } = makeSharedStyle();
  const {
    formContainer,
    formTitle,
    submitButton,
    locationFormElement,
    elementWidth,
    selectInput,
    secondSelectInput,
  } = makeLocationsStyle();
  return (
    <>
      <h2 className={formTitle}>Find device locations</h2>
      <form onSubmit={onSubmit} className={formContainer}>
        <SelectDeviceDialog
          onSelect={onDeviceSelected}
          devices={devices}
          className={`${locationFormElement} ${elementWidth}`}
          title="Select one of your device"
          primaryTypographyClassName={selectInput}
          secondaryTypographyClassName={secondSelectInput}
        />
        <MuiThemeProvider theme={theme}>
          <MuiPickersUtilsProvider utils={DateFnsUtils}>
            <DateTimePicker
              label="Start date:"
              format="dd-MM-yyyy HH:mm"
              placeholder="SELECT"
              value={startDate}
              className={`${locationFormElement} ${elementWidth}`}
              onChange={onStartDateChange}
              disableFuture
              maxDate={endDate}
              ampm={false}
            />
            <DateTimePicker
              label="End date:"
              format="dd-MM-yyyy HH:mm"
              value={endDate}
              className={`${locationFormElement} ${elementWidth}`}
              onChange={onEndDateChange}
              disableFuture
              minDate={startDate}
              ampm={false}
            />
          </MuiPickersUtilsProvider>
          <MobileTrackerButton
            textOnDisable="Find locations"
            text="Find locations"
            disabled={disableSubmit}
            className={`${inlineFormButton} ${submitButton} ${locationFormElement}`}
          />
        </MuiThemeProvider>
      </form>
    </>
  );
};

FilterLocationForm.propTypes = {
  disableSubmit: PropTypes.bool.isRequired,
  startDate: PropTypes.instanceOf(Date).isRequired,
  endDate: PropTypes.instanceOf(Date).isRequired,
  onStartDateChange: PropTypes.func.isRequired,
  onEndDateChange: PropTypes.func.isRequired,
  onDeviceSelected: PropTypes.func.isRequired,
  onSubmit: PropTypes.func.isRequired,
  devices: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
    })
  ).isRequired,
};

export default FilterLocationForm;
