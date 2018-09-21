package kh.com.sb.controller;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kh.com.sb.R;
import kh.com.sb.module.GithubUserDetailData;
import kh.com.sb.module.GlideApp;
import kh.com.sb.module.MyOkHttpClient;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class UserInfoDetailActivity extends AppCompatActivity {

    private final static ExecutorService service = Executors.newSingleThreadExecutor();
    public static Handler UIHandler;
    private static final String GITHUB_URL = "https://api.github.com/users/";
    private ImageView userImageView;
    private TextView nameText;
    private TextView bioText;
    private TextView loginText;
    private TextView sideAdminText;
    private TextView locationText;
    private TextView blogText;
    private ProgressBar progressBar;
    private String loginID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginID = getIntent().getExtras().getString("login");
        UIHandler = new Handler(Looper.getMainLooper());
        initUI();
        getUserDetailInfo();
    }

    private void initUI() {
        getSupportActionBar().hide();
        setContentView(R.layout.user_detail_view);
        userImageView = findViewById(R.id.imageId);
        nameText = findViewById(R.id.name);
        bioText = findViewById(R.id.bio);
        loginText = findViewById(R.id.login_text);
        sideAdminText = findViewById(R.id.side_admin);
        locationText = findViewById(R.id.location);
        blogText = findViewById(R.id.blog);
        progressBar = findViewById(R.id.progressBar_cyclic);
    }

    public void getUserDetailInfo() {
        progressBar.setVisibility(View.VISIBLE);
        service.submit(new Runnable() {
            @Override
            public void run() {

                HttpUrl.Builder builder = HttpUrl.parse(GITHUB_URL + loginID).newBuilder();
                Request request = new Request.Builder()
                        .url(builder.toString())
                        .build();

                try {
                    GithubUserDetailData githubUserDetailData;
                    final Response response = MyOkHttpClient.getInstance().newCall(request).execute();
                    final String resStr = response.body().string();
                    Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(List.class, new GithubUserDetailData.EmptyListDeserializer()).create();
                    githubUserDetailData = gson.fromJson(resStr, GithubUserDetailData.class);
                    bindUserDetailDataToUI(githubUserDetailData);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeProgressBar();
                }
            }
        });
    }

    public void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    private void bindUserDetailDataToUI(final GithubUserDetailData githubUserDetailData) {
        runOnUI(new Runnable() {
            @Override
            public void run() {
                updateUI(githubUserDetailData);
            }
        });
    }

    private void updateUI(GithubUserDetailData githubUserDetailData) {
        GlideApp.with(UserInfoDetailActivity.this)
                .asBitmap()
                .load(githubUserDetailData.getAvatar_url())
                .circleCrop()
                .into(new BitmapImageViewTarget(userImageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        userImageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
        nameText.setText(githubUserDetailData.getName());
        bioText.setText(githubUserDetailData.getBio());
        loginText.setText(githubUserDetailData.getLogin());
        if (Boolean.valueOf(githubUserDetailData.getSiteAdmin())) {
            sideAdminText.setVisibility(View.VISIBLE);
            sideAdminText.setText("staff");
        }
        locationText.setText(githubUserDetailData.getLocation());
        blogText.setText(githubUserDetailData.getBlog());
    }

    private void closeProgressBar() {
        runOnUI(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
