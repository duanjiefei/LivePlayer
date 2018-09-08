package com.duanjiefei.github.liveplayer.fragment;



import android.support.v4.app.Fragment;

import java.util.HashMap;

public class FragmentManagerWrapper {

    private static volatile FragmentManagerWrapper mInstance = null;

    /**
     * DCL Double check Lock 单例
     * @return
     */
    public static FragmentManagerWrapper getInstance(){
        if (mInstance==null){
            synchronized (FragmentManagerWrapper.class){
                if (mInstance==null){
                    mInstance = new FragmentManagerWrapper();
                }
            }
        }
        return mInstance;
    }

    private HashMap<String,Fragment> hashMapFragments = new HashMap<>();

    public Fragment crateFragment(Class<?> clazz){ return createFragment(clazz,true);}

    public Fragment createFragment(Class<?> clazz, boolean isObtain){
        Fragment fragment = null;
        String className = clazz.getName();
        if (hashMapFragments.containsKey(className)){
            fragment = hashMapFragments.get(className);
        }else{
            try {
                fragment = (Fragment) Class.forName(className).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (isObtain){
            hashMapFragments.put(className,fragment);
        }
        return fragment;

    }
}
