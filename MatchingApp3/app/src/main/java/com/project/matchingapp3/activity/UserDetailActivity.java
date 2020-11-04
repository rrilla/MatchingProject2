package com.project.matchingapp3.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.matchingapp3.MainActivity;
import com.project.matchingapp3.R;
import com.project.matchingapp3.TeamActivity;
import com.project.matchingapp3.UserActivity;
import com.project.matchingapp3.model.Team;
import com.project.matchingapp3.model.User;
import com.project.matchingapp3.model.dto.NavDataDto;
import com.project.matchingapp3.task.RestAPITask;

import java.util.concurrent.ExecutionException;

public class UserDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    DrawerLayout drawer;

    private NavDataDto navDataDto;
    private String jwtToken;
    private int selectUserId;
    private String selectUserTeam;
    private String selectUserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();
        selectUserId = intent.getIntExtra("selectUserId",0);
        selectUserTeam = intent.getStringExtra("selectUserTeam");
        selectUserRole = intent.getStringExtra("selectUserRole");

        jwtToken = intent.getStringExtra("jwtToken");
        navDataDto = (NavDataDto)intent.getSerializableExtra("navDataDto");

        String[] result = new String[1];
        RestAPITask task = new RestAPITask();

        try {
            result = task.execute("app/userDetail/", Integer.toString(selectUserId)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("test-유저받기",result[0]);
        Gson gson = new Gson();
        User user = gson.fromJson(result[0], new TypeToken<User>(){}.getType());
        Log.e("test-유저", user.toString());

        //툴바
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //user데이터 view출력
        TextView tvName = findViewById(R.id.uDetail_tv_nickname);
        TextView tvLocation = findViewById(R.id.uDetail_tv_location);
        TextView tvPosition = findViewById(R.id.uDetail_tv_position);
        TextView tvEmail = findViewById(R.id.uDetail_tv_email);
        TextView tvPhone = findViewById(R.id.uDetail_tv_phone);
        ImageView ivImage = findViewById(R.id.uDetail_iv_image);

        tvName.setText(user.getNickname());
        tvLocation.setText(user.getLocation());
        tvPosition.setText(user.getPosition());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhone());
        Glide.with(this).load(user.getUrlImage()).into(ivImage);

        //드로어 레이아웃
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


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
                        Intent intent2 = new Intent(getApplicationContext(), TeamActivity.class);
                        intent2.putExtra("jwtToken", jwtToken);
                        intent2.putExtra("navDataDto", navDataDto);
                        startActivity(intent2);
                        return true;
                    case R.id.tab3:
                        Intent intent3 = new Intent(getApplicationContext(), UserActivity.class);
                        intent3.putExtra("jwtToken", jwtToken);
                        intent3.putExtra("navDataDto", navDataDto);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });

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
        if(navDataDto.getImage() != null) {
            ImageView navImage = header.findViewById(R.id.navHeader_iv_image);
            Glide.with(this).load(navDataDto.getUrlImage()).into(navImage);
        }
        //텍스트
        TextView navName = header.findViewById(R.id.navHeader_tv_username);
        TextView navTName = header.findViewById(R.id.navHeader_tv_tName);
        navName.setText(navDataDto.getUsername()+"("+ navDataDto.getNickname()+")");
        if(navDataDto.getT_name() != null){
            navTName.setText(navDataDto.getT_name());
        }

        Button btnScout = findViewById(R.id.uDetail_btn_scout);
        btnScout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //팀장아니면 못하게해야함.
                if(navDataDto.getT_name() == null){
                    Snackbar.make(view, "스카웃 신청 실패.  팀을 먼저 생성 해주세요.", Snackbar.LENGTH_SHORT).show();
                    return ;
                } else if(selectUserRole != null){
                    Snackbar.make(view, "스카웃 신청 실패.  구단주만 신청할 수 있습니다.", Snackbar.LENGTH_SHORT).show();
                    return ;
                } else if(selectUserTeam != null){
                    Snackbar.make(view, "스카웃 신청 실패.  해당 유저는 가입된 팀이 있음.", Snackbar.LENGTH_SHORT).show();
                    return ;
                } else {
                    String[] result = new String[1];
                    RestAPITask task = new RestAPITask(jwtToken);

                    try {
                        result = task.execute("user/apply2/", Integer.toString(selectUserId)).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (result[0].equals("ok")) {
                        Snackbar.make(view, "스카웃 신청 완료.  유저가 수락하면 가입됩니다.", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(view, "스카웃 신청 실패.  " + result[0], Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
                intent.putExtra("navDataDto", navDataDto);
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
        toolbar.setTitle("팀 상세보기");
        return true;
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
            Intent intent = new Intent(getApplicationContext(), TeamCreateActivity.class);
            intent.putExtra("jwtToken", jwtToken);
            intent.putExtra("navDataDto", navDataDto);
            startActivity(intent);
        } else if (id == R.id.nav_menu3) {
            Toast.makeText(this, "네비-메뉴3 선택", Toast.LENGTH_LONG).show();
        }
        drawer.closeDrawer(GravityCompat.START);
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