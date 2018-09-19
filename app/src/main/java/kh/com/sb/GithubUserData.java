package kh.com.sb;

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

public class GithubUserData {
    private String login = "login";
    private String id = "id";
    private String node_id = "node_id";
    private String avatar_url = "avatar_url";
    private String gravatar_id = "gravatar_id";
    private String url = "url";
    private String html_url = "html_url";
    private String followers_url = "followers_url";
    private String following_url = "following_url";
    private String gists_url = "gists_url";
    private String starred_url = "starred_url";
    private String subscriptions_url = "subscriptions_url";
    private String organizations_url = "organizations_url";
    private String repos_url = "repos_url";
    private String events_url = "events_url";
    private String received_events_url = "received_events_url";
    private String type = "type";
    private String site_admin = "site_admin";

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("[");
            sb.append(login + "=").append(getLogin());
            sb.append(",").append(id + "=").append(getId());
            sb.append(",").append(node_id + "=").append(getNodeID());
            sb.append(",").append(avatar_url + "=").append(getAvatar_url());
            sb.append(",").append(gravatar_id + "=").append(getGravatar_id());
            sb.append(",").append(url + "=").append(getUrl());
            sb.append(",").append(html_url + "=").append(getHtml_url());
            sb.append(",").append(followers_url + "=").append(getFollowers_url());
            sb.append(",").append(following_url + "=").append(getFollowing_url());
            sb.append(",").append(gists_url + "=").append(getGists_url());
            sb.append(",").append(starred_url + "=").append(getStarred_url());
            sb.append(",").append(subscriptions_url + "=").append(getSubscriptions_url());
            sb.append(",").append(organizations_url + "=").append(getOrganizations_url());
            sb.append(",").append(repos_url + "=").append(getRepos_url());
            sb.append(",").append(events_url + "=").append(getEvents_url());
            sb.append(",").append(received_events_url + "=").append(getReceived_events_url());
            sb.append("]");
        } catch (Exception e) {
        }
        return sb.toString();
    }
}
