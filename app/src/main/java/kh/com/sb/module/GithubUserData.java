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

public class GithubUserData {
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
