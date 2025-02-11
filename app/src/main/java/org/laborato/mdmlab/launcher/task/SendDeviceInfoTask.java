package org.laborato.mdmlab.launcher.task;

import android.content.Context;
import android.os.AsyncTask;

import org.laborato.mdmlab.launcher.Const;
import org.laborato.mdmlab.launcher.helper.SettingsHelper;
import org.laborato.mdmlab.launcher.json.DeviceInfo;
import org.laborato.mdmlab.launcher.server.ServerService;
import org.laborato.mdmlab.launcher.server.ServerServiceKeeper;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class SendDeviceInfoTask extends AsyncTask< DeviceInfo, Integer, Integer > {

    private Context context;
    private SettingsHelper settingsHelper;

    public SendDeviceInfoTask( Context context ) {
        this.context = context;
        this.settingsHelper = SettingsHelper.getInstance( context );
    }

    @Override
    protected Integer doInBackground( DeviceInfo... info ) {
        ServerService serverService = ServerServiceKeeper.getServerServiceInstance(context);
        ServerService secondaryServerService = ServerServiceKeeper.getSecondaryServerServiceInstance(context);
        Response< ResponseBody > response = null;

        try {
            response = serverService.sendDevice(settingsHelper.getServerProject(), info[ 0 ]).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (response == null) {
                response = secondaryServerService.sendDevice(settingsHelper.getServerProject(), info[0]).execute();
            }
            if ( response.isSuccessful() ) {
                return Const.TASK_SUCCESS;
            }
        }
        catch ( Exception e ) { e.printStackTrace(); }

        return Const.TASK_ERROR;
    }
}
