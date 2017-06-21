package services;

import models.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Користувач on 20.06.2017.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    BioMiniSDK bioMiniSDK;


    public DeviceServiceImpl(BioMiniSDK bioMiniSDK) {
        this.bioMiniSDK = bioMiniSDK;
    }

    @Override
    public void init() {
        bioMiniSDK.init();
    }

    @Override
    public void uninit() {
        return;
    }

    @Override
    public HashSet<Device> deviceList() {
        bioMiniSDK.updateScannerList();
        HashSet<Device> devices = new HashSet<>(bioMiniSDK.getListModel().values());
        return devices;
    }

    @Override
    public void changeDevice(Device device) {
        bioMiniSDK.setCurrentDevice(device);
    }

}
