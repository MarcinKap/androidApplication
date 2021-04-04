package com.example.projekt_android.menuViews;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WrapContentLinearLayoutManager extends LinearLayoutManager
{
    private static final String mCode = "WrapContentLinearLayoutManager";

    /**
     * Constructor with parameter
     *
     * @param context
     */
    public WrapContentLinearLayoutManager(Context context)
    {
        super(context);
    }

    /**
     * Overwritten method for capturing an exception being invoked
     *
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state)
    {
        try
        {
            super.onLayoutChildren(recycler, state);
        }
        catch (IndexOutOfBoundsException e)
        {
            Log.e("TAG", "błąd z recyclerem!!!");
        }
    }
}
