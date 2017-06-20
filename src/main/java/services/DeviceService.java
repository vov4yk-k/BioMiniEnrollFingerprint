package services;

import models.Device;
import java.util.HashSet;

/**
 * Created by Користувач on 20.06.2017.
 */
public interface DeviceService {

    void init();

    void uninit();

    HashSet<Device> deviceList();

    void changeDevice(Device device);
}
