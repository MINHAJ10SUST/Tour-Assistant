package com.example.user.tourassistant.page_fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.tourassistant.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends Fragment {


    private View view;
    private EditText expense_DetailsET,expense_AmountET;
    private Button expense_SaveBT;
    FirebaseDatabase database;
    DatabaseReference myExpenseRef;
    public AddExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                database = FirebaseDatabase.getInstance();
        view=inflater.inflate(R.layout.fragment_add_expense, container, false);
        expense_DetailsET=view.findViewById(R.id.Expense_DetailsET);
        expense_AmountET=view.findViewById(R.id.Expense_AmountET);


        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_manu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveEvent:
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                myExpenseRef = database.getReference("TExpense").push();
                myExpenseRef.child("EDetails").setValue(expense_DetailsET.getText().toString());
                myExpenseRef.child("Expense").setValue(expense_AmountET.getText().toString());
                myExpenseRef.child("Date").setValue(date);

                FragmentManager fm4 = getActivity().getSupportFragmentManager();
                FragmentTransaction ft4 = fm4.beginTransaction();
                ExpenseListFragment expenseFragment = new ExpenseListFragment();
                ft4.replace(R.id.homeFragmentView,expenseFragment);
                ft4.addToBackStack(null);
                ft4.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
