package com.project.matchingapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.project.matchingapp3.activity.LoginActivity;
import com.project.matchingapp3.activity.MyPageActivity;
import com.project.matchingapp3.adapter.ViewPagerAdapter;
import com.project.matchingapp3.fragment.Fragment1;
import com.project.matchingapp3.fragment.Fragment2;
import com.project.matchingapp3.fragment.Fragment3;
import com.project.matchingapp3.model.dto.MainDataDto;
import com.project.matchingapp3.task.ImageTask;
import com.project.matchingapp3.task.RestAPITask;

import java.net.URI;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    ViewPager pager;
    Toolbar toolbar;
    DrawerLayout drawer;

    MainDataDto mainDataDto;
    String jwtToken;
    Bitmap bitImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        jwtToken = intent.getStringExtra("jwtToken");

        String[] result = new String[1];
        RestAPITask task = new RestAPITask(jwtToken);

        try {
            result = task.execute("user/mainData").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("test-데이터받음",result[0]);
        Gson gson = new Gson();
        mainDataDto = gson.fromJson(result[0],MainDataDto.class);


        //툴바
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //드로어 레이아웃
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //네비게이션 뷰
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //네비뷰의 로그아웃 버튼
        View header = navigationView.getHeaderView(0);
        Button btnLogout = header.findViewById(R.id.navHeader_btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("autoLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("id");
                editor.remove("pw");
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        //네비뷰 헤더의 사용자 정보
        //이미지
        if(mainDataDto.getImage() != null) {
            ImageView navImage = header.findViewById(R.id.navHeader_iv_image);
            ImageTask imgTask = new ImageTask();
            try {
                bitImg = imgTask.execute("0df08699-9cde-450b-96b1-09409f9e2c67.jpg").get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            navImage.setImageBitmap(bitImg);
        }
        //텍스트
        TextView navName = header.findViewById(R.id.navHeader_tv_username);
        TextView navTName = header.findViewById(R.id.navHeader_tv_tName);
        navName.setText(mainDataDto.getUsername()+"("+mainDataDto.getNickname()+")");
        if(mainDataDto.getT_name() != null){
            navTName.setText(mainDataDto.getT_name());
        }


        //뷰 페이저
        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3); //미리 로딩해 놓을 아이템의 갯수, 스크롤 넘길시 빠른 조회가능

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        final Fragment1 fragment1 = new Fragment1();
        adapter.addItem(fragment1);

        final Fragment2 fragment2 = new Fragment2();
        adapter.addItem(fragment2);

        final Fragment3 fragment3 = new Fragment3();
        adapter.addItem(fragment3);

        pager.setAdapter(adapter);


        //상단 탭 네비
        TabLayout tabs = findViewById(R.id.tab_layout);
        tabs.addTab(tabs.newTab().setText("내 경기"));
        tabs.addTab(tabs.newTab().setText("내 점수"));
        tabs.addTab(tabs.newTab().setText("내 팀"));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                String text = "";
                String title = "";
                if(position == 0){
                    text = "상단탭 1 선택";
                    title = "내 경기";
                }else if(position == 1){
                    text = "상단탭 2 선택";
                    title = "내 점수";
                }else if(position == 2){
                    text = "상단탭 3 선택";
                    title = "내 팀";
                }
                Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT).show();
                pager.setCurrentItem(position,true);   // true = 페이지 전환시 스무스
                toolbar.setTitle(title);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        //하단 탭 네비
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab1:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("jwtToken", jwtToken);
                        startActivity(intent);
                        return true;
                    case R.id.tab2:
                        //팀 액티비티
                        return true;
                    case R.id.tab3:
                        //선수 액티비티
                        return true;
                }
                return false;
            }
        });
    }

    //네비게이션 메뉴의 아이템 선택시 - 인텐트 액티비티 이동, 페이지 이동 구현
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_menu1) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("jwtToken", jwtToken);
            startActivity(intent);
        } else if (id == R.id.nav_menu2) {
            Toast.makeText(this, "네비-메뉴2 선택", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_menu3) {
            Toast.makeText(this, "네비-메뉴3 선택", Toast.LENGTH_LONG).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //앱바 메뉴의 아이템 선택시 -
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.appbar_search:
                Toast.makeText(this, "앱바-메뉴1 검색 선택", Toast.LENGTH_SHORT).show();
                break;
            case R.id.appbar_info:
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                intent.putExtra("jwtToken", jwtToken);
                intent.putExtra("mainDataDto", mainDataDto);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //앱바 메뉴 인플레이션
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        toolbar.setTitle("홈");
        return true;
    }

    //뒤로가기 때 호출 - 네비창 닫기
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}