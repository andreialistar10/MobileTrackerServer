import { makeStyles } from "@material-ui/core/styles";
import React from "react";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import ModalDropDown from "../../common/modals/ModalDropDown";
import PropTypes from "prop-types";
import {
  FIELD_BACKGROUND_COLOR,
  FIELD_BORDER,
  FIELD_BORDER_RADIUS,
  FIELD_COLOR,
} from "../../../../../style/activated-account/constants";

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
    backgroundColor: theme.palette.background.paper,
  },
  paper: {
    width: "80%",
    maxHeight: 435,
  },
  gutters: {
    backgroundColor: FIELD_BACKGROUND_COLOR,
    paddingLeft: "5px",
    paddingRight: "5px",
    paddingTop: 0,
    paddingBottom: 0,
    border: FIELD_BORDER,
    borderRadius: FIELD_BORDER_RADIUS,
  },
  primary: {
    color: FIELD_COLOR,
    fontSize: "1rem",
    fontWeight: "bold",
    fontFamily: "Rubik-Regular",
  },
  secondary: {
    fontFamily: "Rubik-Regular",
    fontSize: "0.8rem",
    fontWeight: "bold",
    color: "#ff0303eb",
  },
}));

const SelectDeviceDialog = ({ devices, onSelect, title, className }) => {
  const classes = useStyles();
  const rootClasses = !className ? classes : `${classes} ${className}`;
  const [dropDownIsOpen, setDropDownIsOpen] = React.useState(false);
  const [replacedDevice, setReplacedDevice] = React.useState({
    name: "",
    id: "",
  });
  const handleOnClick = () => {
    setDropDownIsOpen(true);
  };

  const handleOnClose = (newValue) => {
    setDropDownIsOpen(false);

    if (newValue) {
      setReplacedDevice({ ...newValue });
      onSelect(newValue);
    }
  };

  return (
    <div className={rootClasses}>
      <ListItem
        button
        divider
        aria-haspopup="true"
        onClick={handleOnClick}
        classes={{
          gutters: classes.gutters,
        }}
      >
        <ListItemText
          primaryTypographyProps={{
            className: classes.primary,
          }}
          secondaryTypographyProps={{
            className: classes.secondary,
          }}
          primary={replacedDevice.name || "Please select a device"}
          secondary={replacedDevice.id}
        />
      </ListItem>
      <ModalDropDown
        classes={{
          paper: classes.paper,
        }}
        id="ringtone-menu"
        keepMounted
        open={dropDownIsOpen}
        onClose={handleOnClose}
        title={title}
        data={{
          keyProp: "id",
          valueProp: "name",
          elements: devices,
        }}
        defaultElementKey={"MOTR_1"}
      />
    </div>
  );
};

SelectDeviceDialog.propTypes = {
  devices: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
      date: PropTypes.string.isRequired,
    })
  ).isRequired,
  onSelect: PropTypes.func.isRequired,
  title: PropTypes.string.isRequired,
  className: PropTypes.string,
};

export default SelectDeviceDialog;
