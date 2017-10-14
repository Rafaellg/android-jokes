package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private InterstitialAd mInterstitialAd;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize the mobile adds
        MobileAds.initialize(getContext(), getString(R.string.ad_app_id));

        // Set's up the banner ad
        AdView mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        // Get the progressbar from layout
        final ProgressBar pbLoading = root.findViewById(R.id.pb_loading);

        // Set's up the interstitial ad
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Reload the ad
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

                // Execute the async task to get jokes
                new JokesAsyncTask(getContext(), pbLoading).execute();

                Log.i("Ads", "onAdClosed");
            }
        });

        Button btTellJoke = root.findViewById(R.id.bt_tell_joke);
        btTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterstitialAd.show();
            }
        });

        return root;
    }
}
