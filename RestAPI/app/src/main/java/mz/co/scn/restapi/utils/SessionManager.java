package mz.co.scn.restapi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mz.co.scn.restapi.model.Person;

/**
 * Created by Simao on 10/20/2016.
 */
public class SessionManager {

    private static SessionManager mInstance;
    // User (make variable public to access from outside)
    public static final String KEY_USER = "user";
    public static final String PERSONS = "persons";
    // Sharedpref file name
    private static final String PREF_NAME = "rest";
    // Shared Preferences
    private static SharedPreferences pref;
    // Editor for Shared preferences
    private static SharedPreferences.Editor editor;
    // Context
    private static Context _context;
    //ObjectMapper to convert objects to json
    private static ObjectMapper objectMapper;

    /**
     * @return SessionManager
     */
    public static SessionManager getInstance() {
        if (mInstance == null)
            mInstance = new SessionManager();
        return mInstance;
    }

    /**
     * @param context - O contexto
     */
    public static void init(Context context) {
        _context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        editor = pref.edit();
        objectMapper = new ObjectMapperProvider().getContext();
    }

    public void createPersonsSession(List<Person> persons, String key) throws JsonProcessingException {
        editor.putString(key, objectMapper.writeValueAsString(persons));
        editor.commit();
    }

    public void createStringSession(String cod, String value) {
        editor.putString(cod, value);
        editor.commit();
    }

    public void createFloatSession(String cod, float value) {
        editor.putFloat(cod, value);
        editor.commit();
    }

    public String getTexto(String cod) {
        return pref.getString(cod, null);
    }

    public Float getFloatSession(String cod) {
        return pref.getFloat(cod, 0);
    }

    public List<Person> getPersons() throws IOException {
        //convert json data to user value object.
        String persons = pref.getString("persons", null);

        if (persons != null && !persons.equals("null")) {
            // return user
            List<Person> personList = Arrays.asList(objectMapper.readValue(persons, Person[].class));

            return personList;
        }

        return new ArrayList<>();
    }
}
