package com.finalprojectgroupae.immunization.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.finalprojectgroupae.immunization.data.DemoDataProvider;
import com.finalprojectgroupae.immunization.databinding.FragmentScheduleBinding;
import com.finalprojectgroupae.immunization.ui.adapters.AppointmentAdapter;

public class ScheduleFragment extends Fragment {

    public static final String TAG = "ScheduleFragment";

    private FragmentScheduleBinding binding;
    private AppointmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new AppointmentAdapter();
        binding.recyclerAppointments.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerAppointments.setAdapter(adapter);
        adapter.submitList(DemoDataProvider.getUpcomingAppointments());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

