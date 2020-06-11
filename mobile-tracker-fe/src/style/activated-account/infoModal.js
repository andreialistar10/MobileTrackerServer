import { makeStyles } from "@material-ui/core/styles";
import { FONT_FAMILY, SMALL_DEVICE_MAX_WIDTH } from "./constants";

const makeInfoModalStyles = makeStyles({
  infoModalWrapper: {
    fontFamily: FONT_FAMILY,
    width: "35%",
    height: "50vh",
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
    [`@media (max-width:600px)`]: {
      width: "80%",
    },
    [`@media (max-width:400px)`]: {
      height: "55vh",
    },
  },
  topInfoModal: {
    width: "100%",
    height: "35%",
    backgroundColor: "#2979FF",
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
  },
  svgImage: {
    maxWidth: "90px",
    maxHeight: "90px",
    width: "60%",
    height: "60%",
    color: "#061f58",
  },
  bottomInfoModal: {
    width: "100%",
    height: "65%",
    padding: "10px",
    backgroundColor: "#d1dcef",
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
  },
  headerInfoModal: {
    marginBottom: "40px",
    fontWeight: "700",
    textAlign: "center",
    [`@media (max-width:850px)`]: {
      fontSize: "2.2rem",
    },
    [`@media (max-width:600px)`]: {
      fontSize: "1.8rem",
    },
    [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
      fontSize: "1.6rem",
      fontWeight: "bold",
    },
  },
  messageInfoModal: {
    textAlign: "center",
    fontWeight: "600",
    fontSize: "1.4rem",
    marginBottom: "25px",
  },
  [`@media (max-width:850px)`]: {
    fontSize: "1.3rem",
  },
  [`@media (max-width:600px)`]: {
    fontSize: "1.2rem",
  },
  [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
    fontSize: "1.1rem",
  },
});

export default makeInfoModalStyles;
