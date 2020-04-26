import { makeStyles } from "@material-ui/core/styles";

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
  },
  icon: {
    width: "100%",
    height: "100%",
  },
});
