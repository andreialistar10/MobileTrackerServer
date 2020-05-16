package com.andrei.mobiletracker.device.facade.populator.unregistereddevice;

import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.deviceconnectivity.UnregisteredDeviceCredentialsData;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import org.springframework.stereotype.Component;

@Component
public class UnregisteredDeviceCredentialsDataToUnregisteredDevicePopulator implements Populator<UnregisteredDevice, UnregisteredDeviceCredentialsData> {

    @Override
    public void populate(UnregisteredDeviceCredentialsData unregisteredDeviceCredentialsData, UnregisteredDevice unregisteredDevice) {

        unregisteredDevice.setId(unregisteredDeviceCredentialsData.getId());
        unregisteredDevice.setPassword(unregisteredDeviceCredentialsData.getPassword());
        unregisteredDevice.setTryingToPairingUsername(unregisteredDeviceCredentialsData.getTryingToPairUsername());
        String idAfterPairing = unregisteredDeviceCredentialsData.getIdAfterPairing();
        if (idAfterPairing == null || idAfterPairing.equals("")){
            unregisteredDevice.setIdAfterPairing(unregisteredDeviceCredentialsData.getId());
        } else {
            unregisteredDevice.setIdAfterPairing(idAfterPairing);
        }
    }
}
