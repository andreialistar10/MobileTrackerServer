import { makeStyles } from "@material-ui/core/styles";
import {
  DEFAULT_HEADER_HEIGHT_VALUE,
  FONT_SIZE_TITLE_PAGE_MAX_WIDTH_500PX,
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
      minHeight: `calc(100% - ${HEADER_HEIGHT_VALUE_MAX_WIDTH_500PX})`,
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
    paddingLeft: RIGHT_SIDE_PADDING,
    paddingRight: RIGHT_SIDE_PADDING,
    paddingBottom: RIGHT_SIDE_PADDING,
    paddingTop: RIGHT_SIDE_PADDING,
    width: `calc(100% - ${DEFAULT_HEADER_HEIGHT_VALUE})`,
    minHeight: "100%",
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      width: `calc(100% - ${HEADER_HEIGHT_VALUE_MAX_WIDTH_500PX})`,
      paddingBottom: FONT_SIZE_TITLE_PAGE_MAX_WIDTH_500PX,
      paddingTop: FONT_SIZE_TITLE_PAGE_MAX_WIDTH_500PX,
    },
  },
  rightSideContentWrapper: {
    position: "relative",
    width: "100%",
    // backgroundColor: "rgba(207, 241, 238, 1)",
    backgroundImage: `linear-gradient(to right, rgba(207, 241, 238, 0.65), rgba(207, 241, 238, 1))`,
    paddingLeft: '3.5%',
    paddingRight: '3.5%',
    paddingTop: '4vh',
    paddingBottom: '4vh',
    border: "1.4px solid rgb(213, 228, 227)",
    boxSizing: "border-box",
    "-webkit-box-shadow": "0px 1px 6px -2px rgba(0,0,0,0.75)",
    "-moz-box-shadow": "0px 1px 6px -2px rgba(0,0,0,0.75)",
    "box-shadow": "0px 1px 6px -2px rgba(0,0,0,0.75)",
    minHeight: "85%",
    borderRadius: 6,
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      paddingLeft: '5%',
    }
  },
});
