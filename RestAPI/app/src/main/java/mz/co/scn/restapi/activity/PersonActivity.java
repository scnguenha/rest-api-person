package mz.co.scn.restapi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mz.co.scn.restapi.R;
import mz.co.scn.restapi.adapter.PersonListAdapter;
import mz.co.scn.restapi.model.Person;
import mz.co.scn.restapi.sync.SendPersonAsync;

public class PersonActivity extends AppCompatActivity {
    EditText edtName;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        edtName = findViewById(R.id.edt_name);

        findViewById(R.id.btn_gravar).setOnClickListener(v -> {
            if (!edtName.getText().toString().isEmpty()) {
                person = new Person(edtName.getText().toString());
                //JSONObject jsonObject = new JSONObject();
                new SendPersonAsync(this, person,"post").execute();
                finish();
            } else {
                edtName.setError("Campo Obrigatório");
            }
        });

        findViewById(R.id.btn_actualizar).setOnClickListener(v -> {

            if (!edtName.getText().toString().isEmpty()) {
                person.setName(edtName.getText().toString());
                //JSONObject jsonObject = new JSONObject();
                new SendPersonAsync(this, person, "put").execute();
                finish();
            } else {
                edtName.setError("Campo Obrigatório");
            }
        });

        if (PersonListAdapter.selectedPerson != null) {
            person = PersonListAdapter.selectedPerson;
            edtName.setText(person.getName());
            findViewById(R.id.btn_gravar).setVisibility(View.GONE);
            findViewById(R.id.btn_actualizar).setVisibility(View.VISIBLE);
            PersonListAdapter.selectedPerson = null;
        }
    }
}