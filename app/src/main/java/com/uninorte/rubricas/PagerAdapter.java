package com.uninorte.rubricas;


import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.uninorte.rubricas.fragments.EstudiantesDentroAsignaturas;
import com.uninorte.rubricas.fragments.EvaluacionesDentroAsignaturas;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private final Bundle fragmentBundle;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, Bundle data) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        fragmentBundle = data;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                EstudiantesDentroAsignaturas tab1 = new EstudiantesDentroAsignaturas();
                tab1.setArguments(this.fragmentBundle);
                return tab1;
            case 1:
                EvaluacionesDentroAsignaturas tab2 = new EvaluacionesDentroAsignaturas();
                tab2.setArguments(this.fragmentBundle);
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}