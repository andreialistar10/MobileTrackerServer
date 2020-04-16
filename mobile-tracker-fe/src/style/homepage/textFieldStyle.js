import { makeStyles } from "@material-ui/core/styles";

export const makeTextFieldStyle = makeStyles({
  textField: {
    width: "80%",
    padding: "2px",
    marginBottom: "0.5rem",
  },
  helperText: {
    fontWeight: "bold",
    marginBottom: "0.5rem",
    color: "#ff1100",
  },
  input: {
    padding: "2px",
    fontSize: "1rem",
    marginBottom: "0.25rem",
    color: "alicia-blue",
  },
  label: {
    fontSize: "1.1rem",
    color: "rgba(0,63,243,0.9)",
    "&$erroredLabel": {
      color: "#003ff3",
    },
  },
  erroredLabel: {},
});
