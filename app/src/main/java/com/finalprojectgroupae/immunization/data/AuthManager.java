package com.finalprojectgroupae.immunization.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * Lightweight credential manager that persists demo users in SharedPreferences.
 * This is purely client-side storage to support a basic login/sign-up flow.
 */
public class AuthManager {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";

    private static final String PREFS_NAME = "immunization_auth_prefs";
    private static final String KEY_USERS = "auth_users";
    private static final String KEY_ACTIVE_USERNAME = "auth_active_username";
    private static final String KEY_ACTIVE_NAME = "auth_active_name";
    private static final String KEY_ACTIVE_ROLE = "auth_active_role";

    private final SharedPreferences prefs;

    private static final UserSeed[] DEFAULT_USERS = new UserSeed[]{
            new UserSeed("Program Admin", "admin", "admin123", ROLE_ADMIN),
            new UserSeed("Ruth Gwiza", "ruth.gwiza", "26082", ROLE_USER),
            new UserSeed("Nziza Benjamin", "nzizabenjamin", "26240", ROLE_USER),
            new UserSeed("Cyubahiro Eddy Prince", "cyubahiroeddyprince", "24881", ROLE_USER),
            new UserSeed("Baraka Johnson Bright", "barakajohnsonbright", "25583", ROLE_USER),
            new UserSeed("Tonkuba Decon", "tonkubadecon", "25713", ROLE_USER),
            new UserSeed("Manira Theoneste", "maniratheoneste728", "26078", ROLE_USER),
            new UserSeed("Kidda Vinah", "kiddavinah", "25231", ROLE_USER),
            new UserSeed("Munezero Eugene", "munezeroeugene", "26506", ROLE_USER),
            new UserSeed("Serge Dukuziyaremye", "sergedukuziyaremye", "26084", ROLE_USER),
            new UserSeed("Ineza 74", "ineza74", "25948", ROLE_USER)
    };

    public AuthManager(@NonNull Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Seeds the store with curated demo accounts so first-time users can log in.
     */
    public void ensureSeedUsers() {
        for (UserSeed seed : DEFAULT_USERS) {
            signUpInternal(seed.displayName, seed.username, seed.password, seed.role, false);
        }
    }

    public boolean signUp(@NonNull String name,
                          @NonNull String username,
                          @NonNull String password,
                          @NonNull String role) {

        return signUpInternal(name, username, password, role, true);
    }

    public boolean login(@NonNull String username,
                         @NonNull String password,
                         @NonNull String role) {

        String normalizedUsername = normalizeIdentifier(username);
        String normalizedRole = normalizeRole(role);

        for (String entry : getUsers()) {
            UserRecord record = UserRecord.parse(entry);
            if (record == null) {
                continue;
            }
            if (record.username.equals(normalizedUsername)
                    && record.password.equals(password)
                    && record.role.equals(normalizedRole)) {

                prefs.edit()
                        .putString(KEY_ACTIVE_USERNAME, record.username)
                        .putString(KEY_ACTIVE_NAME, record.name)
                        .putString(KEY_ACTIVE_ROLE, record.role)
                        .apply();
                return true;
            }
        }
        return false;
    }

    public void logout() {
        prefs.edit()
                .remove(KEY_ACTIVE_USERNAME)
                .remove(KEY_ACTIVE_NAME)
                .remove(KEY_ACTIVE_ROLE)
                .apply();
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(prefs.getString(KEY_ACTIVE_USERNAME, null));
    }

    @Nullable
    public String getActiveRole() {
        return prefs.getString(KEY_ACTIVE_ROLE, null);
    }

    private boolean signUpInternal(@NonNull String name,
                                   @NonNull String username,
                                   @NonNull String password,
                                   @NonNull String role,
                                   boolean failIfExists) {

        String normalizedUsername = normalizeIdentifier(username);
        String normalizedRole = normalizeRole(role);
        if (!TextUtils.equals(normalizedRole, ROLE_ADMIN)
                && !TextUtils.equals(normalizedRole, ROLE_USER)) {
            return false;
        }

        Set<String> users = getUsers();
        String entryToReplace = null;
        for (String entry : users) {
            UserRecord record = UserRecord.parse(entry);
            if (record != null && record.username.equals(normalizedUsername)) {
                if (failIfExists) {
                    return false;
                } else {
                    // Replace the seeded account.
                    entryToReplace = entry;
                    break;
                }
            }
        }

        if (entryToReplace != null) {
            users.remove(entryToReplace);
        }

        users.add(UserRecord.join(name.trim(), normalizedUsername, password, normalizedRole));
        prefs.edit().putStringSet(KEY_USERS, users).apply();
        return true;
    }

    private Set<String> getUsers() {
        return new HashSet<>(prefs.getStringSet(KEY_USERS, new HashSet<>()));
    }

    private String normalizeIdentifier(@NonNull String handle) {
        return handle.trim().toLowerCase();
    }

    private String normalizeRole(@NonNull String role) {
        return role.trim().toLowerCase();
    }

    private static class UserRecord {
        final String name;
        final String username;
        final String password;
        final String role;

        private UserRecord(String name, String username, String password, String role) {
            this.name = name;
            this.username = username;
            this.password = password;
            this.role = role;
        }

        static String join(String name, String username, String password, String role) {
            return name + "|" + username + "|" + password + "|" + role;
        }

        @Nullable
        static UserRecord parse(@Nullable String entry) {
            if (TextUtils.isEmpty(entry)) {
                return null;
            }
            String[] tokens = entry.split("\\|");
            if (tokens.length != 4) {
                return null;
            }
            return new UserRecord(tokens[0], tokens[1], tokens[2], tokens[3]);
        }
    }

    private static class UserSeed {
        final String displayName;
        final String username;
        final String password;
        final String role;

        private UserSeed(String displayName, String username, String password, String role) {
            this.displayName = displayName;
            this.username = username;
            this.password = password;
            this.role = role;
        }
    }
}


