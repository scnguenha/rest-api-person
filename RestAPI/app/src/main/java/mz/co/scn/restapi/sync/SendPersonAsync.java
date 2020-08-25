package mz.co.scn.restapi.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.entity.StringEntity;
import mz.co.scn.restapi.model.Person;
import mz.co.scn.restapi.service.PersonService;
import mz.co.scn.restapi.utils.ObjectMapperProvider;

/**
 * Created by Sidónio Goenha on 08/08/2020.
 */
public class SendPersonAsync extends AsyncTask<String, Integer, String> {
    protected ObjectMapper objectMapper = new ObjectMapperProvider().getContext();
    protected Context context;
    protected Person person;
    protected String operacao;
    List<Person> persons;

    public SendPersonAsync(Context context, Person person, String operacao) {
        this.context = context;
        this.person = person;
        this.operacao = operacao;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            sendPerson();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "Finished";
    }

    public void sendPerson() throws IOException, JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", person.getName());
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        HttpResponse response;
        switch (operacao){
            case "post":
                 response = PersonService.post("persons/", entity, "application/json");
                break;
            case "put":
                 response = PersonService.put("persons/" + person.getId(), entity, "application/json");
                break;
            case "delete":
                 response = PersonService.delete("persons/" + person.getId(), "application/json");
                 break;
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        new ReceivePersonAsync(context).execute();
    }
}
