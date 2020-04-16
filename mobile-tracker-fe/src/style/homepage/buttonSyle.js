import { makeStyles } from "@material-ui/core/styles";

const borderRadiusValue = "8px";
const defaultLinkButtonStyle = {
  textTransform: "capitalize",
  fontWeight: "bold",
  height: "3.2rem",
  padding: "2px",
  fontSize: "1.0rem",
  width: "50%",
  borderRadius: "0px",
  color: "white",
  "&:focus": { outline: "none" },
  "&:disabled": {
    color: "white",
    opacity: 0.8,
    backgroundColor: "#b3abab",
  },
  "&:hover": {
    backgroundColor: "rgba(238,118,51,0.6)",
  },
};

export const makeButtonStyle = makeStyles({
  leftButton: {
    ...defaultLinkButtonStyle,
    borderTopLeftRadius: borderRadiusValue,
    borderBottomLeftRadius: borderRadiusValue,
  },
  rightButton: {
    ...defaultLinkButtonStyle,
    borderTopRightRadius: borderRadiusValue,
    borderBottomRightRadius: borderRadiusValue,
  },
  submitButton: {
    marginTop: "2.0rem",
    marginBottom: "2.0rem",
    width: "10rem",
    height: "3rem",
    borderRadius: "8px",
    padding: "2px",
    fontSize: "1.4rem",
    textTransform: "capitalize",
    fontWeight: "bold",
    backgroundColor: "#46ccf2",
    color: "white",
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
  },
});
