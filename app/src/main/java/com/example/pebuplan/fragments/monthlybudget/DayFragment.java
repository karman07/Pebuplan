package com.example.pebuplan.fragments.monthlybudget;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pebuplan.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DayFragment extends Fragment {

    EditText food_budget,transport_budget, housing_budget, utilities_budget, insurance_budget;
    EditText food_spent,transport_spent, housing_spent, utilities_spent, insurance_spent;
    EditText food_remains, transport_remains, housing_remains, utilities_remains, insurance_remains;
    EditText income_txt;
    TextView budget_txt, spent_txt, remains_txt, date;
    ArrayList<EditText> list1EditTexts = new ArrayList<>();

    ArrayList<EditText> list2EditTexts = new ArrayList<>();

    ArrayList<EditText> RemainsEditTexts = new ArrayList<>();

    ArrayList<String> budget_text = new ArrayList<>();
    ArrayList<String> spent_text = new ArrayList<>();

    //ImageView back_image;

    ImageView back, forward;

    boolean list1GreaterThanList2 = true;

    int total_budget = 0;
    int total_spent = 0;
    int total_remains = 0;

    Button submit_btn;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

    EditText[] budgetList, spentList, remainsList;
    public DayFragment() {

    }

    SharedPreferences sharedPref;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String month = dateFormat.format(calendar.getTime());
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        View view = inflater.inflate(R.layout.fragment_day, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        Date currentDate = new Date();

        food_budget = view.findViewById(R.id.food_budget);
        transport_budget = view.findViewById(R.id.transport_budget);
        housing_budget = view.findViewById(R.id.housing_budget);
        utilities_budget = view.findViewById(R.id.utilities_budget);
        insurance_budget = view.findViewById(R.id.insurance_budget);

        food_spent = view.findViewById(R.id.spent_food);
        transport_spent = view.findViewById(R.id.transport_spent);
        housing_spent = view.findViewById(R.id.housing_spent);
        utilities_spent = view.findViewById(R.id.utilities_spent);
        insurance_spent = view.findViewById(R.id.insurance_spent);

        food_remains = view.findViewById(R.id.remains);
        transport_remains = view.findViewById(R.id.transport_remains);
        housing_remains = view.findViewById(R.id.housing_remains);
        utilities_remains = view.findViewById(R.id.utilities_remains);
        insurance_remains = view.findViewById(R.id.insurance_remains);

        income_txt = view.findViewById(R.id.incomeinput);

        budget_txt = view.findViewById(R.id.Budget_Total);
        spent_txt = view.findViewById(R.id.budget_Total);
        remains_txt = view.findViewById(R.id.budget_remains);

        //back_image = view.findViewById(R.id.back_image);

        budgetList = new EditText[]{food_budget, transport_budget, housing_budget,utilities_budget,insurance_budget};
        spentList = new EditText[]{food_spent, transport_spent, housing_spent, utilities_spent, insurance_spent};
        remainsList = new EditText[]{food_remains,transport_remains,housing_remains,utilities_remains,insurance_remains};

        list1EditTexts.addAll(Arrays.asList(food_budget, transport_budget, housing_budget, utilities_budget, insurance_budget));
        list2EditTexts.addAll(Arrays.asList(food_spent, transport_spent, housing_spent, utilities_spent, insurance_spent));
        RemainsEditTexts.addAll(Arrays.asList(food_remains, transport_remains, housing_remains,utilities_remains,insurance_remains));
        submit_btn = view.findViewById(R.id.submit_btn);

        date = view.findViewById(R.id.timeline);

        back = view.findViewById(R.id.back_image_m);
        forward = view.findViewById(R.id.forward_image);

        date.setText(String.valueOf(day));

        getValue();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < list1EditTexts.size(); i++) {
                    EditText list1EditText = list1EditTexts.get(i);
                    EditText list2EditText = list2EditTexts.get(i);
                    EditText RemainsEditText = RemainsEditTexts.get(i);

                    String list1Text = list1EditText.getText().toString();
                    String list2Text = list2EditText.getText().toString();


                    if (list1Text.isEmpty() || list2Text.isEmpty() || Integer.parseInt(list1Text) <= Integer.parseInt(list2Text)) {
                        list1GreaterThanList2 = false;
                        break;
                    }

                    String remainsTxt = String.valueOf((Integer.parseInt(list1Text) - Integer.parseInt(list2Text)));

                    RemainsEditText.setText(remainsTxt);

                    total_budget += Integer.parseInt(list1Text);
                    total_spent += Integer.parseInt(list2Text);
                    total_remains += Integer.parseInt(remainsTxt);

                    budget_text.add(list1Text);
                    spent_text.add(list2Text);
                }

                if (list1GreaterThanList2 & !income_txt.getText().toString().isEmpty()) {
                    Log.d("1", "Saved");
                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    budget_txt.setText("₱" + total_budget);
                    spent_txt.setText("₱" + total_spent);
                    remains_txt.setText("₱" + total_remains);

//                    String budgetString = TextUtils.join(",",budget_text);
//                    String spentString = TextUtils.join(",",spent_text);
                    StringBuilder stringBuilder = new StringBuilder();
                    StringBuilder stringBuilder2 = new StringBuilder();

                    for (String item : budget_text) {
                        stringBuilder.append(item);
                        stringBuilder.append(", ");
                    }

                    for (String item : spent_text) {
                        stringBuilder2.append(item);
                        stringBuilder2.append(", ");
                    }


                    String result = stringBuilder.toString();
                    String result2 = stringBuilder2.toString();

                    SharedPreferences.Editor editor = preferences.edit();

                    Log.d("Value", result);

                    editor.putString(date.getText().toString() + "_budget", result);
                    editor.putString(date.getText().toString() + "_spent", result2);
                    editor.putString(date.getText().toString() + "_income", String.valueOf(income_txt.getText()));

                    editor.putString("Total_Budget", budget_txt.getText().toString());
                    editor.putString("Total_Spent", spent_txt.getText().toString());
                    editor.putString("Total_Remains",remains_txt.getText().toString());

                    editor.putString(date.getText().toString() + "_Total_Budget", budget_txt.getText().toString());
                    editor.putString(date.getText().toString() + "_Total_Spent", spent_txt.getText().toString());
                    editor.putString(date.getText().toString() + "_Total_Remains", remains_txt.getText().toString());

                    editor.apply();

                } else {
                    Toast.makeText(getActivity(), "Budget can't be lesser than spent", Toast.LENGTH_LONG).show();
                    Log.d("2", "Budget can't be lesser than spent");
                    Log.d("1", String.valueOf(list1GreaterThanList2));
                }


            }
        });

        /*TextView titleView = view.findViewById(R.id.title);
        titleView.setText("Monthly Budget");*/


