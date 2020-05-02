import { makeStyles } from "@material-ui/core/styles";
import {
  DEFAULT_HEADER_HEIGHT_VALUE,
  HEADER_HEIGHT_VALUE_MAX_WIDTH_500PX,
  RIGHT_SIDE_PADDING,
  SMALL_DEVICE_MAX_WIDTH,
} from "./constants";

export const makeActivatedAccountStyle = makeStyles({
  root: {
    margin: "0",
    minHeight: "100%",
    height: "100%",
    fontFamily: "Rubik Regular",
  },
  header: {
    height: DEFAULT_HEADER_HEIGHT_VALUE,
    width: "100%",
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      height: HEADER_HEIGHT_VALUE_MAX_WIDTH_500PX,
    },
  },
  belowHeader: {
    width: "100%",
    display: "flex",
    minHeight: `calc(100% - ${DEFAULT_HEADER_HEIGHT_VALUE})`,
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      height: `calc(100% - ${HEADER_HEIGHT_VALUE_MAX_WIDTH_500PX})`,
    },
  },
  leftSide: {
    width: DEFAULT_HEADER_HEIGHT_VALUE,
    fontSize: "0.9em",
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      width: HEADER_HEIGHT_VALUE_MAX_WIDTH_500PX,
      fontSize: "0.7em",
    },
  },
  rightSide: {
    backgroundColor: "rgba(226,223,178,0.26)",
    position: "relative",
    boxSizing: "border-box",
    padding: RIGHT_SIDE_PADDING,
    width: `calc(100% - ${DEFAULT_HEADER_HEIGHT_VALUE})`,
    minHeight: "100%",
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      width: `calc(100% - ${HEADER_HEIGHT_VALUE_MAX_WIDTH_500PX})`,
    },
  },
});
