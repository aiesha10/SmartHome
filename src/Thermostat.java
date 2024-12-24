class Thermostat extends Device {
    private int temperature;

    public Thermostat(String name, int temperature) {
        super(name, false);
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        System.out.println(getName() + " temperature set to " + temperature + "°C.");
    }

    @Override
    public void displayStatus() {
        System.out.println(getName() + " is " + (isOn() ? "On" : "Off") + ", Temperature: " + temperature + "°C");
    }
}
