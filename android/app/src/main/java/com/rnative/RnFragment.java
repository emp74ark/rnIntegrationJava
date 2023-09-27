package com.rnative;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.react.PackageList;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;

import java.util.List;

public abstract class RnFragment extends Fragment {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    protected abstract String getModuleName();

    @Override
    public void onDetach() {
        super.onDetach();
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mReactRootView = new ReactRootView(context);
//        mReactInstanceManager =
//                ((ReactApplication) getActivity().getApplication())
//                        .getReactNativeHost()
//                        .getReactInstanceManager();

        List<ReactPackage> packages = new PackageList(getActivity().getApplication()).getPackages();

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getActivity().getApplication())
                .setCurrentActivity(getActivity())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackages(packages)
                .addPackage(new RnPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.BEFORE_RESUME)
                .build();
    }

    @Nullable
    @Override
    public ReactRootView onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mReactRootView = new ReactRootView(requireActivity());

        mReactRootView.startReactApplication(
                mReactInstanceManager,
                getModuleName(),
                savedInstanceState
        );

        return mReactRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mReactRootView.unmountReactApplication();
    }
}

