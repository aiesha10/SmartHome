
// Main GUI Class
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SmartHomeGUI {
    public static void main(String[] args) {

        SmartHomeSystem smartHomeSystem = new SmartHomeSystem();
        Light light = new Light("Living Room Light", false);
        Thermostat thermostat = new Thermostat("Living Room Thermostat", 22);
        Fan fan = new Fan("Ceiling Fan", false);

        smartHomeSystem.addDevice(light);
        smartHomeSystem.addDevice(thermostat);
        smartHomeSystem.addDevice(fan);

        JFrame frame = new JFrame("Smart Home System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new CardLayout());

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

        JButton lightButton = createDeviceButton("Lights", headerFont);
        JButton thermostatButton = createDeviceButton("Thermostat", headerFont);
        JButton fanButton = createDeviceButton("Fan", headerFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        homePanel.add(lightButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        homePanel.add(thermostatButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        homePanel.add(fanButton, gbc);


        JPanel lightPanel = createDevicePanel("Light Control", headerFont, labelFont, light, frame);

        JPanel thermostatPanel = createThermostatPanel(headerFont, labelFont, thermostat, frame);
        JPanel fanPanel = createFanPanel(headerFont, labelFont, fan, frame);

        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        frame.add(homePanel, "Home");
        frame.add(lightPanel, "Lights");
        frame.add(thermostatPanel, "Thermostat");
        frame.add(fanPanel, "Fan");

        lightButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Lights"));
        thermostatButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Thermostat"));
        fanButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Fan"));


        cardLayout.show(frame.getContentPane(), "Home");

        frame.setVisible(true);
    }

    private static JButton createDeviceButton(String name, Font font) {
        JButton button = new JButton(name);
        button.setFont(font);
        button.setPreferredSize(new Dimension(200, 150));
        //button.setBackground(Color.LIGHT_GRAY);
        button.setBackground(new Color(151, 90, 184));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);

        // this parts adds the hover effect for scaling, when one places their cursor on button their size changes
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setPreferredSize(new Dimension(220, 170)); // Grow slightly
                button.revalidate(); // Re-layout the button to apply the new size
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setPreferredSize(new Dimension(200, 150)); // Back to original size
                button.revalidate(); // Re-layout the button to revert the size
            }
        });


        return button;
    }

    private static JPanel createDevicePanel(String title, Font headerFont, Font labelFont, Device device, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                //color options for light panel
                Color color1 = new Color(126, 142, 82);
                Color color2 = new Color(102, 149, 189);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };

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
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "Home");
        });

        panel.add(label, BorderLayout.CENTER);
        panel.add(toggleButton, BorderLayout.SOUTH);
        panel.add(backButton, BorderLayout.NORTH);
        return panel;
    }

    private static JPanel createThermostatPanel(Font headerFont, Font labelFont, Thermostat thermostat, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(227, 173, 68, 195);
                Color color2 = new Color(195, 195, 202);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };

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
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "Home");
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(increaseButton);
        buttonsPanel.add(decreaseButton);

        panel.add(label, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        panel.add(backButton, BorderLayout.NORTH);
        return panel;
    }

    private static JPanel createFanPanel(Font headerFont, Font labelFont, Fan fan, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(102, 149, 189);
                Color color2 = new Color(181, 132, 209);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };

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
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "Home");
        });

        panel.add(label, BorderLayout.CENTER);
        panel.add(speedSelector, BorderLayout.SOUTH);
        panel.add(backButton, BorderLayout.NORTH);
        return panel;
    }
}