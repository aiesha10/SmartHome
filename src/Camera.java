// Add this class where other device classes (Light, Thermostat, Fan) are defined
class Camera extends Device {
    private boolean isRecording;

    public Camera(String name, boolean isOn) {
        super(name, isOn);
        this.isRecording = false; // Default: not recording
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void startRecording() {
        if (isOn()) {
            isRecording = true;
            System.out.println(getName() + " started recording.");
        } else {
            System.out.println(getName() + " must be turned on to start recording.");
        }
    }

    public void stopRecording() {
        if (isRecording) {
            isRecording = false;
            System.out.println(getName() + " stopped recording.");
        } else {
            System.out.println(getName() + " is not currently recording.");
        }
    }

    @Override
    public void displayStatus() {
        System.out.println(getName() + " is " + (isOn() ? "On" : "Off") +
                ", Recording: " + (isRecording ? "On" : "Off"));
    }
}
