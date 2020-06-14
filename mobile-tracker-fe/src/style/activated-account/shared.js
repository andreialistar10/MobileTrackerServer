import { makeStyles } from "@material-ui/core/styles";
import {
  DEFAULT_FONT_SIZE_TITLE_PAGE,
  DEFAULT_BORDER_SPACING,
  DEFAULT_BORDER_SPACING_MAX_WIDTH_500px,
  MEDIUM_DEVICE_MAX_WIDTH,
  SMALL_DEVICE_MAX_WIDTH,
  FIELD_BORDER_RADIUS,
  FIELD_BORDER,
  FIELD_BACKGROUND_COLOR,
  FIELD_COLOR,
  FIELD_HEIGHT,
  LABEL_COLOR,
  COLOR_TITLE_PAGE,
  FONT_SIZE_TITLE_PAGE_MAX_WIDTH_500PX,
  FONT_FAMILY,
} from "./constants";

export const makeSharedStyle = makeStyles({
  behindContent: {
    position: "absolute",
    bottom: "2rem",
    top: "2rem",
    right: "0",
    height: "calc(100% - 4rem)",
    boxSizing: "border-box",
    paddingLeft: "25%",
    width: "55%",
    color: "rgba(192,192,192,0.35)",
    zIndex: 0,
  },
  icon: {
    width: "100%",
    height: "100%",
  },
  defaultModalBody: {
    position: "relative",
    overflow: "auto",
    width: "55%",
    height: "60vh",
    minHeight: "300px",
    borderRadius: "6px",
    boxSizing: "border-box",
    [`@media (max-width:${MEDIUM_DEVICE_MAX_WIDTH})`]: {
      width: "65%",
    },
    [`@media (max-width:650px)`]: {
      width: "75%",
    },
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      width: "90%",
      height: "55vh",
    },
  },
  modalCloseIcon: {
    position: "absolute",
    top: 0,
    right: 0,
    "&:focus": { outline: "none" },
  },
  modalTitle: {
    width: "100%",
    fontWeight: "bold",
    fontSize: DEFAULT_FONT_SIZE_TITLE_PAGE,
    color: "rgb(101, 92, 92)",
    marginTop: "3vh",
    marginBottom: "3vh",
    fontFamily: FONT_FAMILY,
    // color: "rgba(15, 10, 190, 1)",
    textAlign: "center",
  },
  modalWrapper: {
    minHeight: "fit-content",
    zIndex: 4,
    width: "100%",
    height: "100%",
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
  },

  cell: {
    display: "table-cell",
    verticalAlign: "middle",
    paddingBottom: "15px",
  },

  label: {
    fontWeight: "bold",
    fontFamily: FONT_FAMILY,
    // fontFamily: "Segoe UI",
    fontSize: "1.2rem",
    color: LABEL_COLOR,
    display: "table-cell",
    paddingRight: DEFAULT_BORDER_SPACING,
    margin: "auto",
    paddingBottom: "15px",
    verticalAlign: "middle",
    [`@media (max-width:600px)`]: {
      fontSize: "1rem",
      paddingRight: "0.65rem",
    },
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      fontSize: "0.9rem",
      paddingRight: DEFAULT_BORDER_SPACING_MAX_WIDTH_500px,
    },
  },
  textField: {
    margin: 0,
    justifyContent: "center",
  },
  primitiveInput: {
    paddingLeft: "5px",
    paddingRight: "5px",
    paddingTop: 0,
    paddingBottom: 0,
  },
  input: {
    backgroundColor: FIELD_BACKGROUND_COLOR,
    border: FIELD_BORDER,
    fontSize: "1.2rem",
    fontWeight: "500",
    color: FIELD_COLOR,
    fontFamily: FONT_FAMILY,
    borderRadius: FIELD_BORDER_RADIUS,
    height: FIELD_HEIGHT,
  },
  inputError: {
    borderColor: "#be4b49",
    borderWidth: "1.5px",
  },
  textFieldWrapper: {
    display: "table-row",
  },
  errorIcon: {
    color: "#be4b49",
  },
  formContainer: {
    display: "table",
    borderSpacing: DEFAULT_BORDER_SPACING,
    [`@media (max-width:900px)`]: {
      borderSpacing: "1rem",
    },
    [`@media (max-width:880px)`]: {
      borderSpacing: "0.8rem",
    },
    [`@media (max-width:850px)`]: {
      borderSpacing: "0.7rem",
    },
    [`@media (max-width:600px)`]: {
      borderSpacing: "0.65rem",
    },
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      borderSpacing: DEFAULT_BORDER_SPACING_MAX_WIDTH_500px,
    },
  },
  button: {
    backgroundColor: "rgba(5, 152, 230,1)",
    borderRadius: "8px",
    padding: "10px",
    fontSize: "1.1rem",
    textTransform: "uppercase",
    fontWeight: "bold",
    color: "#fff",
    boxShadow:
      "0px 3px 5px -1px rgba(0,0,0,0.2), 0px 6px 10px 0px rgba(0,0,0,0.14), 0px 1px 18px 0px rgba(0,0,0,0.12)",
    "&:focus": { outline: "none" },
    "&:hover": {
      backgroundColor: "rgba(121,215,187,0.5)",
    },
    "&:disabled": {
      color: "white",
      backgroundColor: "rgba(121,215,187,0.5)",
    },
    marginBottom: DEFAULT_BORDER_SPACING,
  },

  modalTitlePage: {
    color: COLOR_TITLE_PAGE,
    fontFamily: FONT_FAMILY,
    fontSize: DEFAULT_FONT_SIZE_TITLE_PAGE,
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      fontSize: FONT_SIZE_TITLE_PAGE_MAX_WIDTH_500PX,
      marginBottom: FONT_SIZE_TITLE_PAGE_MAX_WIDTH_500PX,
    },
  },

  inlineFormLabel: {
    paddingBottom: "35px",
    color: COLOR_TITLE_PAGE,
  },

  inlineFormTextField: {
    paddingRight: "40px",
    [`@media (min-width:1200px)`]: {
      paddingRight: "45px",
    },
    [`@media (min-width:1400px)`]: {
      paddingRight: "50px",
    },
  },

  inlineFormMaterialUITextField: {
    [`@media (min-width:1200px)`]: {
      minWidth: "300px",
    },
    [`@media (min-width:1400px)`]: {
      minWidth: "350px",
    },
  },
  inlineFormButton: {
    fontSize: "1rem",
    textTransform: "capitalize",
    float: "right",
    marginRight: "1.2rem",
    [`@media (max-width:900px)`]: {
      marginRight: "1rem",
    },
    [`@media (max-width:880px)`]: {
      marginRight: "0.8rem",
    },
    [`@media (max-width:850px)`]: {
      marginRight: "0.7rem",
    },
    [`@media (max-width:600px)`]: {
      marginRight: "0.65rem",
    },
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      marginRight: DEFAULT_BORDER_SPACING_MAX_WIDTH_500px,
    },
  },
  whiteColor: {
    color: "white",
  },
});
