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
import com.finalprojectgroupae.immunization.activities.MainActivity;
import com.finalprojectgroupae.immunization.databinding.FragmentHomeBinding;
import com.finalprojectgroupae.immunization.ui.adapters.PatientAdapter;
import com.finalprojectgroupae.immunization.ui.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private PatientAdapter patientAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupRecyclerView();
        observeData();
        setupListeners();
    }

    private void setupRecyclerView() {
        patientAdapter = new PatientAdapter();
        binding.recyclerChildren.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerChildren.setAdapter(patientAdapter);
    }

    private void observeData() {
        // Observe all patients from database
        viewModel.getAllPatients().observe(getViewLifecycleOwner(), patients -> {
            if (patients != null && !patients.isEmpty()) {
                patientAdapter.submitList(patients);
                updateHeroCard(patients.size());
            } else {
                // Show empty state
                binding.heroTitle.setText("No children registered");
                binding.heroSubtitle.setText("Add your first child to start tracking vaccinations");
            }
        });

        // Observe patient count
        viewModel.getPatientCount().observe(getViewLifecycleOwner(), count -> {
            if (count != null && count > 0) {
                binding.heroTitle.setText(count + " children registered");
            }
        });

        // Observe due doses
        viewModel.getDueDoses().observe(getViewLifecycleOwner(), doses -> {
            if (doses != null && !doses.isEmpty()) {
                binding.heroSubtitle.setText(doses.size() + " vaccinations due this week");
            }
        });
    }

    private void updateHeroCard(int patientCount) {
        binding.heroTitle.setText("Community Coverage");
        binding.heroSubtitle.setText(patientCount + " children tracked • View schedules below");
    }

    private void setupListeners() {
        binding.heroCta.setOnClickListener(view -> {
            if (requireActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).navigateToTab(R.id.nav_schedule);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}