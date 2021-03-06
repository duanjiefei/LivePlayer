package com.duanjiefei.github.liveplayer.indiactor;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;

/**
 *
 */

public abstract class ViewPagerIndicatorAdapter {

    public abstract int getCount();
    public abstract IPagerTitle getTitle(Context context, int index);
    public abstract IPagerIndicatorView getIndicator(Context conext);

    public float getTitleWeight(){
        return 1;
    }

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public final void registerDataSetObservable(DataSetObserver Observable) {
        mDataSetObservable.registerObserver(Observable);
    }

    public final void unregisterDataSetObservable(DataSetObserver Observable) {
        mDataSetObservable.unregisterObserver(Observable);
    }

    public final void notifySetDataChanged() {
        mDataSetObservable.notifyChanged();
    }
}
