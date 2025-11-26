package com.eventplanner.app.util;

import com.eventplanner.app.model.Booking;
import com.eventplanner.app.model.Client;
import com.eventplanner.app.model.EventPackage;
import com.eventplanner.app.service.BookingService;
import com.eventplanner.app.service.ClientService;
import com.eventplanner.app.service.EventPackageService;

import java.io.*;
import java.util.List;

public class FileUtil {

    public static boolean saveClients(ClientService clientService, String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            List<Client> clients = clientService.getClients();
            for (Client c : clients) {
                pw.println(c.getId() + "," + escape(c.getName()) + "," + escape(c.getPhone()));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean loadClients(ClientService clientService, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return true; // nothing to load, not an error


        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = splitCsv(line, 3);
                if (parts == null) continue;
                int id = Integer.parseInt(parts[0]);
                String name = unescape(parts[1]);
                String phone = unescape(parts[2]);
                clientService.addClient(new Client(id, name, phone));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean savePackages(EventPackageService packageService, String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            List<EventPackage> packages = packageService.getPackages();
            for (EventPackage p : packages) {
                pw.println(p.getId() + "," + escape(p.getCategory()) + ","
                        + escape(p.getName()) + "," + p.getPrice());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean loadPackages(EventPackageService packageService, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return true;


        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = splitCsv(line, 4);
                if (parts == null) continue;
                int id = Integer.parseInt(parts[0]);
                String category = unescape(parts[1]);
                String name = unescape(parts[2]);
                double price = Double.parseDouble(parts[3]);
                packageService.addPackage(new EventPackage(id, category, name, price));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveBookings(BookingService bookingService, String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            List<Booking> bookings = bookingService.getBookings();
            for (Booking b : bookings) {
                pw.println(escape(b.getBookingId()) + ","
                        + b.getClient().getId() + ","
                        + b.getEventPackage().getId() + ","
                        + escape(b.getEventDate()));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean loadBookings(BookingService bookingService,
                                       ClientService clientService,
                                       EventPackageService packageService,
                                       String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return true;


        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = splitCsv(line, 4);
                if (parts == null) continue;
                String bookingId = unescape(parts[0]);
                int clientId = Integer.parseInt(parts[1]);
                int packageId = Integer.parseInt(parts[2]);
                String date = unescape(parts[3]);

                Client client = clientService.getClientById(clientId);
                EventPackage pkg = packageService.getPackageById(packageId);
                if (client == null || pkg == null) continue;

                bookingService.createBooking(new Booking(bookingId, client, pkg, date));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace(",", "\\,");
    }

    private static String unescape(String s) {
        if (s == null) return "";
        return s.replace("\\,", ",").replace("\\\\", "\\");
    }

    private static String[] splitCsv(String line, int expectedParts) {
        StringBuilder current = new StringBuilder();
        String[] result = new String[expectedParts];
        int index = 0;
        boolean escaping = false;

        for (char ch : line.toCharArray()) {
            if (escaping) {
                current.append(ch);
                escaping = false;
            } else if (ch == '\\') {
                escaping = true;
            } else if (ch == ',') {
                if (index >= expectedParts) return null;
                result[index++] = current.toString();
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }
        if (index != expectedParts - 1) return null;
        result[index] = current.toString();
        return result;
    }
}