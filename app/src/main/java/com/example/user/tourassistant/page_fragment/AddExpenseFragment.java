package com.example.user.tourassistant.page_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
        expense_SaveBT=view.findViewById(R.id.Expense_SaveBT);

        expense_SaveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                myExpenseRef = database.getReference("TExpense").push();
                myExpenseRef.child("EDetails").setValue(expense_DetailsET.getText().toString());
                myExpenseRef.child("Expense").setValue(expense_AmountET.getText().toString());
                myExpenseRef.child("Date").setValue(date);


            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
