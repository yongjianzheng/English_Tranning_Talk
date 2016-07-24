package com.yongjian.english_tranning_talk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yongjian.english_tranning_talk.bean.ApplicationData;
import com.yongjian.english_tranning_talk.bean.User;
import com.yongjian.english_tranning_talk.fragment.OrderFragment;
import com.yongjian.english_tranning_talk.fragment.UserInfoFragment;
import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.fragment.FindForeignerFragment;
import com.yongjian.english_tranning_talk.fragment.WalletFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by YONGJIAN on 2016/4/24 0024.
 */
@ContentView(R.layout.ldrawer_activity)
public class LDrawerActivity extends AppCompatActivity{
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawerLayout;
    @ViewInject(R.id.navigation_view)
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    User user = ApplicationData.getInstance().getmUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        setupDrawerContent(navigationView);
        switchToFind();

    }
    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){

                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.find:
                                switchToFind();
                                break;
                            case R.id.user_info:
                                switchToUserInfo();
                                break;
                            case R.id.order:
                                switchToOrder();
                                break;
                            case R.id.my_wallet:
                                switchToWallet();
                                break;
                            case R.id.nav_share:
                                break;
                            case R.id.nav_person:
                                break;
                            case R.id.exit:
                                break;
                        }
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        return false;
                    }
                }
        );
    }
    private void switchToUserInfo(){
        getSupportFragmentManager().beginTransaction().replace(R.id.cotainer,new UserInfoFragment()).commit();
        toolbar.setTitle("个人信息");
    }
    private void switchToOrder(){
        getSupportFragmentManager().beginTransaction().replace(R.id.cotainer,new OrderFragment()).commit();
        toolbar.setTitle("我的订单");
    }
    private void switchToWallet(){
        getSupportFragmentManager().beginTransaction().replace(R.id.cotainer,new WalletFragment()).commit();
        toolbar.setTitle("我的钱包");
    }
    private void switchToFind(){
        getSupportFragmentManager().beginTransaction().replace(R.id.cotainer,new FindForeignerFragment()).commit();
        toolbar.setTitle("找口语老师");
    }
}
