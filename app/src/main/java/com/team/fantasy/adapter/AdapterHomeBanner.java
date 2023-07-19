package com.team.fantasy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.fantasy.APICallingPackage.Config;
import com.team.fantasy.Bean.BeanBanner;
import com.team.fantasy.R;

import java.util.List;

public class AdapterHomeBanner extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<BeanBanner> mListenerList;

    public AdapterHomeBanner(List<BeanBanner> mListenerList, Context context) {
        this.context = context;
        this.mListenerList = mListenerList;
    }

    @Override
    public int getCount() {
        return mListenerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_banner, null);

        ImageView imageView = view.findViewById(R.id.im_banner);

        String Imagename = mListenerList.get(position).getBanner_image();
        final String Type = mListenerList.get(position).getType();

        Glide.with(context).load(Config.BANNERIMAGE+Imagename)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
