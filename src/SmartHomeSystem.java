import java.util.Scanner;

public class SmartHomeSystem {
    public static void main(String[] args) {
        // Create devices
        Device light = new Light("Living Room Light", false);
        Device thermostat = new Thermostat("Living Room Thermostat", 22);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWhich device do you want to check?");
            System.out.println("1. Light");
            System.out.println("2. Thermostat");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    light.displayStatus();
                    System.out.println("Do you want to turn it On or Off? (on/off/skip): ");
                    String lightAction = scanner.next();
                    if (lightAction.equalsIgnoreCase("on")) {
                        light.turnOn();
                    } else if (lightAction.equalsIgnoreCase("off")) {
                        light.turnOff();
                    }
                    break;

                case 2:
                    thermostat.displayStatus();
                    System.out.println("Do you want to turn it On or Off? (on/off/skip): ");
                    String thermostatAction = scanner.next();
                    if (thermostatAction.equalsIgnoreCase("on")) {
                        thermostat.turnOn();
                    } else if (thermostatAction.equalsIgnoreCase("off")) {
                        thermostat.turnOff();
                    }
                    System.out.println("Do you want to set a new temperature? (yes/no): ");
                    String tempAction = scanner.next();
                    if (tempAction.equalsIgnoreCase("yes")) {
                        System.out.print("Enter new temperature: ");
                        int newTemp = scanner.nextInt();
                        ((Thermostat) thermostat).setTemperature(newTemp);
                    }
                    break;

                case 3:
                    System.out.println("Exiting Smart Home System. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
