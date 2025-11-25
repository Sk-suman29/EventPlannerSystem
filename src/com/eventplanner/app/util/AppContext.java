package com.eventplanner.app.util;

//import com.eventplanner.app.model.SystemUser;
//import com.eventplanner.app.service.AuthService;
import com.eventplanner.app.service.BookingService;
import com.eventplanner.app.service.ClientService;
import com.eventplanner.app.service.EventPackageService;

public class AppContext {

    private static final ClientService CLIENT_SERVICE = new ClientService();
    private static final EventPackageService PACKAGE_SERVICE = new EventPackageService();
    private static final BookingService BOOKING_SERVICE = new BookingService();
  //  private static final AuthService AUTH_SERVICE = new AuthService();

  //  private static SystemUser currentUser;

    public static ClientService getClientService() { return CLIENT_SERVICE; }
    public static EventPackageService getPackageService() { return PACKAGE_SERVICE; }
    public static BookingService getBookingService() { return BOOKING_SERVICE; }
   // public static AuthService getAuthService() { return AUTH_SERVICE; }

  //  public static SystemUser getCurrentUser() { return currentUser; }
   // public static void setCurrentUser(SystemUser user) { currentUser = user; }
   // public static void clearCurrentUser() { currentUser = null; }
}