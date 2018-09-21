package kh.com.sb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

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
    public void onBindViewHolder(final UserListRecycleViewAdapter.ViewHolder holder, int position) {
        final GithubUserData githubUserData = this.githubUserDataList.get(position);

        GlideApp.with(context)
                .asBitmap()
                .load(Uri.parse(githubUserData.getAvatar_url()))
                .into(new BitmapImageViewTarget(holder.imageId) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.imageId.setImageDrawable(circularBitmapDrawable);
                    }
                });

        holder.loginText.setText(String.valueOf(githubUserData.getLogin()));
        if(Boolean.valueOf(githubUserData.getSiteAdmin())) {
            holder.sideAdmin.setVisibility(View.VISIBLE);
            holder.sideAdmin.setText(R.string.staff);
        } else{
            holder.sideAdmin.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserInfoDetailActivity.class);
                intent.putExtra("login", githubUserData.getLogin());
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
        TextView loginText, sideAdmin;

        ViewHolder(View itemView) {
            super(itemView);
            imageId = itemView.findViewById(R.id.imageId);
            loginText = itemView.findViewById(R.id.login_text);
            sideAdmin = itemView.findViewById(R.id.side_admin);
        }
    }
}
