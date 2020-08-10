package mz.co.scn.restapi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mz.co.scn.restapi.R;
import mz.co.scn.restapi.activity.PersonActivity;
import mz.co.scn.restapi.model.Person;
import mz.co.scn.restapi.sync.SendPersonAsync;

/**
 * Created by Sidónio Goenha on 06/08/2020.
 */
public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder> {

    private List<Person> persons;
    public static Person selectedPerson;
    public Context context;

    /**
     * @param persons
     */
    public PersonListAdapter(List<Person> persons) {
        this.persons = persons;
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public PersonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_list_item, parent, false);

        return new PersonListAdapter.ViewHolder(v);
    }

    /**
     * @param holder
     * @param pos
     */
    @Override
    public void onBindViewHolder(@NonNull PersonListAdapter.ViewHolder holder, final int pos) {
        if (persons != null && !persons.isEmpty()) {
            Person person = persons.get(pos);
            if (person != null) {
                holder.tv_id.setText(String.valueOf(person.getId()));
                holder.tv_name.setText(person.getName() != null ? person.getName() : "");
            }

            holder.pos = pos;

            holder.optionMenu.setOnClickListener(view -> {
                        //creating a popup menu
                        PopupMenu popup = new PopupMenu(context, holder.optionMenu);
                        //inflating menu from xml resource
                        popup.inflate(R.menu.options_menu);
                        //make item, partilhar visible
                        popup.setOnMenuItemClickListener(item -> {
                                    switch (item.getItemId()) {
                                        case R.id.menuEditar:
                                            selectedPerson = persons.get(pos);

                                            context.startActivity(new Intent(context, PersonActivity.class));
                                            break;

                                        case R.id.menuEliminar:

                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            builder.setTitle("Aviso");
                                            builder.setMessage("Tem Certeza que deseja eliminar?");
                                            builder.setCancelable(false);

                                            builder.setPositiveButton("Sim", (dialog, which) -> {
                                                selectedPerson = persons.get(pos);
                                                new SendPersonAsync(context, person,"delete").execute();
                                                selectedPerson = null;
                                            });

                                            builder.setNegativeButton("Não", (dialog, which) -> {

                                            });

                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                            break;
                                    }

                                    return false;
                                }

                        );

                        //displaying the popup
                        popup.show();

                        // force icons in popup menu
                        //CommonFunctions.getInstance().

                                //forceIconMenuShow(popup);
                    }

            );
        }
    }

    /**
     * @return int
     */
    @Override
    public int getItemCount() {
        return persons.size();
    }

    /**
     * @param newPersons
     */
    public void setFilter(List<Person> newPersons) {
        persons = new ArrayList<>();
        persons.addAll(newPersons);
        notifyDataSetChanged();
    }

    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public int pos;

        private TextView tv_name, tv_id, optionMenu;

        public ViewHolder(@NonNull View v) {
            super(v);

            tv_name = v.findViewById(R.id.tv_name);
            tv_id = v.findViewById(R.id.tv_id);
            optionMenu = v.findViewById(R.id.optionMenuPerson);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //selectedPerson = persons.get(pos);

            //Intent i = new Intent(view.getContext(), PersonActivity.class);
            //view.getContext().startActivity(i);
        }
    }
}
