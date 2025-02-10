package com.hmdm.launcher.databinding;

import androidx.databinding.BindingAdapter;
import android.view.View;

public class ViewBindingUtils {

    @BindingAdapter( { "boolToVisible" } )
    public static void boolToVisible( View view, boolean boolToVisible ) {
        view.setVisibility( boolToVisible ? View.VISIBLE : View.GONE );
    }

    @BindingAdapter( { "boolToDisable" } )
    public static void boolToDisable( View view, boolean boolToDisable ) {
        view.setEnabled( !boolToDisable );
    }

}
