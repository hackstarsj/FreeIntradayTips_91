package com.silverlinesoftwares.intratips.util;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.silverlinesoftwares.intratips.R;

import java.util.HashMap;

public class BarCustomMarkerView extends MarkerView {
    private TextView tvContent;
    HashMap<String,String> hashMap;

    public BarCustomMarkerView(Context context,HashMap<String,String > hashMap, int layoutResource) {
        super(context,layoutResource);
        // this markerview only displays a textview
        this.hashMap=hashMap;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public MPPointF getOffset() {
        return null;
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
       String dd=String.valueOf(Double.valueOf(e.getY()).longValue());
        String ee=dd;
        tvContent.setText("" + hashMap.get(ee));

        super.refreshContent(e, highlight);
    }
    private MPPointF mOffset;

}
