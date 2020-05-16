import React from "react";
import PropTypes from "prop-types";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Dialog from "@material-ui/core/Dialog";
import RadioGroup from "@material-ui/core/RadioGroup";
import Radio from "@material-ui/core/Radio";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import MobileTrackerButton from "./form/MobileTrackerButton";
import { makeSharedStyle } from "../../../../style/activated-account/shared";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core";
import { COLOR_TITLE_PAGE } from "../../../../style/activated-account/constants";

const ModalDropDown = ({
  onClose,
  open,
  title,
  data,
  defaultElementKey,
  ...props
}) => {
  const [selectedObject, setSelectedObject] = React.useState(null);
  const [firstSelectedObject, setFirstSelectedObject] = React.useState(null);
  const { keyProp, valueProp, elements } = data;
  const { modalTitlePage } = makeSharedStyle();

  const handleCancel = () => {
    setSelectedObject(firstSelectedObject);
    onClose();
  };

  React.useEffect(() => {
    setFirstSelectedObject(selectedObject);
  }, [open]);

  const handleOk = () => {
    onClose(selectedObject);
  };

  const handleChange = (element) => {
    setSelectedObject(element);
  };

  const theme = createMuiTheme({
    palette: {
      primary: { main: "#fff" },
      secondary: { main: COLOR_TITLE_PAGE },
    },
  });

  return (
    <Dialog
      disableBackdropClick
      disableEscapeKeyDown
      maxWidth="xs"
      aria-labelledby="confirmation-dialog-title"
      open={open}
      {...props}
    >
      <DialogTitle id="drop-down-dialog-title" className={modalTitlePage}>
        {title}
      </DialogTitle>
      <DialogContent dividers>
        <MuiThemeProvider theme={theme}>
          <RadioGroup
            aria-label="ringtone"
            name="ringtone"
            defaultValue={defaultElementKey}
            value={selectedObject == null ? "" : selectedObject[keyProp]}
          >
            {elements.map((element) => {
              return (
                <FormControlLabel
                  key={element[keyProp]}
                  value={element[keyProp]}
                  control={<Radio />}
                  label={element[valueProp]}
                  onChange={() => handleChange(element)}
                />
              );
            })}
          </RadioGroup>
        </MuiThemeProvider>
      </DialogContent>
      <DialogActions>
        <MobileTrackerButton
          textOnDisable="CANCEL"
          text="CANCEL"
          disabled={false}
          autoFocus
          onClick={handleCancel}
          style={{ fontSize: "0.875rem", backgroundColor: "#d43c3c" }}
        />
        <MobileTrackerButton
          textOnDisable="OK"
          text="OK"
          disabled={!selectedObject || selectedObject[keyProp] === ""}
          onClick={handleOk}
          type="button"
          style={{ fontSize: "0.875rem" }}
        />
      </DialogActions>
    </Dialog>
  );
};

ModalDropDown.propTypes = {
  onClose: PropTypes.func.isRequired,
  open: PropTypes.bool.isRequired,
  title: PropTypes.string.isRequired,
  data: PropTypes.shape({
    keyProp: PropTypes.string.isRequired,
    valueProp: PropTypes.string.isRequired,
    elements: PropTypes.arrayOf(PropTypes.object),
  }).isRequired,
  defaultElementKey: PropTypes.string,
};

ModalDropDown.defaultProps = {
  defaultElementKey: "",
};

export default ModalDropDown;
