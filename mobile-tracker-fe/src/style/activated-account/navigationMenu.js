import { makeStyles } from "@material-ui/core/styles";
import {
  DEFAULT_HEADER_HEIGHT_VALUE,
  HEADER_HEIGHT_VALUE_MAX_WIDTH_500PX,
  SMALL_DEVICE_MAX_WIDTH,
} from "./constants";

const selectedOrHoverListItem = "rgba(171, 232, 227,0.85)";

export const makeNavigationMenuStyle = makeStyles({
  root: {
    width: "100%",
    minHeight: "100%",
    display: "flex",
    flexDirection: "column",
    backgroundColor: "#2e303e",
    "-webkit-box-shadow": "1px 3px 6px -3px rgba(0,0,0,0.75)",
    "-moz-box-shadow": "1px 3px 6px -3px rgba(0,0,0,0.75)",
    "box-shadow": "1px 3px 6px -3px rgba(0,0,0,0.75)",
  },
  list: {
    listStyleType: "none",
    margin: "auto",
    textAlign: "center",
    padding: 0,
    width: "100%",
  },
  listItem: {
    width: "100%",
    height: DEFAULT_HEADER_HEIGHT_VALUE,
    position: "relative",
    "&:hover": {
      backgroundColor: selectedOrHoverListItem,
    },
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      height: HEADER_HEIGHT_VALUE_MAX_WIDTH_500PX,
    },
  },
  selectedListItem: {
    borderLeft: "4px solid rgb(5, 152, 230)",
    backgroundColor: selectedOrHoverListItem,
  },
  anchor: {
    width: "100%",
    height: "100%",
    display: "flex",
    "&:hover": {
      color: "#007bff",
    },
  },
  icon: {
    width: "2rem",
    height: "2rem",
    margin: "auto",
    // color: "transparent",
  },
  anchorText: {
    position: "absolute",
    width: "100%",
    left: 0,
    bottom: 0,
    textAlign: "center",
  },
});
