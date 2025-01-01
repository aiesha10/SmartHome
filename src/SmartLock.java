//aiesha
class SmartLock extends Device {
    private String pin; // Stored PIN for the lock
    private boolean isLocked;

    public SmartLock(String name, boolean isOn, String pin) {
        super(name, isOn);
        this.pin = pin;
        this.isLocked = true; // Default: locked
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void unlock(String inputPin) throws InvalidPinException {
        if (inputPin.equals(pin)) {
            isLocked = false;
            System.out.println(getName() + " unlocked successfully.");
        } else {
            throw new InvalidPinException("Incorrect PIN! Triggering Emergency Alarm.");
        }
    }

    public void lock() {
        isLocked = true;
        System.out.println(getName() + " locked successfully.");
    }

    @Override
    public void displayStatus() {
        System.out.println(getName() + " is " + (isOn() ? "On" : "Off") + ", Locked: " + (isLocked ? "Yes" : "No"));
    }
}

