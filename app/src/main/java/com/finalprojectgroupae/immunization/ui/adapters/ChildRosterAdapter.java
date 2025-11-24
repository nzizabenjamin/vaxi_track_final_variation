package com.finalprojectgroupae.immunization.ui.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.finalprojectgroupae.immunization.data.model.Child;
import com.finalprojectgroupae.immunization.databinding.ItemChildCardBinding;

import java.util.ArrayList;
import java.util.List;

public class ChildRosterAdapter extends RecyclerView.Adapter<ChildRosterAdapter.ChildViewHolder> {

    private final List<Child> items = new ArrayList<>();

    public void submitList(List<Child> children) {
        items.clear();
        if (children != null) {
            items.addAll(children);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemChildCardBinding binding = ItemChildCardBinding.inflate(inflater, parent, false);
        return new ChildViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        private final ItemChildCardBinding binding;

        ChildViewHolder(ItemChildCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Child child) {
            Context context = binding.getRoot().getContext();
            binding.textChildName.setText(child.getName());
            binding.textDueInfo.setText(child.getDueVaccine() + " • " + child.getDueDate());
            binding.textCaregiver.setText("Caregiver: " + child.getCaregiver());
            binding.textVillage.setText(child.getVillage());
            binding.textStatus.setText(child.getStatus());

            int color = ContextCompat.getColor(context, child.getStatusColorRes());
            ViewCompat.setBackgroundTintList(binding.textStatus, ColorStateList.valueOf(color));
        }
    }
}

