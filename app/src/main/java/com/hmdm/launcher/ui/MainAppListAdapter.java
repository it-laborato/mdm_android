package com.hmdm.launcher.ui;

import android.app.Activity;

/**
 * Created by Ivan Lozenko on 21.02.2017.
 */

public class MainAppListAdapter extends BaseAppListAdapter {

    public MainAppListAdapter(Activity parentActivity, OnAppChooseListener appChooseListener, SwitchAdapterListener switchAdapterListener) {
        super(parentActivity, appChooseListener, switchAdapterListener);
        items = AppShortcutManager.getInstance().getInstalledApps(parentActivity, false);
        initShortcuts();
    }
}
