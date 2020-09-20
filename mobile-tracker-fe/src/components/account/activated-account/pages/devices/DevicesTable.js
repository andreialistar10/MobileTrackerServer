import React from "react";
import MaterialTable from "material-table";
import {
  COLOR_TITLE_PAGE,
  FONT_FAMILY,
} from "../../../../../style/activated-account/constants";
import { compose, setDisplayName } from "recompose";
import AddCircleIcon from "@material-ui/icons/AddCircle";
import { makeDevicesStyle } from "../../../../../style/activated-account/pages/devices";
import PropTypes from "prop-types";
import { toast } from "react-toastify";
import MobileTrackerModalLoadingIndicator from "../../common/modals/MobileTrackerModalLoadingIndicator";

const columns = [
  {
    title: "device code",
    field: "id",
    sorting: false,
    editable: "never",
    searchable: false,
  },
  { title: "device name", field: "name", sorting: false },
  {
    title: "date of registration",
    field: "date",
    editable: "never",
    sorting: false,
    searchable: false,
  },
];
const DevicesTable = ({ handleOnAdd, devices, onUpdate, onDelete }) => {
  const myTextIcon = React.useRef(null);
  const wrapperStyle = {
    backgroundColor: "rgba(255,255,255,0.6)",
  };

  const { deviceTableAddButton, deviceTableAddButtonText } = makeDevicesStyle();

  const headerStyle = {
    color: COLOR_TITLE_PAGE,
    textTransform: "uppercase",
    fontWeight: "bold",
    fontFamily: FONT_FAMILY,
    textAlign: "center",
    // backgroundColor: "rgba(255,255,255,0.6)",
  };
  const cellStyle = {
    fontFamily: FONT_FAMILY,
    fontWeight: "500",
    fontSize: "0.9rem",
    textAlign: "center",
  };

  React.useEffect(() => {
    // removing the class 'MuiIconButton-root' from parent of parent of our ref node.

    myTextIcon.current.parentNode.parentNode.classList.remove(
      "MuiIconButton-root"
    );
  }, []);

  const [pendingRequests, setPendingRequests] = React.useState(0);

  return (
    <>
      <MaterialTable
        columns={columns}
        data={devices}
        style={wrapperStyle}
        options={{
          minBodyHeight: 295,
          maxBodyHeight: 295,
          // doubleHorizontalScroll: true,
          emptyRowsWhenPaging: false,
          draggable: false,
          showTitle: false,
          headerStyle,
          cellStyle,
        }}
        localization={{
          body: {
            emptyDataSourceMessage: "No registered device",
            editRow: {
              deleteText: "Are you sure you want to delete this device?",
            },
          },
        }}
        actions={[
          {
            icon: compose(setDisplayName("AddButton"))(() => {
              return (
                <div className={deviceTableAddButton} ref={myTextIcon}>
                  <AddCircleIcon />
                  <span className={deviceTableAddButtonText}>
                    Add new device
                  </span>
                </div>
              );
            }),
            tooltip: "Add new device",
            isFreeAction: true,
            onClick: handleOnAdd,
          },
        ]}
        editable={{
          onRowUpdate: (newData, oldData) => {
            return new Promise((resolve, reject) => {
              const deviceCode = oldData.id;
              const { name } = newData;
              setPendingRequests((prevState) => prevState + 1);
              return onUpdate(deviceCode, { name })
                .then(() => {
                  toast.success(`You successfully updated ${name}'s device!`);
                  resolve();
                })
                .catch((error) => {
                  toast.error(error.message);
                  reject();
                })
                .finally(() =>
                  setPendingRequests((prevState) => prevState - 1)
                );
            });
          },
          onRowDelete: (oldData) => {
            return new Promise((resolve, reject) => {
              const { id: deviceCode, name } = oldData;
              setPendingRequests((prevState) => prevState + 1);
              return onDelete(deviceCode)
                .then(() => {
                  toast.success(`You successfully deleted ${name}'s device!`);
                  resolve();
                })
                .catch((error) => {
                  toast.error(error.message);
                  reject();
                })
                .finally(() =>
                  setPendingRequests((prevState) => prevState - 1)
                );
            });
          },
        }}
      />
      {pendingRequests !== 0 && (
        <MobileTrackerModalLoadingIndicator loading={pendingRequests !== 0} />
      )}
    </>
  );
};

DevicesTable.propTypes = {
  handleOnAdd: PropTypes.func.isRequired,
  onUpdate: PropTypes.func.isRequired,
  onDelete: PropTypes.func.isRequired,
  devices: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
      date: PropTypes.string.isRequired,
    })
  ).isRequired,
};

export default DevicesTable;
