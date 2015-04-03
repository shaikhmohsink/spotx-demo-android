package com.spotxchange.demo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

public class MainActivity extends Activity implements View.OnClickListener {

    private final String HOCKEYAPP_API_KEY = "d1fb52c9a6a4b7c0d860b73434113535";

    private WebView mCookieWebView;

    public static final String DEFAULT_CHANNEL_ID_HINT = "90782";
    // Integration = 82949
    // Stage2 = 88980
    // Production = 90782

    /*------------- Activity-------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkForCrashes();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        if (savedInstanceState == null) {
            loadMainFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForUpdates();
    }

    private void checkForCrashes() {
        if (!BuildConfig.DEBUG) {
            CrashManager.register(this, HOCKEYAPP_API_KEY);
        }
    }

    private void checkForUpdates() {
        // TODO: Remove this for Google Play Store builds!
        if (!BuildConfig.DEBUG) {
            UpdateManager.register(this, HOCKEYAPP_API_KEY);
        }
    }

    /*------------- View.OnClickListener -------------*/

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_interstitial_activity_example) {
            loadInterstitialSpotXAdViewActivity();
        } else if (v.getId() == R.id.button_interstitial_view_example) {
            loadInterstitialSpotXAdView();
        } else if (v.getId() == R.id.button_programmatic_example) {
            loadProgrammaticFragment();
        } else if (v.getId() == R.id.button_xml_example) {
            loadXmlFragment();
        }
    }

    @Override
    public void onBackPressed() {
        WebView cookies = (WebView)findViewById(R.id.webview_cookies);
        if (cookies != null && cookies.getVisibility() == View.VISIBLE) {
            findViewById(R.id.webview_cookies).setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    /*------------------ Begin Custom Logic ------------------*/

    private void loadMainFragment() {
        MainFragment fragment = new MainFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main_container, fragment);
        fragmentTransaction.commit();
    }

    private void loadInterstitialSpotXAdViewActivity() {
        InterstitialActivityFragment fragment = new InterstitialActivityFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void loadInterstitialSpotXAdView() {
        InterstitialViewFragment fragment = new InterstitialViewFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void loadProgrammaticFragment() {
        ProgrammaticFragment fragment = new ProgrammaticFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void loadXmlFragment() {
        XmlFragment fragment = new XmlFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}