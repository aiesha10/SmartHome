// Main GUI Class
import javax.swing.*;
import java.awt.*;

public class SmartHomeGUI {

    private static CardLayout cardLayout;

    public static void main(String[] args) {
        SmartHomeSystem smartHomeSystem = new SmartHomeSystem();
        Light light = new Light("Room Light", false);
        Thermostat thermostat = new Thermostat("Room Thermostat", 22);
        Fan fan = new Fan("Ceiling Fan", false);
        Camera camera = new Camera("Security Camera", false);
        SmartLock smartLock = new SmartLock("Front Door SmartLock", true, "1234");


        smartHomeSystem.addDevice(light);
        smartHomeSystem.addDevice(thermostat);
        smartHomeSystem.addDevice(fan);
        smartHomeSystem.addDevice(camera);

        JFrame frame = new JFrame("Smart Home System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        // Initializing CardLayout
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);

        Font headerFont = new Font("Arial", Font.BOLD, 24);
        Font labelFont = new Font("Arial", Font.PLAIN, 18);

        JPanel homePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(196, 255, 234);
                Color color2 = new Color(209, 174, 123);
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        homePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Light Button
        JButton lightButton = createDeviceButton("Lights", "src/bulb.png", frame, "Lights");
        gbc.gridx = 0;
        gbc.gridy = 0;
        homePanel.add(lightButton, gbc);

        // Thermostat Button
        JButton thermostatButton = createDeviceButton("Thermostat", "src/thermometer.png", frame, "Thermostat");
        gbc.gridx = 1;
        gbc.gridy = 0;
        homePanel.add(thermostatButton, gbc);

        // Fan Button
        JButton fanButton = createDeviceButton("Fan", "src/fan.png", frame, "Fan");
        gbc.gridx = 2;
        gbc.gridy = 0;
        homePanel.add(fanButton, gbc);

        // Camera Button
        JButton cameraButton = createDeviceButton("Camera", "src/camera.png", frame, "Camera");
        gbc.gridx = 0; // New row for Camera button
        gbc.gridy = 1;
        homePanel.add(cameraButton, gbc);

        //SmartLock button
        JButton smartLockButton = createDeviceButton("SmartLock", "src/smartlock.png", frame, "SmartLock");
        gbc.gridx = 3; // Adjust grid position as needed
        gbc.gridy = 0;
        homePanel.add(smartLockButton, gbc);

        // Add Panels to CardLayout
        JPanel lightPanel = createDevicePanel(headerFont, labelFont, light, "room", frame);
        JPanel thermostatPanel = createThermostatPanel(headerFont, labelFont, thermostat, "room", frame);
        JPanel fanPanel = createFanPanel(headerFont, labelFont, fan, "room", frame);
        //old one JPanel cameraPanel = createCameraPanel(headerFont, labelFont, camera, frame);
//new camera addinglocations as well
        JPanel cameraPanel = createCameraPanel(headerFont, labelFont, camera, frame);
        JPanel cameraLocationsPanel = createCameraLocationsPanel(headerFont, labelFont, camera, frame);
        JPanel garageCameraPanel = createGarageCameraPanel(headerFont, labelFont, camera, frame);
        JPanel livingRoomCameraPanel = createGarageCameraPanel(headerFont, labelFont, camera, frame);
        JPanel frontDoorCameraPanel = createGarageCameraPanel(headerFont, labelFont, camera, frame);

        //smartlock panel
        // SmartLock smartLock = new SmartLock("Front Door SmartLock", true, "1234"); // Example PIN: 1234
        JPanel smartLockPanel = createSmartLockPanel(headerFont, labelFont, smartLock, frame);
        frame.add(smartLockPanel, "SmartLock");

        frame.add(homePanel, "Home");
        frame.add(lightPanel, "Lights");
        frame.add(thermostatPanel, "Thermostat");
        frame.add(fanPanel, "Fan");
        frame.add(cameraPanel, "Camera");
        //new for locations
        frame.add(cameraLocationsPanel, "CameraLocations");
        frame.add(garageCameraPanel, "GarageCamera");
        frame.add(livingRoomCameraPanel, "LivingRoomCamera");
        frame.add(frontDoorCameraPanel, "FrontDoorCamera");

        frame.add(createRoomSelectionPanel("Light", headerFont, labelFont, frame, light, fan, thermostat), "LightRoomSelection");
        frame.add(createRoomSelectionPanel("Fan", headerFont, labelFont, frame, light, fan, thermostat), "FanRoomSelection");
        frame.add(createRoomSelectionPanel("Thermostat", headerFont, labelFont, frame, light, fan, thermostat), "ThermostatRoomSelection");

        cardLayout.show(frame.getContentPane(), "Home");
        frame.setVisible(true);
    }

