package com.hmdm.launcher.task;

import android.content.Context;
import android.os.AsyncTask;

import com.hmdm.launcher.Const;
import com.hmdm.launcher.helper.SettingsHelper;
import com.hmdm.launcher.json.DeviceInfo;
import com.hmdm.launcher.server.ServerService;
import com.hmdm.launcher.server.ServerServiceKeeper;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ConfirmPasswordResetTask extends AsyncTask< DeviceInfo, Integer, Integer > {

    private Context context;
    private SettingsHelper settingsHelper;

    public ConfirmPasswordResetTask(Context context ) {
        this.context = context;
        this.settingsHelper = SettingsHelper.getInstance( context );
    }

    @Override
    protected Integer doInBackground( DeviceInfo... info ) {
        ServerService serverService = ServerServiceKeeper.getServerServiceInstance(context);
        ServerService secondaryServerService = ServerServiceKeeper.getSecondaryServerServiceInstance(context);
        Response< ResponseBody > response = null;

        try {
            response = serverService.confirmPasswordReset(settingsHelper.getServerProject(), info[0].getDeviceId(), info[0]).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (response == null) {
                response = secondaryServerService.confirmPasswordReset(settingsHelper.getServerProject(), info[0].getDeviceId(), info[0]).execute();
            }
            if ( response.isSuccessful() ) {
                return Const.TASK_SUCCESS;
            }
        }
        catch ( Exception e ) { e.printStackTrace(); }

        return Const.TASK_ERROR;
    }
}
