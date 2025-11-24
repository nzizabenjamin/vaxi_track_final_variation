package com.finalprojectgroupae.immunization.ui.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.finalprojectgroupae.immunization.data.model.Reminder;
import com.finalprojectgroupae.immunization.databinding.ItemReminderBinding;

import java.util.ArrayList;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private final List<Reminder> items = new ArrayList<>();

    public void submitList(List<Reminder> reminders) {
        items.clear();
        if (reminders != null) {
            items.addAll(reminders);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemReminderBinding binding = ItemReminderBinding.inflate(inflater, parent, false);
        return new ReminderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ReminderViewHolder extends RecyclerView.ViewHolder {

        private final ItemReminderBinding binding;

        ReminderViewHolder(ItemReminderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Reminder reminder) {
            Context context = binding.getRoot().getContext();
            binding.textChannel.setText(reminder.getChannel());
            binding.textRecipient.setText(reminder.getRecipient());
            binding.textMessage.setText(reminder.getMessage());
            binding.textStatus.setText(reminder.getStatus());

            int color = ContextCompat.getColor(context, reminder.getChannelColorRes());
            ViewCompat.setBackgroundTintList(binding.textChannel, ColorStateList.valueOf(color));
        }
    }
}

