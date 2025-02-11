package org.laborato.mdmlab.launcher.task;

import android.content.Context;
import android.os.AsyncTask;

import org.laborato.mdmlab.launcher.Const;
import org.laborato.mdmlab.launcher.helper.SettingsHelper;
import org.laborato.mdmlab.launcher.json.RemoteLogConfigResponse;
import org.laborato.mdmlab.launcher.server.ServerService;
import org.laborato.mdmlab.launcher.server.ServerServiceKeeper;
import org.laborato.mdmlab.launcher.util.RemoteLogger;

import retrofit2.Response;

public class GetRemoteLogConfigTask extends AsyncTask< Void, Integer, Integer > {
    private Context context;
    private SettingsHelper settingsHelper;

    public GetRemoteLogConfigTask( Context context ) {
        this.context = context;
        this.settingsHelper = SettingsHelper.getInstance( context );
    }

    @Override
    protected Integer doInBackground( Void... voids ) {
        ServerService serverService = ServerServiceKeeper.getServerServiceInstance(context);
        ServerService secondaryServerService = ServerServiceKeeper.getSecondaryServerServiceInstance(context);
        Response<RemoteLogConfigResponse> response = null;

        try {
            response = serverService.
                    getRemoteLogConfig(settingsHelper.getServerProject(), settingsHelper.getDeviceId()).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (response == null) {
                response = secondaryServerService.
                        getRemoteLogConfig(settingsHelper.getServerProject(), settingsHelper.getDeviceId()).execute();
            }

            if ( response.isSuccessful() ) {
                if ( Const.STATUS_OK.equals( response.body().getStatus() ) && response.body().getData() != null ) {
                    RemoteLogger.updateConfig(context, response.body().getData());

                    return Const.TASK_SUCCESS;
                } else {
                    return Const.TASK_ERROR;
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return Const.TASK_NETWORK_ERROR;
    }

}
