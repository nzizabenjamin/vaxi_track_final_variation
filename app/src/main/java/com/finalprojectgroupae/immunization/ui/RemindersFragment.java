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
import com.finalprojectgroupae.immunization.databinding.FragmentRemindersBinding;
import com.finalprojectgroupae.immunization.ui.adapters.ReminderAdapter;

public class RemindersFragment extends Fragment {

    public static final String TAG = "RemindersFragment";

    private FragmentRemindersBinding binding;
    private ReminderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRemindersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ReminderAdapter();
        binding.recyclerReminders.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerReminders.setAdapter(adapter);
        adapter.submitList(DemoDataProvider.getReminderQueue());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

