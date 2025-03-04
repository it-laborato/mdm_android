package org.laborato.mdmlab.launcher.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.laborato.mdmlab.launcher.BuildConfig;
import org.laborato.mdmlab.launcher.json.Application;
import org.laborato.mdmlab.launcher.json.ApplicationSetting;
import org.laborato.mdmlab.launcher.json.RemoteFile;
import org.laborato.mdmlab.launcher.json.ServerConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SettingsHelper {

    private static final String PREFERENCES_ID = ".helpers.PREFERENCES";
    private static final String PREF_KEY_BASE_URL = ".helpers.BASE_URL";
    private static final String PREF_KEY_SECONDARY_BASE_URL = ".helpers.SECONDARY_BASE_URL";
    private static final String PREF_KEY_SERVER_PROJECT = ".helpers.SERVER_PROJECT";
    private static final String PREF_KEY_DEVICE_ID = ".helpers.DEVICE_ID";
    private static final String PREF_KEY_CUSTOMER = ".helpers.CUSTOMER";
    private static final String PREF_KEY_CONFIG_NAME = ".helpers.CONFIG_NAME";
    private static final String PREF_KEY_GROUP = ".helpers.GROUP";
    private static final String PREF_KEY_DEVICE_ID_USE = ".helpers.DEVICE_ID_USE";
    private static final String PREF_KEY_CONFIG = ".helpers.CONFIG";
    private static final String PREF_KEY_IP_ADDRESS = ".helpers.IP_ADDRESS";
    private static final String PREF_QR_PROVISIONING = ".helpers.QR_PROVISIONING";
    private static final String PREF_CFG_UPDATE_TIMESTAMP = ".helpers.CFG_UPDATE_TIMESTAMP";
    private static final String PREF_KEY_ACTIVITY_RUNNING = ".helpers.ACTIVITY_RUNNING";
    private static final String PREF_KEY_RESTORE_LAUNCHER = ".helpers.NEED_LAUNCHER_RESET";
    private static final String PREF_KEY_INTEGRATED_PROVISIONING_FLOW = ".helpers.INTEGRATED_PROVISIONING_FLOW";
    private static final String PREF_KEY_LAST_APP_UPDATE_STATE = ".helpers.LAST_APP_UPDATE_STATE";
    private static final String PREF_KEY_APP_START_TIME = ".helpers.APP_START_TIME";
    // This prefix is for the compatibility with a legacy package name
    private static String PACKAGE_NAME;

    private SharedPreferences sharedPreferences;
    private ServerConfig config;
    private ServerConfig oldConfig;
    private Map<String,ApplicationSetting> appSettings = new HashMap<>();
    private Set<String> allowedClasses = new HashSet<>();

    private static SettingsHelper instance;

    public static SettingsHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SettingsHelper(context);
        }

        return instance;
    }

    public SettingsHelper(Context context) {
        PACKAGE_NAME = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(PACKAGE_NAME + PREFERENCES_ID, Context.MODE_PRIVATE );
        initConfig();
    }

    public void refreshConfig(Context context) {
        if (config == null) {
            sharedPreferences = context.getSharedPreferences(PACKAGE_NAME + PREFERENCES_ID, Context.MODE_PRIVATE );
            initConfig();
        }
    }

    private void initConfig() {
        try {
            if ( sharedPreferences.contains(PACKAGE_NAME + PREF_KEY_CONFIG) ) {
                ObjectMapper mapper = new ObjectMapper();
                config = mapper.readValue(
                        sharedPreferences.getString(PACKAGE_NAME + PREF_KEY_CONFIG, "" ),
                        ServerConfig.class );
                updateAppSettingsMap(config);
                updateAllowedClassesSet(config);
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    // Warning: this may return false if the launcher has been updated from older version
    public boolean isQrProvisioning() {
        return sharedPreferences.getBoolean(PACKAGE_NAME + PREF_QR_PROVISIONING, false);
    }

    public boolean setQrProvisioning(boolean value) {
        return sharedPreferences.edit().putBoolean(PACKAGE_NAME + PREF_QR_PROVISIONING, value).commit();
    }

    public boolean isIntegratedProvisioningFlow() {
        return sharedPreferences.getBoolean(PACKAGE_NAME + PREF_KEY_INTEGRATED_PROVISIONING_FLOW, false);
    }

    public boolean setIntegratedProvisioningFlow(boolean value) {
        return sharedPreferences.edit().putBoolean(PACKAGE_NAME + PREF_KEY_INTEGRATED_PROVISIONING_FLOW, value).commit();
    }

    public boolean isBaseUrlSet() {
        return sharedPreferences.getString(PACKAGE_NAME + PREF_KEY_BASE_URL, null ) != null;
    }

    public String getBaseUrl() {
        return sharedPreferences.getString(PACKAGE_NAME + PREF_KEY_BASE_URL, BuildConfig.BASE_URL );
    }

    public boolean setBaseUrl( String baseUrl ) {
        return sharedPreferences.edit().putString(PACKAGE_NAME + PREF_KEY_BASE_URL, baseUrl ).commit();
    }

    public String getSecondaryBaseUrl() {
        return sharedPreferences.getString(PACKAGE_NAME + PREF_KEY_SECONDARY_BASE_URL, BuildConfig.SECONDARY_BASE_URL );
    }

    public boolean setSecondaryBaseUrl( String secondaryBaseUrl ) {
        return sharedPreferences.edit().putString(PACKAGE_NAME + PREF_KEY_SECONDARY_BASE_URL, secondaryBaseUrl ).commit();
    }

    public String getServerProject() {
        return sharedPreferences.getString(PACKAGE_NAME + PREF_KEY_SERVER_PROJECT, BuildConfig.SERVER_PROJECT );
    }

    public boolean setServerProject( String serverProject ) {
        return sharedPreferences.edit().putString(PACKAGE_NAME + PREF_KEY_SERVER_PROJECT, serverProject ).commit();
    }

    public String getDeviceId() {
        return sharedPreferences.getString(PACKAGE_NAME + PREF_KEY_DEVICE_ID,"" );
    }

    public boolean setDeviceId( String deviceId ) {
        return sharedPreferences.edit().putString(PACKAGE_NAME + PREF_KEY_DEVICE_ID, deviceId ).commit();
    }

    public String getExternalIp() {
        return sharedPreferences.getString(PACKAGE_NAME + PREF_KEY_IP_ADDRESS, "" );
    }

    public boolean setExternalIp( String externalIp ) {
        if (externalIp == null) {
            externalIp = "";
        }
        return sharedPreferences.edit().putString(PACKAGE_NAME + PREF_KEY_IP_ADDRESS, externalIp ).commit();
    }

    public boolean isMainActivityRunning() {
        return sharedPreferences.getBoolean(PACKAGE_NAME + PREF_KEY_ACTIVITY_RUNNING, false );
    }

    public boolean setMainActivityRunning(boolean running) {
        return sharedPreferences.edit().putBoolean(PACKAGE_NAME + PREF_KEY_ACTIVITY_RUNNING, running ).commit();
    }

    public boolean isRestoreLauncher() {
        return sharedPreferences.getBoolean(PACKAGE_NAME + PREF_KEY_RESTORE_LAUNCHER, false);
    }

    public boolean setRestoreLauncher(boolean restore) {
        return sharedPreferences.edit().putBoolean(PACKAGE_NAME + PREF_KEY_RESTORE_LAUNCHER, restore ).commit();
    }

    public long getConfigUpdateTimestamp() {
        return sharedPreferences.getLong(PACKAGE_NAME + PREF_CFG_UPDATE_TIMESTAMP, 0);
    }

    public boolean setConfigUpdateTimestamp(long timestamp) {
        return sharedPreferences.edit().putLong(PACKAGE_NAME + PREF_CFG_UPDATE_TIMESTAMP, timestamp).commit();
    }

    public boolean setCreateOptionCustomer(String customer) {
        if (customer == null) {
            return sharedPreferences.edit().remove(PACKAGE_NAME + PREF_KEY_CUSTOMER).commit();
        } else {
            return sharedPreferences.edit().putString(PACKAGE_NAME + PREF_KEY_CUSTOMER, customer ).commit();
        }
    }

    public String getCreateOptionCustomer() {
        return sharedPreferences.getString(PACKAGE_NAME + PREF_KEY_CUSTOMER, null);
    }

    public boolean setDeviceIdUse(String deviceIdUse) {
        if (deviceIdUse == null) {
            return sharedPreferences.edit().remove(PACKAGE_NAME + PREF_KEY_DEVICE_ID_USE).commit();
        } else {
            return sharedPreferences.edit().putString(PACKAGE_NAME + PREF_KEY_DEVICE_ID_USE, deviceIdUse ).commit();
        }
    }

    public String getDeviceIdUse() {
        return sharedPreferences.getString(PACKAGE_NAME + PREF_KEY_DEVICE_ID_USE, null);
    }

    public boolean setLastAppUpdateState(boolean lastAppUpdateState) {
        return sharedPreferences.edit().putBoolean(PACKAGE_NAME + PREF_KEY_LAST_APP_UPDATE_STATE, lastAppUpdateState).commit();
    }

    public boolean getLastAppUpdateState() {
        return sharedPreferences.getBoolean(PACKAGE_NAME + PREF_KEY_LAST_APP_UPDATE_STATE, false);
    }

    public boolean setAppStartTime(long time) {
        return sharedPreferences.edit().putLong(PACKAGE_NAME + PREF_KEY_APP_START_TIME, time).commit();
    }

    public long getAppStartTime() {
        return sharedPreferences.getLong(PACKAGE_NAME + PREF_KEY_APP_START_TIME, 0);
    }

    public boolean setCreateOptionConfigName(String configName) {
        if (configName == null) {
            return sharedPreferences.edit().remove(PACKAGE_NAME + PREF_KEY_CONFIG_NAME).commit();
        } else {
            return sharedPreferences.edit().putString(PACKAGE_NAME + PREF_KEY_CONFIG_NAME, configName ).commit();
        }
    }

    public String getCreateOptionConfigName() {
        return sharedPreferences.getString(PACKAGE_NAME + PREF_KEY_CONFIG_NAME, null);
    }

    public boolean setCreateOptionGroup(Set<String> group) {
        if (group == null) {
            return sharedPreferences.edit().remove(PACKAGE_NAME + PREF_KEY_GROUP).commit();
        } else {
            return sharedPreferences.edit().putStringSet(PACKAGE_NAME + PREF_KEY_GROUP, group).commit();
        }
    }

    public Set<String> getCreateOptionGroup() {
        return sharedPreferences.getStringSet(PACKAGE_NAME + PREF_KEY_GROUP, null);
    }

    public void updateConfig( ServerConfig config ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            sharedPreferences.edit().putString(PACKAGE_NAME + PREF_KEY_CONFIG, objectMapper.writeValueAsString( config ) ).commit();
        } catch ( Exception e ) {
            e.printStackTrace();
            // Do not apply changes when there's an error while writing settings
            return;
        }
        updateAppSettingsMap(config);
        updateAllowedClassesSet(config);
        this.oldConfig = this.config;
        this.config = config;
    }

    public ServerConfig getConfig() {
        return config;
    }

    public void removeRemoteFile(RemoteFile remoteFile) {
        Iterator<RemoteFile> it = config.getFiles().iterator();
        while (it.hasNext()) {
            RemoteFile file = it.next();
            if ( file.getPath().equals( remoteFile.getPath() ) ) {
                it.remove();
                updateConfig(config);
                return;
            }
        }
    }

    public void removeApplication(Application application) {
        Iterator<Application> it = config.getApplications().iterator();
        while (it.hasNext()) {
            Application app = it.next();
            if (app.getPkg().equals(application.getPkg())) {
                it.remove();
                updateConfig(config);
                return;
            }
        }
    }

    public void removeApplicationUrl(Application application) {
        Iterator<Application> it = config.getApplications().iterator();
        while (it.hasNext()) {
            Application app = it.next();
            if (app.getPkg().equals(application.getPkg())) {
                app.setUrl(null);
                updateConfig(config);
                return;
            }
        }
    }

    private void updateAppSettingsMap(ServerConfig config) {
        if (config == null || config.getApplicationSettings() == null) {
            return;
        }
        appSettings.clear();
        for (ApplicationSetting setting : config.getApplicationSettings()) {
            String key = setting.getPackageId() + "." + setting.getName();
            appSettings.put(key, setting);
        }
    }

    private void updateAllowedClassesSet(ServerConfig config) {
        if (config == null || config.getAllowedClasses() == null) {
            return;
        }
        String[] allowedClassesList = config.getAllowedClasses().split(",");
        for (int n = 0; n < allowedClassesList.length; n++) {
            allowedClassesList[n] = allowedClassesList[n].trim();
        }
        // Is it thread-safe? Hopefully yes
        allowedClasses = new HashSet<>(Arrays.asList(allowedClassesList));
    }

    public String getAppPreference(String packageId, String attr) {
        String key = packageId + "." + attr;
        ApplicationSetting setting = appSettings.get(key);
        if (setting == null) {
            return null;
        }
        return setting.getValue();
    }

    public boolean setAppPreference(String packageId, String attr, String value) {
        String key = packageId + "." + attr;
        ApplicationSetting setting = appSettings.get(key);
        if (setting == null) {
            setting = new ApplicationSetting();
            setting.setPackageId(packageId);
            setting.setName(attr);
            setting.setType(1);     // 1 is string (default value)
            setting.setReadOnly(false);
            appSettings.put(key, setting);
        }
        if (setting.isReadOnly()) {
            return false;
        }
        setting.setValue(value);
        setting.setLastUpdate(System.currentTimeMillis());
        return true;
    }

    public void commitAppPreferences(String packageId) {
        // TODO: send new preferences to server
    }

    public Set<String> getAllowedClasses() {
        return allowedClasses;
    }
}
