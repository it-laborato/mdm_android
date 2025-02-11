package org.laborato.mdmlab.launcher.pro.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

/**
 * In open-source version, the service checking foreground apps is just a stub;
 * this option is available in Pro-version only
 */
public class CheckForegroundAppAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Stub
    }

    @Override
    public void onInterrupt() {
        // Stub
    }
}
