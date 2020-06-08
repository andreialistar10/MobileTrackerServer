import { makeStyles } from "@material-ui/core/styles";
import { COLOR_TITLE_PAGE } from "../constants";

export const makeLocationsStyle = makeStyles({
  wrapper: {
    width: "100%",
    height: "auto",
    display: "flex",
    alignItems: "center",
  },
  mapContainer: {
    width: "60%",
    height: "55vh",
    marginRight: "10%",
    [`@media (max-width:700px)`]: {
      width: "55%",
      height: "50vh",
    },
    [`@media (max-width:600px)`]: {
      width: "52%",
      height: "45vh",
    },
  },
  rightSide: {
    width: "30%",
    backgroundColor: "#ffffff9f",
    minHeight: "55vh",
    margin: 0,
    padding: "1%",
    boxShadow:
      "0px 3px 5px -1px rgba(0,0,0,0.2), 0px 6px 10px 0px rgba(0,0,0,0.14), 0px 1px 18px 0px rgba(0,0,0,0.12)",
    borderRadius: "10px",
    [`@media (max-width:700px)`]: {
      width: "35%",
      minHeight: "50vh",
    },
    [`@media (max-width:600px)`]: {
      width: "58%",
      minHeight: "45vh",
    },
  },
  formTitle: {
    color: COLOR_TITLE_PAGE,
    fontFamily: "Rubik-Regular",
    fontWeight: "bold",
    fontSize: "1.5rem",
    textAlign: "center",
  },
  formContainer: {
    display: "flex",
    flexDirection: "column",
    alignItems: "flex-start",
    justifyContent: "center",
    marginLeft: "auto",
    marginRight: "auto",
    width: "75%",
    minHeight: "calc(50vh - 1.5rem)",
    [`@media (max-width:1200px)`]: {
      width: "80%",
    },
    [`@media (max-width:1000px)`]: {
      width: "90%",
    },
    [`@media (max-width:700px)`]: {
      width: "100%",
      minHeight: "calc(45vh - 1.5rem)",
    },
    [`@media (max-width:600px)`]: {
      minHeight: "calc(40vh - 1.5rem)",
    },
  },
  submitButton: {
    alignSelf: "flex-end",
    textTransform: "none",
    fontSize: "1.1rem",
    marginRight: 0,
  },
  elementWidth: {
    width: "100%",
  },
  locationFormElement: {
    boxSizing: "border-box",
    marginBottom: "1.1rem",
  },
  locationModal: {
    height: "70vh",
    width: "75%",
    [`@media (max-width:1200px)`]: {
      width: "80%",
    },
    [`@media (max-width:700px)`]: {
      width: "90%",
    },
    [`@media (max-width:600px)`]: {
      width: "100%",
      marginLeft: 0,
      marginRight: 0,
    },
  },

  tableWrapper: {
    "& .MuiDialogContent-root": {
      minWidth: "100%",
    },
  },
});
