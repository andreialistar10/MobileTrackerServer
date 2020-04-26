import { makeStyles } from "@material-ui/core/styles";
import {
  COLOR_TITLE_PAGE,
  DEFAULT_FONT_SIZE_TITLE_PAGE,
  FONT_SIZE_TITLE_PAGE_MAX_WIDTH_500PX,
  PADDING_LEFT_TITLE_PAGE,
  SMALL_DEVICE_MAX_WIDTH,
} from "./constants";

export const makeTitlePageStyle = makeStyles({
  root: {
    margin: 0,
    fontWeight: 500,
    color: COLOR_TITLE_PAGE,
    letterSpacing: 0,
    paddingLeft: PADDING_LEFT_TITLE_PAGE,
    paddingBottom: "15px",
    boxSizing: "border-box",
    textTransform: "capitalize",
    fontSize: DEFAULT_FONT_SIZE_TITLE_PAGE,
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      fontSize: FONT_SIZE_TITLE_PAGE_MAX_WIDTH_500PX,
    },
  },
  titleText: {
    verticalAlign: "text-top",
  },
});
