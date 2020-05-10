import { makeStyles } from "@material-ui/core/styles";
import { COLOR_TITLE_PAGE, SMALL_DEVICE_MAX_WIDTH } from "../constants";

export const makeDevicesStyle = makeStyles({
  root: {
    minHeight: "100%",
    position: "relative",
  },
  deviceTableAddButton: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    fontSize: "1rem",
    marginLeft: "0.7rem",
    marginRight: "calc(0.7rem - 8px)",
    fontFamily: "Rubik-Regular",
    color: COLOR_TITLE_PAGE,
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
});
