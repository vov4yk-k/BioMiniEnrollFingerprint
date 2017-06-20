package services;

import controllers.BioMiniSDK;
import models.Device;

import java.util.HashSet;


/**
 * Created by Користувач on 20.06.2017.
 */
public class DeviceServiceImpl implements DeviceService {

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
        return bioMiniSDK.getListModel();
    }

    @Override
    public void changeDevice(Device device) {
        bioMiniSDK.setCurrentDevice(device);
    }

}
