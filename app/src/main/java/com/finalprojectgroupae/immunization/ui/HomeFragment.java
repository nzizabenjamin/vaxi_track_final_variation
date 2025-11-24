package com.finalprojectgroupae.immunization.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.finalprojectgroupae.immunization.MainActivity;
import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.data.DemoDataProvider;
import com.finalprojectgroupae.immunization.data.model.Child;
import com.finalprojectgroupae.immunization.databinding.FragmentHomeBinding;
import com.finalprojectgroupae.immunization.ui.adapters.ChildRosterAdapter;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    private FragmentHomeBinding binding;
    private ChildRosterAdapter rosterAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        populateHeroCard();
        setupListeners();
    }

    private void setupRecyclerView() {
        rosterAdapter = new ChildRosterAdapter();
        binding.recyclerChildren.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerChildren.setAdapter(rosterAdapter);
        rosterAdapter.submitList(DemoDataProvider.getPriorityChildren());
    }

    private void populateHeroCard() {
        Child featured = DemoDataProvider.getFeaturedChild();
        if (featured != null) {
            binding.heroTitle.setText(featured.getVillage() + " outreach");
            binding.heroSubtitle.setText(featured.getName() + " • " + featured.getDueVaccine());
        }
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

