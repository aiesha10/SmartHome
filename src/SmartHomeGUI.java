// Main GUI Class
import javax.swing.*;
import java.awt.*;

public class SmartHomeGUI {

    private static CardLayout cardLayout;

    public static void main(String[] args) {
        SmartHomeSystem smartHomeSystem = new SmartHomeSystem();
        Light light = new Light("Living Room Light", false);
        Thermostat thermostat = new Thermostat("Living Room Thermostat", 22);
        Fan fan = new Fan("Ceiling Fan", false);
        Camera camera = new Camera("Security Camera", false);

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

        // Add Panels to CardLayout
        JPanel lightPanel = createDevicePanel("Light Control", headerFont, labelFont, light, frame);
        JPanel thermostatPanel = createThermostatPanel(headerFont, labelFont, thermostat, frame);
        JPanel fanPanel = createFanPanel(headerFont, labelFont, fan, frame);
        JPanel cameraPanel = createCameraPanel(headerFont, labelFont, camera, frame);

        frame.add(homePanel, "Home");
        frame.add(lightPanel, "Lights");
        frame.add(thermostatPanel, "Thermostat");
        frame.add(fanPanel, "Fan");
        frame.add(cameraPanel, "Camera");

        cardLayout.show(frame.getContentPane(), "Home");
        frame.setVisible(true);
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
    private static JPanel createDevicePanel(String title, Font headerFont, Font labelFont, Device device, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(device.getName() + " is " + (device.isOn() ? "On" : "Off"), SwingConstants.CENTER);
        label.setFont(headerFont);

        JButton toggleButton = new JButton(device.isOn() ? "Turn Off" : "Turn On");
        toggleButton.setFont(labelFont);
        toggleButton.addActionListener(e -> {
            if (device.isOn()) {
                device.turnOff();
                label.setText(device.getName() + " is Off");
                toggleButton.setText("Turn On");
            } else {
                device.turnOn();
                label.setText(device.getName() + " is On");
                toggleButton.setText("Turn Off");
            }
        });

        JButton backButton = new JButton("Go Back");
        backButton.setFont(labelFont);
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Home"));

        panel.add(label, BorderLayout.CENTER);
        panel.add(toggleButton, BorderLayout.SOUTH);
        panel.add(backButton, BorderLayout.NORTH);
        return panel;
    }

    private static JPanel createThermostatPanel(Font headerFont, Font labelFont, Thermostat thermostat, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Temperature: " + thermostat.getTemperature() + "°C", SwingConstants.CENTER);
        label.setFont(headerFont);

        JButton increaseButton = new JButton("Increase Temperature");
        increaseButton.setFont(labelFont);
        increaseButton.addActionListener(e -> {
            thermostat.setTemperature(thermostat.getTemperature() + 1);
            label.setText("Temperature: " + thermostat.getTemperature() + "°C");
        });

        JButton decreaseButton = new JButton("Decrease Temperature");
        decreaseButton.setFont(labelFont);
        decreaseButton.addActionListener(e -> {
            thermostat.setTemperature(thermostat.getTemperature() - 1);
            label.setText("Temperature: " + thermostat.getTemperature() + "°C");
        });

        JButton backButton = new JButton("Go Back");
        backButton.setFont(labelFont);
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Home"));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(increaseButton);
        buttonsPanel.add(decreaseButton);

        panel.add(label, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        panel.add(backButton, BorderLayout.NORTH);
        return panel;
    }

    private static JPanel createFanPanel(Font headerFont, Font labelFont, Fan fan, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Speed: " + fan.getSpeed(), SwingConstants.CENTER);
        label.setFont(headerFont);

        JComboBox<String> speedSelector = new JComboBox<>(new String[]{"0 (Off)", "1 (Low)", "2 (Medium)", "3 (High)"});
        speedSelector.setSelectedIndex(fan.getSpeed());
        speedSelector.setFont(labelFont);
        speedSelector.addActionListener(e -> {
            int selectedSpeed = speedSelector.getSelectedIndex();
            fan.setSpeed(selectedSpeed);
            label.setText("Speed: " + fan.getSpeed());
        });

        JButton backButton = new JButton("Go Back");
        backButton.setFont(labelFont);
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Home"));

        panel.add(label, BorderLayout.CENTER);
        panel.add(speedSelector, BorderLayout.SOUTH);
        panel.add(backButton, BorderLayout.NORTH);
        return panel;
    }

    private static JPanel createCameraPanel(Font headerFont, Font labelFont, Camera camera, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(camera.getName() + " is " + (camera.isOn() ? "On" : "Off") +
                ", Recording: " + (camera.isRecording() ? "On" : "Off"), SwingConstants.CENTER);
        label.setFont(headerFont);

        JButton toggleButton = new JButton(camera.isOn() ? "Turn Off" : "Turn On");
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
    }
}
