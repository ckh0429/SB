package kh.com.sb;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class UserInfoDetailActivity extends AppCompatActivity {

    private final static ExecutorService service = Executors.newSingleThreadExecutor();
    private ProgressDialog progressDialog;
    public static Handler UIHandler;
    private static final String GITHUB_URL = "https://api.github.com/users/";
    private ImageView userImageView;
    private String loginID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail_view);
        loginID = getIntent().getExtras().getString("login");
        userImageView = findViewById(R.id.imageId);
        getSupportActionBar().setTitle("Github user detail");
        UIHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
            }
        };
        getUserDetailInfo();
    }

    public void getUserDetailInfo() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(UserInfoDetailActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
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
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
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
            }
        });
    }
}
