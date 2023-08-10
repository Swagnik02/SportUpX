package com.team.fantasy.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.fantasy.APICallingPackage.Config;
import com.team.fantasy.Bean.BeanHomeMatches;
import com.team.fantasy.R;
import com.team.fantasy.activity.ContestListActivity;
import com.team.fantasy.activity.MyJoinedFixtureContestListActivity;
import com.team.fantasy.activity.MyJoinedLiveContestListActivity;
import com.team.fantasy.activity.MyJoinedResultContestListActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdapterMyMatches extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<BeanHomeMatches> mListenerList;

    public AdapterMyMatches(List<BeanHomeMatches> mListenerList, Context context) {
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
        View view = layoutInflater.inflate(R.layout.adapter_my_match_list, null);
        CountDownTimer countDownTimer = null;
        final ImageView im_Team1 = view.findViewById(R.id.im_Team1);
        final TextView tv_TeamOneName = view.findViewById(R.id.tv_TeamOneName);
        final TextView tv_TeamsName = view.findViewById(R.id.tv_TeamsName);
        final TextView tv_TimeRemained = view.findViewById(R.id.tv_TimeRemained);
        final ImageView im_Team2 = view.findViewById(R.id.im_Team2);
        final TextView tv_TeamTwoName = view.findViewById(R.id.tv_TeamTwoName);
        final TextView tv_JoinedContestCount = view.findViewById(R.id.tv_JoinedContestCount);
        final TextView tv_MatchTime = view.findViewById(R.id.tv_MatchTime);
        final ImageView megaphone = view.findViewById(R.id.megaphone);
        final TextView tvHomeLineUpOut = view.findViewById(R.id.tvHomeLineUpOut);
        final RelativeLayout RLMyMatchListItem = view.findViewById(R.id.RLMyMatchListItem);


        final String match_id = mListenerList.get(position).getMatch_id();
        String teamid1 = mListenerList.get(position).getTeamid1();
        final String match_status = mListenerList.get(position).getMatch_status();
        String type = mListenerList.get(position).getType();
        final int time = mListenerList.get(position).getTime();
        String teamid2 = mListenerList.get(position).getTeamid2();
        String team_name1 = mListenerList.get(position).getTeam_name1();
        final String team_image1 = mListenerList.get(position).getTeam_image1();
        final String team_short_name1 = mListenerList.get(position).getTeam_short_name1();
        String team_name2 = mListenerList.get(position).getTeam_name2();
        final String team_image2 = mListenerList.get(position).getTeam_image2();
        final String team_short_name2 = mListenerList.get(position).getTeam_short_name2();
        String contest_count = mListenerList.get(position).getContest_count();
        final String eleven_out = mListenerList.get(position).getEleven_out();
        final String match_date_time = mListenerList.get(position).getMatch_date_time();



        tv_JoinedContestCount.setText("Joined with " + contest_count + " Team");
        tv_TeamOneName.setText(team_short_name1);
        tv_TeamTwoName.setText(team_short_name2);
        tv_TeamsName.setText(type);

        Glide.with(context).load(Config.TEAMFLAGIMAGE + team_image1)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(im_Team1);
        Glide.with(context).load(Config.TEAMFLAGIMAGE + team_image2)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(im_Team2);

        if (match_status.equals("Fixture")) {

            if (eleven_out.equals("1")) {
                megaphone.setVisibility(View.VISIBLE);
                tvHomeLineUpOut.setVisibility(View.VISIBLE);
                tvHomeLineUpOut.setText("• Lineup Out");
            } else {
                megaphone.setVisibility(View.GONE);
                tvHomeLineUpOut.setVisibility(View.GONE);
            }

            tv_TimeRemained.setCompoundDrawablesWithIntrinsicBounds(R.drawable.watch_icon, 0, 0, 0);
            tv_TimeRemained.setText(time + "");
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            try {
                int FlashCount = time;
                long FlashMiliSec = FlashCount * 1000;

                countDownTimer = new CountDownTimer(FlashMiliSec, 1000) {

                    public void onTick(long millisUntilFinished) {

                        long Days = TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                        long Hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished));
                        long Minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                        long Seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                        String format = "%1$02d";
                        tv_TimeRemained.setText(String.format(format, Days) + ":" + String.format(format, Hours) + ":" + String.format(format, Minutes) + ":" + String.format(format, Seconds));
                    }
                    public void onFinish() {
                        tv_TimeRemained.setText("Entry Over!");
                    }

                }.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else
        if (match_status.equals("Live")){
            tv_TimeRemained.setText("Live");
        }else
        if (match_status.equals("Result")){
            tv_TimeRemained.setText("Completed");
            tv_MatchTime.setVisibility(View.VISIBLE);
            tv_MatchTime.setText(match_date_time);
        }

        RLMyMatchListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (match_status.equals("Fixture")) {
                    Intent k = new Intent(context, MyJoinedFixtureContestListActivity.class);
                    k.putExtra("MatchId", match_id);
                    k.putExtra("Time", time + "");
                    ContestListActivity.IntentTime = String.valueOf(time);
                    k.putExtra("TeamsName", tv_TeamsName.getText().toString());
                    ContestListActivity.IntenTeamsName = tv_TeamsName.getText().toString();
                    k.putExtra("TeamsOneName", team_short_name1);
                    ContestListActivity.IntentTeamOneName = team_short_name1;
                    k.putExtra("TeamsTwoName", team_short_name2);
                    ContestListActivity.IntentTeamTwoName = team_short_name2;
                    k.putExtra("T1Image", team_image1);
                    k.putExtra("T2Image", team_image2);
                    context.startActivity(k);
                }else if (match_status.equals("Live")){

                    Intent k = new Intent(context, MyJoinedLiveContestListActivity.class);
                    k.putExtra("MatchId",match_id);
                    k.putExtra("Time",time+"");
                    k.putExtra("TeamsName", tv_TeamsName.getText().toString());
                    k.putExtra("TeamsOneName", team_short_name1);
                    k.putExtra("TeamsTwoName", team_short_name2);
                    k.putExtra("T1Image", team_image1);
                    k.putExtra("T2Image", team_image2);
                    context.startActivity(k);
                }else {
                    Intent k = new Intent(context, MyJoinedResultContestListActivity.class);
                    k.putExtra("MatchId",match_id);
                    k.putExtra("Time",time+"");
                    k.putExtra("TeamsName", tv_TeamsName.getText().toString());
                    k.putExtra("TeamsOneName", team_short_name1);
                    k.putExtra("TeamsTwoName", team_short_name2);
                    k.putExtra("T1Image", team_image1);
                    k.putExtra("T2Image", team_image2);
                    context.startActivity(k);
                }
            }
        });


        ViewPager vp = (ViewPager) container;
            vp.addView(view, 0);
            return view;

        }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}