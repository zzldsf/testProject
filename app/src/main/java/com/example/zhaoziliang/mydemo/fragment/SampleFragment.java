package com.example.zhaoziliang.mydemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhaoziliang.mydemo.R;

/**
 * 每个Tab中的fragment
 *
 * @author
 */
public class SampleFragment extends Fragment {
    TextView title;
    View v;
    private static final String ARG_TEXT = "text";

    public static SampleFragment newInstance(String text) {
        SampleFragment f = new SampleFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        f.setArguments(args);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		return super.onCreateView(inflater, container, savedInstanceState);
        return initView();
    }

    public View initView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        v = inflater.inflate(R.layout.md_activity_main, null, false);
        title = (TextView) v.findViewById(R.id.tv);
        title.setText(getArguments().getString(ARG_TEXT));


        return v;
    }




}





