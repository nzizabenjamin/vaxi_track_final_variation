package com.finalprojectgroupae.immunization.ui.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.finalprojectgroupae.immunization.data.model.Appointment;
import com.finalprojectgroupae.immunization.databinding.ItemAppointmentBinding;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private final List<Appointment> items = new ArrayList<>();

    public void submitList(List<Appointment> appointments) {
        items.clear();
        if (appointments != null) {
            items.addAll(appointments);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAppointmentBinding binding = ItemAppointmentBinding.inflate(inflater, parent, false);
        return new AppointmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {

        private final ItemAppointmentBinding binding;

        AppointmentViewHolder(ItemAppointmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Appointment appointment) {
            Context context = binding.getRoot().getContext();
            binding.textAppointmentTitle.setText(appointment.getTitle());
            binding.textAppointmentLocation.setText(appointment.getLocation());
            binding.textAppointmentDate.setText(appointment.getDate() + " • " + appointment.getTime());
            binding.textAppointmentMode.setText(appointment.getMode());

            int color = ContextCompat.getColor(context, appointment.getModeColorRes());
            ViewCompat.setBackgroundTintList(binding.textAppointmentMode, ColorStateList.valueOf(color));
        }
    }
}

