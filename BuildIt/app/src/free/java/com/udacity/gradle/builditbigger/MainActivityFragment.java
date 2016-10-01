package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;


/**
 * Main fragment with UI
 */
public class MainActivityFragment extends Fragment {

    private final static String LOG_TAG = MainActivityFragment.class.getSimpleName();

    // add interstitial add
    PublisherInterstitialAd mPublisherInterstitialAd;
    ProgressBar progressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
                // if you need to get your device ID uncomment next line
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("611F2A146CD0E2D8437BE2B61D07C6D8")
                .build();
        mAdView.loadAd(adRequest);

        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Button button = (Button) root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPublisherInterstitialAd.isLoaded()) {
                    mPublisherInterstitialAd.show();
                } else {
                    tellJoke(getActivity());
                }
            }
        });

        mPublisherInterstitialAd = new PublisherInterstitialAd(getActivity());

        // ad unit should changed to the real one in the production-ready app
        mPublisherInterstitialAd.setAdUnitId("/6499/example/interstitial");

        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                tellJoke(getActivity());
            }
        });

        requestNewInterstitial();

        return root;
    }

    /**
     * Method to get a joke from cloud endpoint through asynctask
     * @param activity from which the call is being made
     */
    public void tellJoke(Activity activity) {
        progressBar.setVisibility(View.VISIBLE);
        FetchJokesEndpoint endpointsAsyncTask = new FetchJokesEndpoint();
        endpointsAsyncTask.execute(activity);
    }

    /**
     * Download new fullscreen add
     */
    private void requestNewInterstitial() {
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                // change device id!
                .addTestDevice("611F2A146CD0E2D8437BE2B61D07C6D8")
                .build();

        mPublisherInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Hide the progressbar on move to another activity/fragment
        progressBar.setVisibility(View.GONE);
    }
}
