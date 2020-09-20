import React from "react";
import Dialog from "@material-ui/core/Dialog";
import PropTypes from "prop-types";
import DialogContent from "@material-ui/core/DialogContent";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";

const MobileTrackerModal = ({
  isOpen,
  title,
  handleOnClose,
  className,
  children,
  closeIsDisabled,
  closeButton,
}) => {
  const {
    defaultModalBody,
    modalTitle,
    modalWrapper,
    modalCloseIcon,
  } = makeSharedStyle();
  const classes = !className
    ? defaultModalBody
    : `${defaultModalBody} ${className}`;

  return (
    <Dialog
      open={isOpen}
      onClose={handleOnClose}
      maxWidth={false}
      PaperProps={{ className: classes }}
    >
      <div className={modalWrapper}>
        {title !== null && title !== undefined && (
          <h1 className={modalTitle}>{title}</h1>
        )}
        <DialogContent>{children}</DialogContent>
        {closeButton && (
          <IconButton
            onClick={handleOnClose}
            className={modalCloseIcon}
            disabled={closeIsDisabled}
          >
            <CloseIcon />
          </IconButton>
        )}
      </div>
    </Dialog>
  );
};

MobileTrackerModal.propTypes = {
  isOpen: PropTypes.bool.isRequired,
  closeIsDisabled: PropTypes.bool,
  handleOnClose: PropTypes.func.isRequired,
  className: PropTypes.string,
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]),
  title: PropTypes.string,
  closeButton: PropTypes.bool,
};

MobileTrackerModal.defaultProps = {
  closeIsDisabled: false,
  closeButton: true,
};

export default MobileTrackerModal;
