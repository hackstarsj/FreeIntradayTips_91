package com.silverlinesoftwares.intratips.fragments.subfragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.activity.ForCastActivity;
import com.silverlinesoftwares.intratips.fragments.ResultFragment;
import com.silverlinesoftwares.intratips.util.Constant;

public class ForcastFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public ForcastFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_economy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=getArguments();
        int post= 0;
        if (bundle != null) {
            post = Integer.parseInt(bundle.getString(Constant.search));
        }
        WebView webView=view.findViewById(R.id.webview);
        webView.loadData(ForCastActivity.datas.get(post),"text/html","UTF-8");
        WebView.setWebContentsDebuggingEnabled(true);

    }
}
