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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.tourassistant.R;
import com.example.user.tourassistant.firebase.Events;
import com.example.user.tourassistant.firebase.Expense;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseListFragment extends Fragment {


    private View view;

    private RecyclerView eventListView;
    private FirebaseRecyclerAdapter<Expense,EventViewHolder1> firebaseRecyclerAdapter;
    private DatabaseReference eventDatabase,userDatabase;
    FirebaseDatabase database;
    DatabaseReference myEventRef;
    public ExpenseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        database = FirebaseDatabase.getInstance();


        view=inflater.inflate(R.layout.fragment_expense_list, container, false);

        eventDatabase = FirebaseDatabase.getInstance().getReference().child("TExpense");
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);

        eventListView = (RecyclerView)view.findViewById(R.id.recyclerview_expenseList);
        eventListView.setHasFixedSize(true);
        eventListView.setLayoutManager(llm);

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Expense, EventViewHolder1>(
                Expense.class, R.layout.expense_row, EventViewHolder1.class, eventDatabase
        ) {
            @Override
            protected void populateViewHolder(EventViewHolder1 viewHolder, Expense model, int position) {

                viewHolder.setEventName(model.getEDetails());
                viewHolder.setFromDate(model.getExpense());
                viewHolder.setToDate(model.getDate());

            }
        };
        eventListView.setAdapter(firebaseRecyclerAdapter);



    }

    public static class EventViewHolder1 extends RecyclerView.ViewHolder{

        View mView;
        public EventViewHolder1(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setEventName(String value){
            TextView eventName=mView.findViewById(R.id.ExpenseDetailsTV);
            eventName.setText(value);

        }

        public void setFromDate(String value){
            TextView fromDate=mView.findViewById(R.id.ExpenseTV);
            fromDate.setText(value);
        }

        public void setToDate(String value){
            TextView toDate=mView.findViewById(R.id.ExpenseDateTv);
            toDate.setText(value);
        }

//        public void setBudget(String value){
//            TextView budget=mView.findViewById(R.id.budgetRow);
//            budget.setText(value);
//        }
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addEvent:
                FragmentManager fm5 = getActivity().getSupportFragmentManager();
                FragmentTransaction ft5 = fm5.beginTransaction();
                AddExpenseFragment addExpenseFragment = new AddExpenseFragment();
                ft5.replace(R.id.homeFragmentView,addExpenseFragment);
                ft5.addToBackStack(null);
                ft5.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
