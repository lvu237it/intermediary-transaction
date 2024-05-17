package utils;

import java.util.HashMap;
import Model.User;
import java.util.Map;
import java.util.Set;

public class UserTokenMap {

    private static UserTokenMap instance;

    private final HashMap<String, String> userTokenMap;

    public UserTokenMap() {
        userTokenMap = new HashMap<>();
    }

    public static synchronized UserTokenMap getInstance() {
        if (instance == null) {
            instance = new UserTokenMap();
        }
        return instance;
    }

    public synchronized void putUserToTokenMap(User user, String jwt) {
        userTokenMap.put(user.getUserName(), jwt);
    }

    public synchronized String getUsernameByToken(String jwtToken) {
        JwtGenerator jwtGenerator = new JwtGenerator();
        // Check if token is expired
        boolean isTokenExp = jwtGenerator.isTokenExpired(jwtToken);
        if (isTokenExp) {
            return null;
        }
        // Find username by jwtToken
        for (Map.Entry<String, String> entry : userTokenMap.entrySet()) {
            if (entry.getValue().equals(jwtToken)) {
                return entry.getKey(); // Return username when jwtToken is found
            }
        }
        return null;
    }

    // Method to return entry set of userTokenMap
    public synchronized Set<Map.Entry<String, String>> entrySet() {
        return userTokenMap.entrySet();
    }
}
