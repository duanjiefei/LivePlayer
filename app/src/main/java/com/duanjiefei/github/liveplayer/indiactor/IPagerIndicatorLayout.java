package com.duanjiefei.github.liveplayer.indiactor;

/**
 *
 */

public interface IPagerIndicatorLayout extends IPagerChangeListener{

    /**
     * 添加到CoolIndicatorLayout上
     */
    void onAttachCoolIndicatorLayout();

    /**
     * 从CoolIndicatorLayout下移除
     */
    void onDetachCoolIndicatorLayout();
}
