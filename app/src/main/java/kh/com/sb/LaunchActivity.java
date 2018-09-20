package kh.com.sb;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private RecyclerView userListRecycleView;
    private static UserListRecycleViewAdapter userListRecycleViewAdapter;
    private static List<GithubUserData> githubUserDataArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;
    public static Handler UIHandler;

    private final static ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        UIHandler = new Handler(Looper.getMainLooper());
        initRecycleView();
        getGithubUser();
        getSupportActionBar().setTitle("Github");
    }

    private void initRecycleView() {
        userListRecycleView = findViewById(R.id.recyclerView_index);
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
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    if (progressDialog == null) {
                        progressDialog = new ProgressDialog(LaunchActivity.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    }
                    if (!progressDialog.isShowing()) {
                        progressDialog.show();
                    }
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

                try {
                    final Response response = MyOkHttpClient.getInstance().newCall(request).execute();
                    final String resStr = response.body().string();
                    Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(List.class, new GithubUserData.EmptyListDeserializer()).create();
                    GithubUserData[] githubUserData = gson.fromJson(resStr, GithubUserData[].class);

                    githubUserDataArrayList.addAll(Arrays.asList(githubUserData));
                    SINCE_START = Integer.valueOf(githubUserDataArrayList.get(githubUserDataArrayList.size() - 1).getId());
                    notifyRecycleViewAdapterChange();
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

    private void notifyRecycleViewAdapterChange() {
        runOnUI(new Runnable() {
            @Override
            public void run() {
                userListRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
