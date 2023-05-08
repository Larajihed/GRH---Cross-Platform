/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP;

import javafx.geometry.Pos;
import COM.HRSTORMDESKTOP.models.user.User;
import org.controlsfx.control.Notifications;

/**
 *
 * @author 21623
 */
public  class  Config {
    private static final String url="jdbc:mysql://localhost:3306/pidev";
    private static final String user = "root";
    private static final String password = "";
    private static final String Mail= "HRSTORM@gmail.com";
    private static final String MailPassword ="yahdikyafourat";
    public static User UserStatic ;
    public static User UserManagedAdd;
    public static User UserManagedUpdate;
    public static User UserManagedDelete;
    private static String VerificationCode;

    
    public static User getUserManagedAdd() {
        return UserManagedAdd;
    }

    public static void setUserManagedAdd(User UserManagedAdd) {
        Config.UserManagedAdd = UserManagedAdd;
    }
 
    public static User getUserManagedUpdate() {
        return UserManagedUpdate;
    }

    public static void setUserManagedUpdate(User UserManagedUpdate) {
        Config.UserManagedUpdate = UserManagedUpdate;
    }

    public static User getUserManagedDelete() {
        return UserManagedDelete;
    }

    public static void setUserManagedDelete(User UserManagedDelete) {
        Config.UserManagedDelete = UserManagedDelete;
    }
    
    public static String getVerificationCode() {
        return VerificationCode;
    }

    public static void setVerificationCode(String VerificationCode) {
        Config.VerificationCode = VerificationCode;
    }

    public static User getUserStatic() {
        return UserStatic;
    }

    public static void setUserStatic(User UserStatic) {
        Config.UserStatic = UserStatic;
    }
    
    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static String getMail() {
        return Mail;
    }

    public static String getMailPassword() {
        return MailPassword;
    }
          public static void showNotification(String finalOutputDestination, String title) {
    Notifications.create()
            .title(title)
            .text(finalOutputDestination)
            .position(Pos.TOP_CENTER)
            .show();
}


}
