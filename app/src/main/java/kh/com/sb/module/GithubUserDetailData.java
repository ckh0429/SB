package kh.com.sb.module;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GithubUserDetailData {
    private String login = null;
    private String id = null;
    private String node_id = null;
    private String avatar_url = null;
    private String gravatar_id = null;
    private String url = null;
    private String html_url = null;
    private String followers_url = null;
    private String following_url = null;
    private String gists_url = null;
    private String starred_url = null;
    private String subscriptions_url = null;
    private String organizations_url = null;
    private String repos_url = null;
    private String events_url = null;
    private String received_events_url = null;
    private String type = null;
    private String site_admin = null;
    private String name = null;
    private String company = null;
    private String blog = null;
    private String location = null;
    private String email = null;
    private String hireable = null;
    private String bio = null;
    private String public_repos = null;
    private String public_gists = null;
    private String followers = null;
    private String following = null;
    private String created_at = null;
    private String updated_at = null;

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    public String getNodeID() {
        return node_id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public String getFollowing_url() {
        return followers_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public String getReceived_events_url() {
        return events_url;
    }

    public String getType() {
        return type;
    }

    public String getSiteAdmin() {
        return site_admin;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getBlog() {
        return blog;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getHireable() {
        return hireable;
    }

    public String getBio() {
        return bio;
    }

    public static class EmptyListDeserializer implements JsonDeserializer<List<?>> {
        @Override
        public List<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            if (!json.isJsonArray()) return Collections.emptyList(); // Avoid Exception;
            JsonArray array = json.getAsJsonArray();
            Type itemType = ((ParameterizedType) type).getActualTypeArguments()[0];
            List list = new ArrayList<>();
            for (int i = 0; i < array.size(); i++)
                list.add(context.deserialize(array.get(i), itemType));
            return list;
        }
    }
}
