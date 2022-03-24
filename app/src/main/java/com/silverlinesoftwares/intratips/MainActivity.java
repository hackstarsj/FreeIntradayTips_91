package com.silverlinesoftwares.intratips;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.silverlinesoftwares.intratips.activity.BulkDealActivity;
import com.silverlinesoftwares.intratips.adapters.StockUpperAdapter;
import com.silverlinesoftwares.intratips.adapters.ViewPagerAdapter;
import com.silverlinesoftwares.intratips.fragments.HomeTabFragment;
import com.silverlinesoftwares.intratips.fragments.IndicesFragment;
import com.silverlinesoftwares.intratips.fragments.HeatMapFragment;
import com.silverlinesoftwares.intratips.fragments.MoreFragment;
import com.silverlinesoftwares.intratips.fragments.MoverLooserFragment;
import com.silverlinesoftwares.intratips.fragments.NewsFragment;
import com.silverlinesoftwares.intratips.fragments.AboutFragment;
import com.silverlinesoftwares.intratips.fragments.ScreenerFragment;
import com.silverlinesoftwares.intratips.fragments.SearchFragment;
import com.silverlinesoftwares.intratips.fragments.TrendingFragment;
import com.silverlinesoftwares.intratips.listeners.DetailsResponseListener;
import com.silverlinesoftwares.intratips.listeners.GainerLooserListener;
import com.silverlinesoftwares.intratips.models.ResponseModel;
import com.silverlinesoftwares.intratips.models.StockUpperModel;
import com.silverlinesoftwares.intratips.models.UserModel;
import com.silverlinesoftwares.intratips.tasks.GainerLooserTask;
import com.silverlinesoftwares.intratips.tasks.auth.FetchProfileTask;
import com.silverlinesoftwares.intratips.util.BgService;
import com.silverlinesoftwares.intratips.util.BuyButtonClick;
import com.silverlinesoftwares.intratips.util.CustomRecycleView;
import com.silverlinesoftwares.intratips.util.StaticMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements OnUserEarnedRewardListener,BottomNavigationView.OnNavigationItemSelectedListener, GainerLooserListener, DetailsResponseListener {


    ViewPager2 upper_page;
    ViewPager2 bottom_page;
    BottomNavigationView bottom_menu;
    BottomNavigationView upper_menu;
    MenuItem prevMenuItem;
    MenuItem prevMenuItem2;
    private CustomRecycleView ticker;
    private StockUpperAdapter gainerLooserAdapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 101;


    private void showDiscalamer() {
        editor.putString("first","first");
        editor.apply();
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.dis_dialog,null);
        Button button=view.findViewById(R.id.clode);
        alertDialog.setView(view);
        final AlertDialog alertDialog1=alertDialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });

    }

    void crash(){
     //   throw new RuntimeException("This is a crash");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        registerReceiver(MainActivity.this.bcNewMessageDownload, new IntentFilter("bcNewMessageDownload"));
         try {
            startService(new Intent(MainActivity.this, BgService.class));
        }catch (java.lang.IllegalStateException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        sharedPreferences=getApplicationContext().getSharedPreferences("MyPref",0);
        editor=sharedPreferences.edit();


        if(StaticMethods.getUserDetails(MainActivity.this)!=null){
            String is_pro=StaticMethods.getUserDetails(MainActivity.this).getIs_pro();
            if(!is_pro.equalsIgnoreCase("1")){
                if(!sharedPreferences.contains("first")) {
                    showDiscalamer();
                }
            }
            if(StaticMethods.getLoginToken(MainActivity.this)!=null){
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(!task.isSuccessful()){
                            return;
                        }
                        String tok=task.getResult();
                        FetchProfileTask fetchProfileTask=new FetchProfileTask(MainActivity.this);
                        fetchProfileTask.execute(StaticMethods.getLoginToken(MainActivity.this),tok);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FetchProfileTask fetchProfileTask=new FetchProfileTask(MainActivity.this);
                        fetchProfileTask.execute(StaticMethods.getLoginToken(MainActivity.this),"");
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        FetchProfileTask fetchProfileTask=new FetchProfileTask(MainActivity.this);
                        fetchProfileTask.execute(StaticMethods.getLoginToken(MainActivity.this),"");
                    }
                });
            }
            else{
                StaticMethods.removeToken(MainActivity.this);
                StaticMethods.removeUser(MainActivity.this);
            }
        }
        else{
            if(!sharedPreferences.contains("first")) {
                showDiscalamer();
            }

        }
        upper_page=findViewById(R.id.top_page);
        bottom_page=findViewById(R.id.bottom_page);

        bottom_menu=findViewById(R.id.bottom_menu);
        upper_menu=findViewById(R.id.top_menu);
        ticker=findViewById(R.id.mywidget);


      //  tv.setSelected(true);  // Set focus to the textview


        bottom_menu.setOnNavigationItemSelectedListener(MainActivity.this);
        upper_menu.setOnNavigationItemSelectedListener(MainActivity.this);

        ViewPagerAdapter upper_adapter=new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        upper_adapter.addFragment(new HeatMapFragment());
        upper_adapter.addFragment(new MoverLooserFragment());
        upper_adapter.addFragment(new ScreenerFragment());
        upper_adapter.addFragment(new TrendingFragment());
        upper_adapter.addFragment(new AboutFragment());
        upper_page.setAdapter(upper_adapter);

        ViewPagerAdapter bottom_adapter=new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        bottom_adapter.addFragment(new HomeTabFragment());
        bottom_adapter.addFragment(new SearchFragment());
        bottom_adapter.addFragment(new NewsFragment());
        bottom_adapter.addFragment(new IndicesFragment());
        bottom_adapter.addFragment(new MoreFragment());
        bottom_page.setAdapter(bottom_adapter);
        bottom_page.setOffscreenPageLimit(5);
        upper_page.setOffscreenPageLimit(5);


        bottom_page.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottom_menu.getMenu().getItem(0).setChecked(false);
                }
                bottom_menu.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottom_menu.getMenu().getItem(position);
                StaticMethods.showInterestialAds(MainActivity.this);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        upper_page.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem2 != null) {
                    prevMenuItem2.setChecked(false);
                }
                else
                {
                    upper_menu.getMenu().getItem(0).setChecked(false);
                }
                upper_menu.getMenu().getItem(position).setChecked(true);
                prevMenuItem2 = upper_menu.getMenu().getItem(position);
                StaticMethods.showInterestialAds(MainActivity.this);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            upper_page.setNestedScrollingEnabled(true);
            bottom_page.setNestedScrollingEnabled(true);
        }





        GainerLooserTask gainerLooserTask=new GainerLooserTask(MainActivity.this);
       gainerLooserTask.execute(new String[]{"NIFTY%20500"});
        //StaticMethods.executeAsyncTask(gainerLooserTask,new String[]{"NIFTY%20500"});
        FirebaseApp.initializeApp(MainActivity.this);
        if(StaticMethods.getNotification(MainActivity.this).equalsIgnoreCase("1")){
            FirebaseMessaging.getInstance().subscribeToTopic("allDevices");
        }
       // crash();

        try {
            UserModel userModel = StaticMethods.getUserDetails(MainActivity.this);
            if (userModel != null) {
                if(userModel.getIs_super()!=null) {
                    if (userModel.getIs_super().equalsIgnoreCase("1")) {
                        FirebaseMessaging.getInstance().subscribeToTopic("pro_plus_topic");
                    } else {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("pro_plus_topic");
                    }
                }
                else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("pro_plus_topic");
                }
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("pro_plus_topic");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        checkForUpdate();

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            if(menuItem.getItemId()==R.id.home_menu) {
                showFragment(0, false);
                //StaticMethods.showRewardAds(MainActivity.this,MainActivity.this);
                return true;
            }
            if(menuItem.getItemId()==R.id.search_menu) {
                showFragment(1, false);
                return true;
            }
            if(menuItem.getItemId()==R.id.news_menu) {
                showFragment(2, false);
                return true;
            }
            if(menuItem.getItemId()==R.id.global_menu) {
                showFragment(3, false);
                return true;
            }
            if(menuItem.getItemId()==R.id.more_menu) {
                showFragment(4, false);
                return true;
            }
            if(menuItem.getItemId()==R.id.intra_menu) {
                showFragment(0, true);
                return true;
            }
            if(menuItem.getItemId()==R.id.trending) {
                showFragment(3, true);
                return true;
            }
            if(menuItem.getItemId()==R.id.option2_menu) {
                showFragment(4, true);
                return true;
            }
            if(menuItem.getItemId()==R.id.mover_looser) {
                showFragment(1, true);
                return true;
            }
            if(menuItem.getItemId()==R.id.forum_menu) {
                showFragment(2, true);
                return true;
            }


        return false;
    }

    private void showFragment(int position,boolean is_top){
        if(is_top){
            upper_page.setCurrentItem(position);
            upper_page.setVisibility(View.VISIBLE);
            bottom_page.setVisibility(View.GONE);
        }
        else {
            bottom_page.setCurrentItem(position);
            bottom_page.setVisibility(View.VISIBLE);
            upper_page.setVisibility(View.GONE);
        }

        if(click<=4) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StaticMethods.showRewardAds(MainActivity.this, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                Log.d("Reward","Earned");
                            }
                        });
                    }
                });
            }, 5000);
            click=click+1;
        }
    }

    public int click=0;


    @Override
    public void onSucess(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            String strings = jsonObject.getString("data");
            TypeToken<List<StockUpperModel>> token = new TypeToken<List<StockUpperModel>>() {
            };
            Gson gson = new Gson();
            List<StockUpperModel> animals = gson.fromJson(strings, token.getType());
            if(animals!=null) {
                Collections.sort(animals, new Comparator<StockUpperModel>() {
                    public int compare(StockUpperModel s1, StockUpperModel s2) {
                        // notice the cast to (Integer) to invoke compareTo
                        return (s1.getSymbol()).compareTo(s2.getSymbol());
                    }
                });
                gainerLooserAdapter = new StockUpperAdapter(ticker, MainActivity.this, animals);
                //    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                //  linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this) {
                    @Override
                    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(MainActivity.this) {
                            private static final float SPEED = 4000f;// Change this value (default=25f)

                            @Override
                            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                                return SPEED / displayMetrics.densityDpi;
                            }
                        };
                        smoothScroller.setTargetPosition(position);
                        startSmoothScroll(smoothScroller);
                    }

                };
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                ticker.setLayoutManager(layoutManager);
                ticker.setAdapter(gainerLooserAdapter);



                ticker.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int lastItem = layoutManager.findLastCompletelyVisibleItemPosition();
                        if(lastItem == layoutManager.getItemCount()-1){
                            mHandler.removeCallbacks(SCROLLING_RUNNABLE);
                            Handler postHandler = new Handler(Looper.getMainLooper());
                            postHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ticker.setAdapter(null);
                                    ticker.setAdapter(gainerLooserAdapter);
                                    mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);
                                }
                            }, 2000);
                        }
                    }
                });
                mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);
            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }


    final int duration = 100;
    final int pixelsToMove = 30;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Runnable SCROLLING_RUNNABLE = new Runnable() {

        @Override
        public void run() {
            ticker.smoothScrollBy(pixelsToMove, 0);
            mHandler.postDelayed(this, duration);
        }
    };

    public void autoScroll(){
        final int speedScroll = 0;
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(gainerLooserAdapter!=null) {
                    if (count == gainerLooserAdapter.getItemCount())
                        count = 0;
                    if (count < gainerLooserAdapter.getItemCount()) {
                        ticker.smoothScrollToPosition(++count);
                        handler.postDelayed(this, speedScroll);
                    }
                }
            }
        };
        handler.postDelayed(runnable,speedScroll);
    }

    @Override
    public void onProfile(ResponseModel data) {

        if(data!=null){
            if(data.getStatus_code().equalsIgnoreCase("200")){
                StaticMethods.saveUserDetails(MainActivity.this,data.getUser());
            }
            else{
                StaticMethods.removeUser(MainActivity.this);
                StaticMethods.removeToken(MainActivity.this);
                ShowDialog("Session Expired! Login Again.");

            }
        }
        else{
            StaticMethods.removeUser(MainActivity.this);
            StaticMethods.removeToken(MainActivity.this);
            ShowDialog("Session Expired! Login Again.");

        }
    }

    @Override
    public void onFailedProfile(String msg) {
        StaticMethods.removeUser(MainActivity.this);
        StaticMethods.removeToken(MainActivity.this);
        ShowDialog("Failed to Fetch your Profile! Login Again.");
    }

    @Override
    public void onFailed(String msg) {

        ticker.setVisibility(View.GONE);
    }

    public void ShowDialog(String msg){
      //  if(!isFinishing()) {
        try {
            AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
            al.setMessage(msg);
            al.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            al.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //}
    }

    //@Override
    public void onBuyButtonClick() {
        showFragment(4,true);
    }


    private void checkForUpdate() {
        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){

                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE, MainActivity.this, RC_APP_UPDATE);


                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                popupSnackbarForCompleteUpdate();
            } else {
                Log.e("Update", "checkForAppUpdateAvailability: something else");
            }
        });
    }

    InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED){
                        popupSnackbarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED){
                        if (mAppUpdateManager != null){
                            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                        }

                    } else {
                        Log.i("Update", "InstallStateUpdatedListener: state: " + state.installStatus());
                    }
                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e("Update", "onActivityResult: app download failed");
            }
        }
    }

    private void popupSnackbarForCompleteUpdate() {
        Toast.makeText(MainActivity.this, "New Update is Ready to Install", Toast.LENGTH_SHORT).show();
    }

    BroadcastReceiver bcNewMessageDownload = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null) {
                onBuyButtonClick();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bcNewMessageDownload);
    }

    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

    }
}
