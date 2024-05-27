package com.example.grocerylistapplogin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;


    ///////////////////////////////////////////

    public void deleteItem(int position) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String userId = currentUser.getUid();
        String key = dataList.get(position).getKey();

        DatabaseReference userListsRef = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(userId).child("Lists").child(key);
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lists").child(key);
        userListsRef.removeValue();

        dataList.remove(position);

        notifyItemRemoved(position);

        Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
    }

    ///////////////////////////////////////

    public MyAdapter(Context context, List<DataClass> dataList) {

        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.recName.setText(dataList.get(position).getDataName());
        holder.recAmount.setText(dataList.get(position).getDataAmount());


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call deleteItem method to delete the item at the clicked position
                deleteItem(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {


        return dataList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView recName, recAmount;
    CardView recCard;
    ImageButton deleteButton;




    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recCard = itemView.findViewById(R.id.recCard);
        recName = itemView.findViewById(R.id.recName);
        recAmount = itemView.findViewById(R.id.recAmount);
        deleteButton = itemView.findViewById(R.id.deleteButton);

    }
}