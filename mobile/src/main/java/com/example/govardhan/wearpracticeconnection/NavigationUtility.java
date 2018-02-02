package com.example.govardhan.wearpracticeconnection;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;



public class NavigationUtility {

    public static void addAnimationToHardwareBackButtonForFragment(final Fragment fragment) {

        fragment.getView().setFocusableInTouchMode(true);
        fragment.getView().requestFocus();

        fragment.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        fragment.getActivity().finish();
                        fragment.getActivity().overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public static void animateLeft(Activity activity) {
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
