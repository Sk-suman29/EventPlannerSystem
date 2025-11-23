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