    private static JPanel createRoomSelectionPanel(String deviceType, Font headerFont, Font labelFont, JFrame frame, Light light, Fan fan, Thermostat thermostat) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select a room for " + deviceType + ":", SwingConstants.CENTER);
        label.setFont(headerFont);

        // Room options
        JPanel roomButtonsPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        String[] rooms = {"Bedroom", "Bathroom", "Kitchen", "Garage", "Living Room"};
        for (String room : rooms) {
            JButton roomButton = new JButton(room);
            roomButton.setFont(labelFont);
            roomButton.addActionListener(e -> {
                // Navigate to the device panel with the room context
                String panelName = room + deviceType + "Panel";
                cardLayout.show(frame.getContentPane(), panelName);
            });
            roomButtonsPanel.add(roomButton);
        }

        // Add device panels for each room (only once, outside the room button loop)
        for (String room : rooms) {
            if (deviceType.equals("Light")) {
                frame.add(createDevicePanel(headerFont, labelFont, light, room, frame), room + "LightPanel");
            } else if (deviceType.equals("Fan")) {
                frame.add(createFanPanel(headerFont, labelFont, fan, room, frame), room + "FanPanel");
            } else if (deviceType.equals("Thermostat")) {
                frame.add(createThermostatPanel(headerFont, labelFont, thermostat, room, frame), room + "ThermostatPanel");
            }
        }

        JButton backButton = new JButton("Go Back");
        backButton.setFont(labelFont);
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Home"));

