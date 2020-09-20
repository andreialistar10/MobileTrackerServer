import { makeStyles } from "@material-ui/core/styles";
import {
  DEFAULT_BORDER_SPACING,
  DEFAULT_BORDER_SPACING_MAX_WIDTH_500px,
  FIELD_BACKGROUND_COLOR,
  FIELD_BORDER,
  FIELD_BORDER_RADIUS,
  FIELD_COLOR,
  FIELD_HEIGHT, FONT_FAMILY,
  LABEL_COLOR,
  SMALL_DEVICE_MAX_WIDTH,
} from "../constants";

export const makeDevicesStyle = makeStyles({
  root: {
    minHeight: "100%",
    position: "relative",
    width: "100%",
  },
  deviceTableAddButton: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    fontSize: "1rem",
    marginLeft: "0.7rem",
    marginRight: "calc(0.7rem - 8px)",
    fontFamily: FONT_FAMILY,
    color: "rgb(17, 22, 31)",
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      fontSize: "0.7rem",
      marginLeft: "calc(0.5rem - 8px)",
    },
    "&:focus": { outline: "none" },
  },
  deviceTableAddButtonText: {
    marginLeft: "0.2rem",
    [`@media (max-width:710px)`]: {
      display: "none",
    },
  },
  pairingImage: {
    position: "absolute",
    top: "4vh",
    right: "4vh",
    left: "4vw",
    height: "calc(100% - 8vh)",
    width: "calc(100% - 8vw)",
  },
  onPairing: {
    opacity: "0.8",
  },
  onNonPairing: {
    opacity: "0.09",
  },
  deviceFormContainer: {
    width: "100%",
    height: "100%",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    flexDirection: "column",
  },
  switchContainer: {
    width: "100%",
    paddingRight: DEFAULT_BORDER_SPACING,
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      paddingRight: DEFAULT_BORDER_SPACING_MAX_WIDTH_500px,
    },
  },
  switchWrapper: {
    float: "right",
  },
  switchLabel: {
    margin: 0,
    marginRight: "0!important",
    userSelect: "none",
    fontFamily: FONT_FAMILY,
    textAlign: "right",
    color: LABEL_COLOR,
    fontWeight: "bold",
    fontSize: "1rem",
    [`@media (max-width:600px)`]: {
      fontSize: "0.9rem",
    },
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      fontSize: "0.8rem",
    },
    "&:hover": {
      color: FIELD_COLOR,
    },
  },
  select: {
    justifyContent: "center",
    width: "100%",
    height: FIELD_HEIGHT,
    margin: 0,
    backgroundColor: FIELD_BACKGROUND_COLOR,
    paddingLeft: "5px",
    paddingRight: "5px",
    paddingTop: 0,
    paddingBottom: 0,
    border: FIELD_BORDER,
    borderRadius: FIELD_BORDER_RADIUS,
    color: FIELD_COLOR,
    fontSize: "1.1rem",
    fontWeight: "500",
  },
});
