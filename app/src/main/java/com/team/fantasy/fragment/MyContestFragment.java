package com.team.fantasy.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.team.fantasy.R;
import com.team.fantasy.databinding.FragmentMyContestBinding;

import java.util.ArrayList;
import java.util.List;

public class MyContestFragment extends Fragment {



    FragmentMyContestBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyContestBinding.inflate(inflater, container, false);

        setupViewPager(binding.myviewpager);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.myviewpager, new FragmentMyFixtures());
        transaction.commit();


        return binding.getRoot();
    }

    private void setupViewPager(ViewPager viewPager) {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentMyFixtures(), "FIXTURES");
        adapter.addFragment(new FragmentMyLive(), "LIVE");
        adapter.addFragment(new FragmentMyResults(), "RESULTS");
        viewPager.setAdapter(adapter);

        binding.FragmentMyTab.setupWithViewPager(binding.myviewpager);

        for (int i = 0; i < binding.FragmentMyTab.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            binding.FragmentMyTab.getTabAt(i).setCustomView(tv);
        }
        binding.myviewpager.setOffscreenPageLimit(2);

    }


    class MyViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MyViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
