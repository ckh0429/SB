package kh.com.sb.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kh.com.sb.module.MyOkHttpClient;
import kh.com.sb.R;
import kh.com.sb.view.UserListRecycleViewAdapter;
import kh.com.sb.module.GithubUserData;
import kh.com.sb.view.DetectScrollToEnd;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class LaunchActivity extends AppCompatActivity {

    private static final String GITHUB_URL = "https://api.github.com/users?";
    private static final String SINCE = "since";
    private static final String PER_PAGE = "per_page";
    private static final int LIST_ITEM_MAX = 100;
    private static int SINCE_START = 1;
    private static final String PAGE_NUM = "20";

    private TextView displayUsersView;
    private UserListRecycleViewAdapter userListRecycleViewAdapter;
    private ProgressBar progressBar;
    private List<GithubUserData> githubUserDataArrayList = new ArrayList<>();
    public static Handler UIHandler;

    private final static ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIHandler = new Handler(Looper.getMainLooper());
        initUI();
        getGithubUser();
    }

    private void initUI() {
        getSupportActionBar().setTitle(getString(R.string.github_users));
        getSupportActionBar().setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.darker_gray)));
        setContentView(R.layout.activity_launch);
        progressBar = findViewById(R.id.progressBar_cyclic);
        displayUsersView = findViewById(R.id.display_users_view);
        initRecycleView();
    }

    private void initRecycleView() {
        RecyclerView userListRecycleView = findViewById(R.id.recyclerView_index);
        userListRecycleView.addItemDecoration(new DividerItemDecoration(this, 0));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        userListRecycleView.setLayoutManager(layoutManager);
        userListRecycleViewAdapter = new UserListRecycleViewAdapter(this, githubUserDataArrayList);
        userListRecycleView.setAdapter(userListRecycleViewAdapter);
        userListRecycleView.addOnScrollListener(new DetectScrollToEnd(layoutManager, 1) {
            @Override
            protected void onLoadMore() {
                if (userListRecycleViewAdapter.getItemCount() >= LIST_ITEM_MAX) {
                    new AlertDialog.Builder(LaunchActivity.this)
                            .setMessage("list item exceed max number")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    getGithubUser();
                }
            }
        });
    }

    public void getGithubUser() {
        service.submit(new Runnable() {
            @Override
            public void run() {

                HttpUrl.Builder builder = HttpUrl.parse(GITHUB_URL).newBuilder();
                builder.addQueryParameter(SINCE, String.valueOf(SINCE_START));
                builder.addQueryParameter(PER_PAGE, PAGE_NUM);
                Request request = new Request.Builder()
                        .url(builder.toString())
                        .build();

                String resStr = null;
                try {
                    final Response response = MyOkHttpClient.getInstance().newCall(request).execute();
                    resStr = response.body().string();
                    Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(List.class, new GithubUserData.EmptyListDeserializer()).create();
                    GithubUserData[] githubUserData = gson.fromJson(resStr, GithubUserData[].class);

                    githubUserDataArrayList.addAll(Arrays.asList(githubUserData));
                    SINCE_START = Integer.valueOf(githubUserDataArrayList.get(githubUserDataArrayList.size() - 1).getId());
                    notifyRecycleViewAdapterChange();
                } catch (Exception e) {
                    showErrorMessageDialog(e, resStr);
                } finally {
                    closeProgressBar();
                }
            }
        });
    }

    private void showErrorMessageDialog(final Exception e, final String resStr) {
        runOnUI(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(LaunchActivity.this)
                        .setMessage(e.getMessage() + resStr)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    public void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    private void notifyRecycleViewAdapterChange() {
        runOnUI(new Runnable() {
            @Override
            public void run() {
                userListRecycleViewAdapter.notifyDataSetChanged();
            }
        });
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
