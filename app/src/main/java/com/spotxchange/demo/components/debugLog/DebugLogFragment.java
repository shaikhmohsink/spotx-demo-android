package com.spotxchange.demo.components.debugLog;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.spotxchange.demo.R;
import com.spotxchange.sdk.android.components.log.SpotxLog;

import org.json.JSONException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;


/**
 * Copyright (C) 2015 SpotXchange
 */
public class DebugLogFragment extends Fragment {
    public static final String LOGTAG = DebugLogFragment.class.getSimpleName();

    private ListView _listView;
    private DebugLogViewAdapter _adapter;

    public DebugLogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SpotxLog.openLog(getActivity());
        Set<String> log = SpotxLog.getDebugLog();
        _adapter = new DebugLogViewAdapter(getActivity(), R.layout.row_debuglog);
        if (!log.isEmpty()) {
            _adapter.addAll(new ArrayList<>(log));
        }
        _listView = (ListView)inflater.inflate(R.layout.fragment_log, container, false);
        _listView.setAdapter(_adapter);
        return _listView;
    }

    private class DebugLogViewAdapter extends ArrayAdapter<String> {

        public DebugLogViewAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View rowView, ViewGroup parent) {
            String logEntry = getItem(position);
            int color;
            String text;

            try {
                SpotxLog.SpotxLogEntry jsonLogEntry = new SpotxLog.SpotxLogEntry(logEntry);
                switch (jsonLogEntry.getSeverity()) {
                    case Log.ERROR:
                        color = R.color.log_error;
                        break;
                    case Log.WARN:
                        color = R.color.log_warn;
                        break;
                    case Log.DEBUG:
                        color = R.color.log_debug;
                        break;
                    default:
                        color = R.color.log_info;
                        break;

                }
                Date timestamp = new Date(jsonLogEntry.getTimestamp());
                text = String.format(
                    "[%s] %s: %s",
                    DateFormat.getTimeInstance().format(timestamp),
                    jsonLogEntry.getTag(),
                    jsonLogEntry.getMessage()
                );
            }
            catch(JSONException e) {
                SpotxLog.clearDebugLog();
                throw new RuntimeException("SpotxLog contained an invalid entry.", e);
            }

            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.row_debuglog, parent, false);
            }
            ((TextView)rowView).setTextColor(color);
            ((TextView)rowView).setText(text);

            return rowView;
        }
    }

}