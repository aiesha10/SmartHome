// Class for Light, extending Device
class Light extends Device {
    public Light(String name, boolean isOn) {
        super(name, isOn);
    }

    @Override
    public void displayStatus() {
        System.out.println(getName() + " is " + (isOn() ? "On" : "Off"));
    }
}