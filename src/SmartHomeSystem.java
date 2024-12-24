//Ahsanullah
// Class to manage all devices in the Smart Home System
import java.util.ArrayList;
import java.util.List;

class SmartHomeSystem {
    private List<Device> devices;

    public SmartHomeSystem() {
        devices = new ArrayList<>();
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void displayAllDevices() {
        for (Device device : devices) {
            device.displayStatus();
        }
    }
}
