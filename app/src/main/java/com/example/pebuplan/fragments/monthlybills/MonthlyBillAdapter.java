package com.example.pebuplan.fragments.monthlybills;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pebuplan.R;
import com.example.pebuplan.model.MonthlyBillModel;

import java.util.ArrayList;

public class MonthlyBillAdapter extends RecyclerView.Adapter<MonthlyBillAdapter.ViewHolder> {

    ArrayList<MonthlyBillModel> monthlyBillList;
    MonthlyBillAdapter(ArrayList<MonthlyBillModel> monthlyBill){
        monthlyBillList = monthlyBill;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monthly_bills,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category.setText(monthlyBillList.get(position).getCategory());
        holder.amount.setText(monthlyBillList.get(position).getAmount());
        holder.pay.setText(monthlyBillList.get(position).getPay());
        holder.debt.setText(monthlyBillList.get(position).getDebt());
        holder.status.setText(monthlyBillList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return monthlyBillList.size();
    }

    public void updateList(ArrayList<MonthlyBillModel> newList){
        monthlyBillList.clear();
        monthlyBillList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView category, amount, pay, debt, status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            amount = itemView.findViewById(R.id.amount);
            debt = itemView.findViewById(R.id.debt);
            pay = itemView.findViewById(R.id.pay);
            status = itemView.findViewById(R.id.status);
        }
    }
}
