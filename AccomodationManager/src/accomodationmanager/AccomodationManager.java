package accomodationmanager;

import java.util.Scanner;

/**
 * Author: MUSHABE ALLOYSIUS
 */
public class AccomodationManager {
    
    // Represents a Gym or Swimming Pool area
    static class Area {
        private final String areaName;
        private int occupants;
        private final boolean[] lights;
        
        public Area(String areaName) {
            this.areaName = areaName;
            this.occupants = 0;
            this.lights = new boolean[3]; // Note that 3 lights, all start OFF
        }
        
        // Add people to this area
        public void addOccupants(int n) {
            if (n > 0) {
                occupants += n;
                System.out.println(n + " occupants added to " + areaName);
            } else {
                System.out.println("Number of occupants must be positive!");
            }
        }
        
        // Remove people from this area (won't go below zero)
        public void removeOccupants(int n) {
            if (n > 0) {
                int oldCount = occupants;
                occupants = Math.max(0, occupants - n);
                int removed = oldCount - occupants;
                if (removed > 0) {
                    System.out.println(removed + " occupants removed from " + areaName);
                }
                if (removed < n) {
                    System.out.println("Warning: Could only remove " + removed + " occupants (reached minimum of 0)");
                }
            } else {
                System.out.println("Number of occupants must be positive!");
            }
        }
        
        // Turn ON a specific light (1, 2, or 3)
        public void switchOnLight(int lightNumber) {
            if (lightNumber >= 1 && lightNumber <= 3) {
                lights[lightNumber - 1] = true;
                System.out.println("Light " + lightNumber + " switched ON in " + areaName);
            }
        }
        
        // Turn OFF a specific light (1, 2, or 3)
        public void switchOffLight(int lightNumber) {
            if (lightNumber >= 1 && lightNumber <= 3) {
                lights[lightNumber - 1] = false;
                System.out.println("Light " + lightNumber + " switched OFF in " + areaName);
            }
        }
        
        // Show current status of this area
        public void displayStatus() {
            System.out.println("\n=== " + areaName + " Status Report ===");
            System.out.println("Occupants: " + occupants);
            System.out.print("Lights: ");
            for (int i = 0; i < lights.length; i++) {
                System.out.print("Light " + (i + 1) + ": " + (lights[i] ? "ON" : "OFF"));
                if (i < lights.length - 1) System.out.print(" | ");
            }
            System.out.println();
        }
        
        // Getters
        public String getAreaName() { return areaName; }
        public int getOccupants() { return occupants; }
        public boolean[] getLights() { return lights.clone(); } 
    }
    
    // The two areas we manage
    private static final Area GYM_AREA = new Area("Gym Area");
    private static final Area SWIMMING_AREA = new Area("Swimming Pool Area");
    private static Area activeArea = GYM_AREA; // Start with Gym as active area
    
    // Main program starts here
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*****************************************************");
        System.out.println("* Welcome to Speke Apartments Accommodation Manager *");
        System.out.println("*           Kampala - Uganda                        *");
        System.out.println("*****************************************************");
        
        runMenu(scanner); // Start the menu system
    }
    
    // Main menu loop - keeps running until user quits
    private static void runMenu(Scanner scanner) {
        String command;
        
        while (true) {
            displayMainMenu();
            System.out.print("Enter your choice: ");
            command = scanner.nextLine().trim().toUpperCase();
            
            if (command.isEmpty()) {
                System.out.println("Please enter a command.");
                continue;
            }
            
            // Handle user command
            switch (command.charAt(0)) {
                case 'S': selectArea(scanner); break;      // Change active area
                case 'W': addOccupants(scanner); break;    // Add people
                case 'X': removeOccupants(scanner); break; // Remove people
                case 'Y': switchOnLight(scanner); break;   // Turn light ON
                case 'Z': switchOffLight(scanner); break;  // Turn light OFF
                case 'R': reportStatus(); break;           // Show status
                case 'Q': 
                    System.out.println("Thank you for using Speke Apartments Manager. Goodbye!");
                    System.out.println("            Developed by Alloysius Mushabe   ");
                    scanner.close();
                    return; // Exit program
                default:
                    System.out.println("Invalid command '" + command + "'. Please try again.");
            }
        }
    }
    
    // Show the main menu options
    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("Active Area: " + (activeArea != null ? activeArea.getAreaName() : "None"));
        System.out.println("S - Select Active Area (G = Gym, P = Swimming)");
        System.out.println("W - Add Occupants");
        System.out.println("X - Remove Occupants");
        System.out.println("Y - Switch ON Light (1-3)");
        System.out.println("Z - Switch OFF Light (1-3)");
        System.out.println("R - Report Status");
        System.out.println("Q - Quit Program");
        System.out.println("=================");
    }
    
    // Let user choose between Gym and Swimming Pool
    private static void selectArea(Scanner scanner) {
        System.out.print("Select active area (G for Gym, P for Swimming): ");
        String areaChoice = scanner.nextLine().trim().toUpperCase();
        
        switch (areaChoice) {
            case "G":
                activeArea = GYM_AREA;
                System.out.println("Gym Area is now active.");
                break;
            case "P":
                activeArea = SWIMMING_AREA;
                System.out.println("Swimming Pool Area is now active.");
                break;
            default:
                System.out.println("Invalid area selection. Please enter 'G' for Gym or 'P' for Swimming.");
        }
    }
    
    // Add people to current area
    private static void addOccupants(Scanner scanner) {
        if (!checkActiveArea()) return;
        int n = getValidInteger(scanner, "Enter number of occupants to add: ");
        activeArea.addOccupants(n);
    }
    
    // Remove people from current area
    private static void removeOccupants(Scanner scanner) {
        if (!checkActiveArea()) return;
        int n = getValidInteger(scanner, "Enter number of occupants to remove: ");
        activeArea.removeOccupants(n);
    }
    
    // Turn ON a light in current area
    private static void switchOnLight(Scanner scanner) {
        if (!checkActiveArea()) return;
        int lightNumber = getValidLightNumber(scanner, "Enter light number to switch ON (1-3): ");
        activeArea.switchOnLight(lightNumber);
    }
    
    // Turn OFF a light in current area
    private static void switchOffLight(Scanner scanner) {
        if (!checkActiveArea()) return;
        int lightNumber = getValidLightNumber(scanner, "Enter light number to switch OFF (1-3): ");
        activeArea.switchOffLight(lightNumber);
    }
    
    // Show status of current area
    private static void reportStatus() {
        if (!checkActiveArea()) return;
        activeArea.displayStatus();
    }
    
    // Check if an area is selected
    private static boolean checkActiveArea() {
        if (activeArea == null) {
            System.out.println("Error: No active area selected. Please use 'S' to select an area first.");
            return false;
        }
        return true;
    }
    
    // Get a valid number from user
    private static int getValidInteger(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Please enter a non-negative number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
    
    // Get a valid light number (1, 2, or 3)
    private static int getValidLightNumber(Scanner scanner, String prompt) {
        while (true) {
            int lightNumber = getValidInteger(scanner, prompt);
            if (lightNumber >= 1 && lightNumber <= 3) {
                return lightNumber;
            } else {
                System.out.println("Invalid light number. Please enter 1, 2, or 3.");
            }
        }
    }
}