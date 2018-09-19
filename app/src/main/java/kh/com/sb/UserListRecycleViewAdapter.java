package kh.com.sb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class UserListRecycleViewAdapter extends RecyclerView.Adapter<UserListRecycleViewAdapter.ViewHolder> {
    private Context context;
    private List<GithubUserData> githubUserDataList;

    public UserListRecycleViewAdapter(Context context, List<GithubUserData> memberList) {
        this.context = context;
        this.githubUserDataList = memberList;
    }

    @Override
    public UserListRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserListRecycleViewAdapter.ViewHolder holder, int position) {
        final GithubUserData githubUserData = this.githubUserDataList.get(position);

        GlideApp.with(context)
                .load(Uri.parse(githubUserData.getAvatar_url()))
                .into(holder.imageId);

        holder.textId.setText(String.valueOf(githubUserData.getId()));
        holder.textName.setText(githubUserData.getLogin());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserInfoDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return githubUserDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageId;
        TextView textId, textName;

        ViewHolder(View itemView) {
            super(itemView);
            imageId = itemView.findViewById(R.id.imageId);
            textId = itemView.findViewById(R.id.textId);
            textName = itemView.findViewById(R.id.textName);
        }
    }
}
