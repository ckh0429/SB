package kh.com.sb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void isReceiveResponseCorrect(){
        String userData = "[{\"login\":\"grempe\",\"id\":117,\"node_id\":\"MDQ6VXNlcjExNw==\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/117?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/grempe\",\"html_url\":\"https://github.com/grempe\",\"followers_url\":\"https://api.github.com/users/grempe/followers\",\"following_url\":\"https://api.github.com/users/grempe/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/grempe/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/grempe/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/grempe/subscriptions\",\"organizations_url\":\"https://api.github.com/users/grempe/orgs\",\"repos_url\":\"https://api.github.com/users/grempe/repos\",\"events_url\":\"https://api.github.com/users/grempe/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/grempe/received_events\",\"type\":\"User\",\"site_admin\":false}]";
        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(List.class, new GithubUserData.EmptyListDeserializer()).create();
        GithubUserData[] githubUserData = gson.fromJson(userData, GithubUserData[].class);
        assertTrue(githubUserData[0].getLogin().equalsIgnoreCase("grempe"));
    }
}