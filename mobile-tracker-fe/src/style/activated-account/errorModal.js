import { makeStyles } from "@material-ui/core/styles";
import {
  SMALL_DEVICE_MAX_WIDTH,
} from "./constants";

export const makeErrorModalStyles = makeStyles({
  defaultErrorModalBody: {
    height: "40vh",
    width: "40%",
    minWidth: "325px",
    [`@media (max-height:900px)`]: {
      height: "45vh",
    },
    [`@media (max-height:850px)`]: {
      height: "55vh",
    },
    [`@media (max-width:1260px)`]: {
      width: "50%",
    },
    [`@media (max-width:1050px)`]: {
      width: "60%",
    },
    [`@media (max-width:900px)`]: {
      width: "65%",
    },
    [`@media (max-width:800px)`]: {
      width: "70%",
    },
    [`@media (max-width:700px)`]: {
      width: "75%",
    },
    [`@media (max-width:670px)`]: {
      width: "95%",
    },
    [`@media (max-width:600px)`]: {
      height: "50vh",
    },
    [`@media (max-width:480px)`]: {
      height: "53vh",
    },
    [`@media (max-width:400px)`]: {
      height: "65vh",
    }
  },
  errorHeaderWrapper: {
    display: "flex",
    flexDirection: "column",
    justifyContent: "flex-end",
    backgroundColor: "#E53935",
    paddingTop: "20px",
    minHeight: "70%",
    width: "100%",
    color: "white",
    overflow: "auto",
  },
  errorHeader: {
    fontWeight: "500",
    textAlign: "center",
    [`@media (max-width:850px)`]: {
      fontSize: "2.2rem",
    },
    [`@media (max-width:600px)`]: {
      fontSize: "1.8rem",
    },
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      fontSize: "1.6rem",
    },
  },
  messageError: {
    paddingLeft: "20px",
    paddingRight: "20px",
    marginTop: "40px",
    marginBottom: "25px",
    textAlign: "center",
    fontSize: "1.4rem",
    fontWeight: "400",
  },
  belowHeaderWrapper: {
    minHeight: "30%",
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
  },
});
