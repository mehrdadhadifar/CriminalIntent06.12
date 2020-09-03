package com.example.criminalintent.controller.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.criminalintent.R;
import com.example.criminalintent.controller.fragment.CrimeDetailFragment;
import com.example.criminalintent.controller.fragment.CrimeListFragment;
import com.example.criminalintent.model.Crime;

public class CrimeListActivity extends SingleFragmentActivity
        implements CrimeListFragment.CallBacks, CrimeDetailFragment.Callbacks {


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CrimeListActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return CrimeListFragment.newInstance();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_master_detail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getUUID());
            startActivity(intent);
        } else {
            CrimeDetailFragment crimeDetailFragment = CrimeDetailFragment.newInstance(crime.getUUID());

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_fragment_container, crimeDetailFragment)
                    .commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment crimeListFragment = (CrimeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        crimeListFragment.updateUI();
    }

    @Override
    public void onCrimeDeleted(Crime crime) {
        CrimeDetailFragment crimeDetailFragment = (CrimeDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_fragment_container);
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .remove(crimeDetailFragment)
                .commit();

        CrimeListFragment crimeListFragment = (CrimeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        crimeListFragment.updateUI();
    }
}