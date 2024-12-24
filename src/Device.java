
abstract class Device {
    private String name;
    private boolean isOn;

    public Device(String name, boolean isOn) {
        this.name = name;
        this.isOn = isOn;
    }

    public String getName() {
        return name;
    }

    public boolean isOn() {
        return isOn;
    }

    public void turnOn() {
        isOn = true;
        System.out.println(name + " is turned on.");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(name + " is turned off.");
    }

    public abstract void displayStatus();
}
