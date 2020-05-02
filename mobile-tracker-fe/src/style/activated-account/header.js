import { makeStyles } from "@material-ui/core/styles";
import {
  COLOR_TITLE_PAGE,
  DEFAULT_FONT_SIZE_TITLE_PAGE,
  DEFAULT_HEADER_HEIGHT_VALUE,
  FONT_SIZE_TITLE_PAGE_MAX_WIDTH_500PX,
  HEADER_GRADIENT_COLOR,
  HEADER_HEIGHT_VALUE_MAX_WIDTH_500PX,
  DEFAULT_HEADER_LOGO_DIMENSIONS,
  PADDING_LEFT_TITLE_PAGE,
  SMALL_DEVICE_MAX_WIDTH,
  HEADER_LOGO_DIMENSIONS_MAX_WIDTH_500PX,
  MEDIUM_DEVICE_MAX_WIDTH,
  TINY_DEVICE_MAX_WIDTH,
  RIGHT_SIDE_PADDING,
} from "./constants";

export const makeHeaderStyle = makeStyles({
  root: {
    position: "relative",
    display: "flex",
    width: "100%",
    height: "100%",
    backgroundImage: `linear-gradient(to right, ${HEADER_GRADIENT_COLOR})`,
    paddingLeft: `calc(${DEFAULT_HEADER_HEIGHT_VALUE} + ${RIGHT_SIDE_PADDING} + ${PADDING_LEFT_TITLE_PAGE})`,
    boxSizing: "border-box",
    "-webkit-box-shadow": "0px 1px 6px -3px rgba(0,0,0,0.75)",
    "-moz-box-shadow": "0px 1px 6px -3px rgba(0,0,0,0.75)",
    "box-shadow": "0px 1px 6px -3px rgba(0,0,0,0.75)",
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      paddingLeft: `calc(${HEADER_HEIGHT_VALUE_MAX_WIDTH_500PX} + ${RIGHT_SIDE_PADDING} + ${PADDING_LEFT_TITLE_PAGE})`,
    },
  },
  logo: {
    marginTop: "auto",
    marginBottom: "auto",
    height: DEFAULT_HEADER_LOGO_DIMENSIONS,
    width: DEFAULT_HEADER_LOGO_DIMENSIONS,
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      height: HEADER_LOGO_DIMENSIONS_MAX_WIDTH_500PX,
      width: HEADER_LOGO_DIMENSIONS_MAX_WIDTH_500PX,
    },
  },
  header: {
    marginLeft: "0.25rem",
    marginTop: "auto",
    marginBottom: "auto",
    fontSize: DEFAULT_FONT_SIZE_TITLE_PAGE,
    fontWeight: 500,
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      fontSize: FONT_SIZE_TITLE_PAGE_MAX_WIDTH_500PX,
    },
  },
  headerText: {
    verticalAlign: "top",
  },
  prefixHeaderText: {
    color: "rgba(15, 10, 190, 1)",
  },
  suffixHeaderText: {
    color: COLOR_TITLE_PAGE,
  },
  logoutButtonContainer: {
    width: "100%",
    margin: "auto",
    display: "flex",
    justifyContent: "flex-end",
    boxSizing: "border-box",
    paddingRight: "2.5rem",
    [`@media (max-width:${MEDIUM_DEVICE_MAX_WIDTH})`]: {
      paddingRight: "2rem",
    },
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      paddingRight: "1.5rem",
    },
    [`@media (max-width:${TINY_DEVICE_MAX_WIDTH})`]: {
      paddingRight: "0.5rem",
    },
  },
  logoutButton: {
    width: DEFAULT_HEADER_LOGO_DIMENSIONS,
    height: DEFAULT_HEADER_LOGO_DIMENSIONS,
    padding: 0,
    "&:focus": {
      outline: "none",
    },
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      width: HEADER_LOGO_DIMENSIONS_MAX_WIDTH_500PX,
      height: HEADER_LOGO_DIMENSIONS_MAX_WIDTH_500PX,
    },
  },
  logoutIcon: {
    width: "100%",
    height: "100%",
  },
});
