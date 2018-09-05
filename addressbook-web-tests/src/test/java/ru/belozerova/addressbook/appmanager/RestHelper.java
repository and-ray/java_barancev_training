package ru.belozerova.addressbook.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.HashMap;

public class RestHelper {

    private ApplicationManager app;

    public RestHelper (ApplicationManager app){
        this.app = app;
    }

    private Executor getExecutor(){
        return Executor.newInstance()
                .auth("288f44776e7bec4bf44fdfeb1e646490","");
    }


    public String getBugStatus(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get("http://bugify.stqa.ru/api/issues/"+issueId+".json"))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        //System.out.println(parsed.toString());
        JsonElement var2 = parsed.getAsJsonObject().get("state_name");
        System.out.println("state = "+var2.toString());
       //System.out.println(var2.getAsJsonObject().get("state_name").toString());
       //System.out.println(var2.getAsJsonObject().get("state_name").toString());
        //HashMap<String, String> issueFields = new Gson().fromJson(issue, new TypeToken<HashMap<String, String>>(){}.getType());                                                                                                                                                        //1

        return "";
    }
}
