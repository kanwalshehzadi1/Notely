package com.example.android.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Note;
import com.example.todoapp.R;
import com.example.todoapp.databinding.EachRvBinding;


public class Rv_Adapter extends ListAdapter<Note,Rv_Adapter.ViewHolder>{
    public Rv_Adapter(){
        super(CALLBACK);

    }
    private static final DiffUtil.ItemCallback<Note> CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDescription().equals(newItem.getDescription());
        }
    };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_rv,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = getItem(position);
        holder.binding.tittle.setText(note.getTitle());
        holder.binding.description.setText(note.getDescription());


    }

    public Note getNote(int adapterPosition) {

        return getItem(adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        EachRvBinding binding;
        public ViewHolder(@Nullable View itemView) {
            super(itemView);
            binding = EachRvBinding.bind(itemView);

        }

    }
}
