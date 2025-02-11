package org.laborato.mdmlab.launcher.server;


import org.laborato.mdmlab.launcher.db.LocationTable;
import org.laborato.mdmlab.launcher.json.DetailedInfo;
import org.laborato.mdmlab.launcher.json.DetailedInfoConfigResponse;
import org.laborato.mdmlab.launcher.json.DeviceCreateOptions;
import org.laborato.mdmlab.launcher.json.DeviceInfo;
import org.laborato.mdmlab.launcher.json.PushResponse;
import org.laborato.mdmlab.launcher.json.RemoteLogConfigResponse;
import org.laborato.mdmlab.launcher.json.RemoteLogItem;
import org.laborato.mdmlab.launcher.json.ServerConfigResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServerService {

    static final String REQUEST_SIGNATURE_HEADER = "X-Request-Signature";
    static final String CPU_ARCH_HEADER = "X-CPU-Arch";

    @POST("{project}/rest/public/sync/configuration/{number}")
    Call<ResponseBody> createAndGetRawServerConfig(@Path("project") String project,
                                                   @Path("number") String number,
                                                   @Header(REQUEST_SIGNATURE_HEADER) String signature,
                                                   @Header(CPU_ARCH_HEADER) String cpuArch,
                                                   @Body DeviceCreateOptions createOptions);

    @GET("{project}/rest/public/sync/configuration/{number}")
    Call<ResponseBody> getRawServerConfig(@Path("project") String project,
                                          @Path("number") String number,
                                          @Header(REQUEST_SIGNATURE_HEADER) String signature,
                                          @Header(CPU_ARCH_HEADER) String cpuArch);

    @POST("{project}/rest/public/sync/configuration/{number}")
    Call<ServerConfigResponse> createAndGetServerConfig(@Path("project") String project,
                                                        @Path("number") String number,
                                                        @Header(REQUEST_SIGNATURE_HEADER) String signature,
                                                        @Header(CPU_ARCH_HEADER) String cpuArch,
                                                        @Body DeviceCreateOptions createOptions);

    @GET("{project}/rest/public/sync/configuration/{number}")
    Call<ServerConfigResponse> getServerConfig(@Path("project") String project,
                                               @Path("number") String number,
                                               @Header(REQUEST_SIGNATURE_HEADER) String signature,
                                               @Header(CPU_ARCH_HEADER) String cpuArch);

    @POST("{project}/rest/public/sync/info")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> sendDevice(@Path("project") String project, @Body DeviceInfo deviceInfo);

    @GET("{project}/rest/notifications/device/{number}")
    Call<PushResponse> queryPushNotifications(@Path("project") String project,
                                              @Path("number") String number,
                                              @Header(REQUEST_SIGNATURE_HEADER) String signature);

    @GET("{project}/rest/notification/polling/{number}")
    Call<PushResponse> queryPushLongPolling(@Path("project") String project,
                                            @Path("number") String number,
                                            @Header(REQUEST_SIGNATURE_HEADER) String signature);

    @GET( "{project}/rest/plugins/devicelog/log/rules/{number}" )
    Call<RemoteLogConfigResponse> getRemoteLogConfig(@Path("project") String project, @Path("number") String number);

    @POST("{project}/rest/plugins/devicelog/log/list/{number}")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> sendLogs(@Path("project") String project, @Path("number") String number, @Body List<RemoteLogItem> logItems);

    @PUT("{project}/rest/plugins/deviceinfo/deviceinfo/public/{number}")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> sendDetailedInfo(@Path("project") String project, @Path("number") String number, @Body List<DetailedInfo> infoItems);

    @PUT("{project}/rest/plugins/devicelocations/public/update/{number}")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> sendLocations(@Path("project") String project, @Path("number") String number, @Body List<LocationTable.Location> locationItems);

    @GET( "{project}/rest/plugins/deviceinfo/deviceinfo-plugin-settings/device/{number}" )
    Call<DetailedInfoConfigResponse> getDetailedInfoConfig(@Path("project") String project, @Path("number") String number);

    @POST("{project}/rest/plugins/devicereset/public/{number}")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> confirmDeviceReset(@Path("project") String project, @Path("number") String number, @Body DeviceInfo deviceInfo);

    @POST("{project}/rest/plugins/devicereset/public/reboot/{number}")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> confirmReboot(@Path("project") String project, @Path("number") String number, @Body DeviceInfo deviceInfo);

    @POST("{project}/rest/plugins/devicereset/public/password/{number}")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> confirmPasswordReset(@Path("project") String project, @Path("number") String number, @Body DeviceInfo deviceInfo);

}
