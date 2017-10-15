package com.example.user.tourassistant.page_fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.example.user.tourassistant.R;
import com.example.user.tourassistant.firebase.Events;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {


    private View view;

    private RecyclerView eventListView;
    private FirebaseRecyclerAdapter<Events,EventViewHolder> firebaseRecyclerAdapter;
    private DatabaseReference eventDatabase,userDatabase;
    FirebaseDatabase database;
    DatabaseReference myEventRef;
    public EventFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();


        view=inflater.inflate(R.layout.fragment_event, container, false);

        eventDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);

        eventListView = (RecyclerView)view.findViewById(R.id.recyclerview_event);
        eventListView.setHasFixedSize(true);
        eventListView.setLayoutManager(llm);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm3 = getActivity().getSupportFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                AddEventFragment addEventFragment = new AddEventFragment();
                ft3.replace(R.id.homeFragmentView,addEventFragment);
                ft3.addToBackStack(null);
                ft3.commit();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Events, EventViewHolder>(
                Events.class, R.layout.event_row, EventViewHolder.class, eventDatabase
        ) {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, Events model, int position) {

                viewHolder.setEventName(model.getDestination());
                viewHolder.setFromDate(model.getFromDate());
                viewHolder.setToDate(model.getToDate());
                viewHolder.setBudget(model.getBudget());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm4 = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft4 = fm4.beginTransaction();
                        ExpenseListFragment expenseFragment = new ExpenseListFragment();
                        ft4.replace(R.id.homeFragmentView,expenseFragment);
                        ft4.addToBackStack(null);
                        ft4.commit();
                    }
                });
            }
        };
        eventListView.setAdapter(firebaseRecyclerAdapter);



    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public EventViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setEventName(String value){
            TextView eventName=mView.findViewById(R.id.eventNameRow);
            eventName.setText(value);

        }

        public void setFromDate(String value){
            TextView fromDate=mView.findViewById(R.id.fromDateRow);
            fromDate.setText(value);
        }

        public void setToDate(String value){
            TextView toDate=mView.findViewById(R.id.toDateRow);
            toDate.setText(value);
        }

        public void setBudget(String value){
            TextView budget=mView.findViewById(R.id.budgetRow);
            budget.setText(value);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_manu, menu);
    }
}
