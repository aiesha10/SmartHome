class Fan extends Device {
    private int speed; // 4 different levels of speed

    public Fan(String name, boolean isOn) {
        super(name, isOn);
        this.speed = 0; // Default speed is Off
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (speed < 0 || speed > 3) {
            throw new IllegalArgumentException("Speed must be between 0 and 3.");
        }
        this.speed = speed;
        System.out.println(getName() + " speed set to " + (isOn() ? speed : "Off") + ".");
    }

    @Override
    public void turnOn() {
        super.turnOn();
        if (speed == 0) {
            speed = 1;
            // speed is 1 because when turned on it will be set at low speed

        }
    }

    @Override
    public void turnOff() {
        super.turnOff();
        speed = 0;
    }

    @Override
    public void displayStatus() {
        System.out.println(getName() + " is " + (isOn() ? "On" : "Off") + ", Speed: " + (isOn() ? speed : "Off"));
    }
}