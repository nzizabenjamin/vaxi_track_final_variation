package com.finalprojectgroupae.immunization.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.databinding.FragmentScheduleBinding;
import com.finalprojectgroupae.immunization.ui.adapters.AppointmentAdapter;
import com.finalprojectgroupae.immunization.ui.viewmodel.ScheduleViewModel;

public class ScheduleFragment extends Fragment {

    public static final String TAG = "ScheduleFragment";

    private FragmentScheduleBinding binding;
    private AppointmentAdapter adapter;
    private ScheduleViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        
        adapter = new AppointmentAdapter();
        binding.recyclerAppointments.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerAppointments.setAdapter(adapter);
        
        observeData();
    }

    private void observeData() {
        // Observe upcoming appointments from database
        viewModel.getUpcomingAppointments().observe(getViewLifecycleOwner(), appointments -> {
            if (appointments != null && !appointments.isEmpty()) {
                adapter.submitList(appointments);
            } else {
                // Show empty state - could add an empty view here
                adapter.submitList(null);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