        panel.add(label, BorderLayout.NORTH);
        panel.add(roomButtonsPanel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    // Method to create a device button with an icon
    private static JButton createDeviceButton(String tooltip, String iconPath, JFrame frame, String panelName) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        JButton button = new JButton();
        button.setPreferredSize(new Dimension(200, 150));
        button.setIcon(icon);
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);
        button.addActionListener(e -> cardLayout.show(frame.getContentPane(), panelName));
        return button;
    }

    // Method to create Light Panel
    private static JPanel createDevicePanel(Font headerFont, Font labelFont, Light light, String room, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("[" + room + "] " + light.getName() + " is " + (light.isOn() ? "On" : "Off"), SwingConstants.CENTER);
        label.setFont(headerFont);

        JButton toggleButton = new JButton(light.isOn() ? "Turn Off" : "Turn On");
        toggleButton.setFont(labelFont);
        toggleButton.addActionListener(e -> {
            if (light.isOn()) {
                light.turnOff();
                label.setText("[" + room + "] " + light.getName() + " is Off");
                toggleButton.setText("Turn On");
            } else {
                light.turnOn();
                label.setText("[" + room + "] " + light.getName() + " is On");
                toggleButton.setText("Turn Off");
            }
        });

        JButton backButton = new JButton("Go Back");
        backButton.setFont(labelFont);
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Home"));

        JButton chooseRoomButton = new JButton("Choose Room");
        chooseRoomButton.setFont(labelFont);
        chooseRoomButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "LightRoomSelection"));

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(chooseRoomButton);

        panel.add(label, BorderLayout.CENTER);
        panel.add(toggleButton, BorderLayout.SOUTH);
        panel.add(buttonPanel, BorderLayout.NORTH);

        return panel;
    }

    private static JPanel createThermostatPanel(Font headerFont, Font labelFont, Thermostat thermostat, String room, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("[" + room + "] Temperature: " + thermostat.getTemperature() + "°C", SwingConstants.CENTER);
        label.setFont(headerFont);

        JButton increaseButton = new JButton("Increase Temperature");
        increaseButton.setFont(labelFont);
        increaseButton.addActionListener(e -> {
            thermostat.setTemperature(thermostat.getTemperature() + 1);
            label.setText("[" + room + "] Temperature: " + thermostat.getTemperature() + "°C");
        });

        JButton decreaseButton = new JButton("Decrease Temperature");
        decreaseButton.setFont(labelFont);
        decreaseButton.addActionListener(e -> {
            thermostat.setTemperature(thermostat.getTemperature() - 1);
            label.setText("[" + room + "] Temperature: " + thermostat.getTemperature() + "°C");
        });

        JButton backButton = new JButton("Go Back");
        backButton.setFont(labelFont);
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Home"));

        JButton chooseRoomButton = new JButton("Choose Room");
        chooseRoomButton.setFont(labelFont);
        chooseRoomButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "ThermostatRoomSelection"));

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(chooseRoomButton);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(increaseButton);
        buttonsPanel.add(decreaseButton);

        panel.add(label, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        panel.add(buttonPanel, BorderLayout.NORTH);

        return panel;
    }

    private static JPanel createFanPanel(Font headerFont, Font labelFont, Fan fan, String room, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("[" + room + "] Fan Speed: " + fan.getSpeed(), SwingConstants.CENTER);
        label.setFont(headerFont);

        JComboBox<String> speedSelector = new JComboBox<>(new String[]{"0 (Off)", "1 (Low)", "2 (Medium)", "3 (High)"});
        speedSelector.setSelectedIndex(fan.getSpeed());
        speedSelector.setFont(labelFont);
        speedSelector.addActionListener(e -> {
            int selectedSpeed = speedSelector.getSelectedIndex();
            fan.setSpeed(selectedSpeed);
            label.setText("[" + room + "] Fan Speed: " + fan.getSpeed());
        });

        JButton backButton = new JButton("Go Back");
        backButton.setFont(labelFont);
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Home"));

        JButton chooseRoomButton = new JButton("Choose Room");
        chooseRoomButton.setFont(labelFont);
        chooseRoomButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "FanRoomSelection"));

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(chooseRoomButton);

        panel.add(label, BorderLayout.CENTER);
        panel.add(speedSelector, BorderLayout.SOUTH);
        panel.add(buttonPanel, BorderLayout.NORTH);

        return panel;
    }

    private static JPanel createCameraPanel(Font headerFont, Font labelFont, Camera camera, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        //old JLabel label = new JLabel(camera.getName() + " is " + (camera.isOn() ? "On" : "Off") +
              //again  ", Recording: " + (camera.isRecording() ? "On" : "Off"), SwingConstants.CENTER);
        JLabel label = new JLabel("Click on the Camera to manage locations:", SwingConstants.CENTER);
        label.setFont(headerFont);

        JButton cameraIconButton = new JButton();
        cameraIconButton.setPreferredSize(new Dimension(200, 150));
        ImageIcon cameraIcon = new ImageIcon("src/camera.png"); // Adjust path to your icon
        Image scaledImage = cameraIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        cameraIconButton.setIcon(new ImageIcon(scaledImage));

        cameraIconButton.setFocusPainted(false);
        cameraIconButton.setToolTipText("Click to manage Camera locations");

        //old code no need of it anymore
        /*JButton toggleButton = new JButton(camera.isOn() ? "Turn Off" : "Turn On");
        toggleButton.setFont(labelFont);
        toggleButton.addActionListener(e -> {
            if (camera.isOn()) {
                camera.turnOff();
                label.setText(camera.getName() + " is Off, Recording: Off");
                toggleButton.setText("Turn On");
            } else {
                camera.turnOn();
                label.setText(camera.getName() + " is On, Recording: " + (camera.isRecording() ? "On" : "Off"));
                toggleButton.setText("Turn Off");
            }
        });

        JButton recordButton = new JButton(camera.isRecording() ? "Stop Recording" : "Start Recording");
        recordButton.setFont(labelFont);
        recordButton.addActionListener(e -> {
            if (camera.isOn()) {
                if (camera.isRecording()) {
                    camera.stopRecording();
                    label.setText(camera.getName() + " is On, Recording: Off");
                    recordButton.setText("Start Recording");
                } else {
                    camera.startRecording();
                    label.setText(camera.getName() + " is On, Recording: On");
                    recordButton.setText("Stop Recording");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Turn on the camera first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Go Back");
        backButton.setFont(labelFont);
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Home"));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(toggleButton);
        buttonsPanel.add(recordButton);

        panel.add(label, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        panel.add(backButton, BorderLayout.NORTH);
        return panel;
    }*/
        cameraIconButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "CameraLocations"));

        JButton backButton = new JButton("Go Back");
        backButton.setFont(labelFont);
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Home"));

        panel.add(label, BorderLayout.NORTH);
        panel.add(cameraIconButton, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }
    private static JPanel createCameraLocationsPanel(Font headerFont, Font labelFont, Camera camera, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select a location to control the camera:", SwingConstants.CENTER);
        label.setFont(headerFont);

        // Panel for location buttons
        JPanel locationButtonsPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        // Create buttons for each location
        JButton garageButton = new JButton("Garage");
        JButton livingRoomButton = new JButton("Living Room");
        JButton frontDoorButton = new JButton("Front Door");

        garageButton.setFont(labelFont);
        livingRoomButton.setFont(labelFont);
        frontDoorButton.setFont(labelFont);

        // Add actions for location buttons
        garageButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "GarageCamera"));
        livingRoomButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "LivingRoomCamera"));
        frontDoorButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "FrontDoorCamera"));

        locationButtonsPanel.add(garageButton);
        locationButtonsPanel.add(livingRoomButton);
        locationButtonsPanel.add(frontDoorButton);

        JButton backButton = new JButton("Go Back");
        backButton.setFont(labelFont);
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Camera"));

        panel.add(label, BorderLayout.NORTH);
        panel.add(locationButtonsPanel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createGarageCameraPanel(Font headerFont, Font labelFont, Camera camera, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel statusLabel = new JLabel("Garage Camera: " + getCameraStatus(camera), SwingConstants.CENTER);
        statusLabel.setFont(headerFont);

        JButton toggleButton = new JButton("Turn On/Off");
        toggleButton.setFont(labelFont);
        toggleButton.addActionListener(e -> {
            if (camera.isOn()) {
                camera.turnOff();
            } else {
                camera.turnOn();
            }
            statusLabel.setText("Garage Camera: " + getCameraStatus(camera));
        });

        JButton recordButton = new JButton("Start/Stop Recording");
        recordButton.setFont(labelFont);
        recordButton.addActionListener(e -> {
            if (camera.isOn()) {
                camera.toggleRecording();
            } else {
                JOptionPane.showMessageDialog(frame, "Turn on the camera first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            statusLabel.setText("Garage Camera: " + getCameraStatus(camera));
        });

        JButton backButton = new JButton("Go Back");
        backButton.setFont(labelFont);
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "CameraLocations"));

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonsPanel.add(toggleButton);
        buttonsPanel.add(recordButton);

        panel.add(statusLabel, BorderLayout.NORTH);
        panel.add(buttonsPanel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    private static String getCameraStatus(Camera camera) {
        return (camera.isOn() ? "On" : "Off") + ", Recording: " + (camera.isRecording() ? "On" : "off");
    }

    //smartlock panel
    private static JPanel createSmartLockPanel(Font headerFont, Font labelFont, SmartLock smartLock, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        //new old JLabel statusLabel = new JLabel(smartLock.getName() + " is " + (smartLock.isOn() ? "On" : "Off") +
        //new old", Locked: " + (smartLock.isLocked() ? "Yes" : "No"), SwingConstants.CENTER);
        JLabel statusLabel = new JLabel(smartLock.getName() + "is Locked" , SwingConstants.CENTER);
        statusLabel.setFont(headerFont);
        statusLabel.setOpaque(true); //it helps in changing color used it for alarm
        statusLabel.setBackground(Color.LIGHT_GRAY);

        //smartlock icon
        JLabel iconLabel = new JLabel();
        ImageIcon smartLockIcon = new ImageIcon("src/smartlock.png");
        Image scaledImage = smartLockIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaledImage));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);


//creating seperate lock and unlock button to make it look clean and pretty

        //unlockbutton
        JButton unlockButton = new JButton("UnLock");
        unlockButton.setFont(labelFont);
        unlockButton.addActionListener(e -> {
            String inputPin = JOptionPane.showInputDialog(frame, "Enter Pin to Unlock: ");
            if(inputPin == null){
                return;
            }
            try{
                smartLock.unlock(inputPin);
                statusLabel.setText("Welcome Back <3");
                statusLabel.setBackground(Color.GREEN);
            }catch (InvalidPinException ex) {
                statusLabel.setText("Incorrect PIN!!! Emergency Alarm Triggered. ");
                statusLabel.setBackground(Color.RED);
                EmergencyAlarm.trigger();
            }
        });

        JButton lockButton = new JButton("Lock");
        lockButton.setFont(labelFont);
        lockButton.addActionListener(e -> {
            smartLock.lock();
            statusLabel.setText("SmartLock is Locked.");
            statusLabel.setBackground(Color.LIGHT_GRAY);
        });
        JButton backButton = new JButton("Go Back" );
        backButton.setFont(labelFont);
        backButton.addActionListener(e ->
                cardLayout.show(frame.getContentPane(), "Home"));

        //Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.add(lockButton);
        buttonPanel.add(unlockButton);
        buttonPanel.add(backButton);

        panel.add(statusLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }


    //emergency alarm method new
    private static void trigger() {
        JOptionPane.showMessageDialog(null, "Emergency Alarm Triggered!", "ALERT", JOptionPane.ERROR_MESSAGE);
        System.out.println("EMERGENCY ALARM: Unauthorized access attempt detected!");
    }
}
