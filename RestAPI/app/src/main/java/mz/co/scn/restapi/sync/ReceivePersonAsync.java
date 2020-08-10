package mz.co.scn.restapi.sync;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.util.EntityUtils;
import mz.co.scn.restapi.activity.MainActivity;
import mz.co.scn.restapi.model.Person;
import mz.co.scn.restapi.service.PersonService;
import mz.co.scn.restapi.utils.ObjectMapperProvider;

/**
 * Created by Sidónio Goenha on 08/08/2020.
 */
public class ReceivePersonAsync extends AsyncTask<String, Integer, String> {
    protected ObjectMapper objectMapper = new ObjectMapperProvider().getContext();
    protected Context context;
    List<Person> persons;

    public ReceivePersonAsync(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            personsSync();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "Finished";
    }

    private void personsSync() throws IOException, JSONException {
        // The HttpResponse
        HttpResponse response = PersonService.get("persons/", "application/json");

        final int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONArray jsonArray = new JSONArray(result);

            String personsJson = jsonArray.toString();

            persons = Arrays.asList(objectMapper.readValue(personsJson, Person[].class));

            Log.e("Sync", personsJson);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (persons != null)
            MainActivity.update(persons);
    }
}