/*        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });*/

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                date.setText(String.valueOf(day));
                income_txt.setText("");
                clear();
                getValue();
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                date.setText(String.valueOf(day));
                income_txt.setText("");
                clear();
                getValue();
            }
        });


        return view;
    }

    private void getValue() {
        sharedPref = requireActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
//        int month = calendar.get(Calendar.MONTH);
//        String[] monthNames = new DateFormatSymbols().getMonths();

        String value = sharedPref.getString(date.getText() + "_spent", ",,,,");
        String value2 = sharedPref.getString(date.getText() + "_budget", ",,,,");

        String value3 = sharedPref.getString(date.getText() + "_Total_Budget" , "Total");

        String value4 = sharedPref.getString(date.getText() + "_Total_Spent" , "Spent");

        String value5  = sharedPref.getString(date.getText() + "_Total_Remains" , "Remains");

        String value6 = sharedPref.getString(date.getText()+"_income", String.valueOf(income_txt.getText()));

        income_txt.setText(value6);



        budget_txt.setText(value3);
        spent_txt.setText(value4);
        remains_txt.setText(value5);


        String[] spentListText = value.split(",");
        String[] budgetListText = value2.split(",");




        for (int i = 0; i < spentList.length; i++) {
            TextView textView = spentList[i];
            if (!(budgetListText.length == 0)) {
                String number = spentListText[i].trim();
                textView.setText(number);
            }
        }

        for (int i = 0; i < budgetList.length; i++) {
            TextView textView = budgetList[i];
            if (!(budgetListText.length == 0)) {
                String number = budgetListText[i].trim();
                textView.setText(number);
            }
        }


        for (int i = 0; i < remainsList.length; i++) {
            TextView textView = remainsList[i];
            if (!(budgetListText.length == 0)) {
                int number1 = Integer.parseInt(budgetListText[i].trim());
                int number2 = Integer.parseInt(spentListText[i].trim());
                textView.setText(String.valueOf(number1 - number2));
            }
        }



    }

    public void clear(){

        for (EditText editText : budgetList) {
            editText.setText("");
        }

        for (EditText editText : spentList) {
            editText.setText("");
        }

        for (EditText editText : remainsList) {
            editText.setText("");
        }
    }
}