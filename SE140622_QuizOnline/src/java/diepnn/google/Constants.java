/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.google;

/**
 *
 * @author Delwyn
 */
public class Constants {
    public static String GOOGLE_CLIENT_ID = 
            "535579488853-7a2qfj3d2rsa2t320t3s7caf8gtnd2pm.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "rsnYLsWQvLz1YQ5MhPmN9d1U";
    public static String GOOGLE_REDIRECT_URI = 
            "http://localhost:8080/QuizOnline/googleLogin";
    public static String GOOGLE_LINK_GET_TOKEN = 
            "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO =
            "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";
}
