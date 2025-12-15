package com.finalprojectgroupae.immunization.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.data.local.entities.PatientEntity;
import com.finalprojectgroupae.immunization.databinding.ItemChildCardBinding;
import com.finalprojectgroupae.immunization.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private final List<PatientEntity> patients = new ArrayList<>();

    public void submitList(List<PatientEntity> newPatients) {
        patients.clear();
        if (newPatients != null) {
            patients.addAll(newPatients);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemChildCardBinding binding = ItemChildCardBinding.inflate(inflater, parent, false);
        return new PatientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        holder.bind(patients.get(position));
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    static class PatientViewHolder extends RecyclerView.ViewHolder {
        private final ItemChildCardBinding binding;

        PatientViewHolder(ItemChildCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(PatientEntity patient) {
            binding.textChildName.setText(patient.getFullName());

            // Show age
            String age = DateUtils.getHumanReadableAge(patient.getDateOfBirth());
            binding.textDueInfo.setText("Age: " + age);

            // Guardian info
            binding.textCaregiver.setText("Guardian: " + patient.getGuardianName());

            // Location
            binding.textVillage.setText(patient.getVillage() + ", " + patient.getDistrict());

            // Status - you can enhance this based on vaccination progress
            binding.textStatus.setText("Active");
            binding.textStatus.setBackgroundTintList(
                    itemView.getContext().getColorStateList(R.color.colorSuccess)
            );
        }
    }
}