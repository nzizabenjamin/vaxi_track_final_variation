package com.finalprojectgroupae.immunization.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.data.DemoDataProvider;
import com.finalprojectgroupae.immunization.data.model.Child;
import com.finalprojectgroupae.immunization.databinding.FragmentChildDetailBinding;

import java.util.List;

public class ChildDetailFragment extends Fragment {

    public static final String TAG = "ChildDetailFragment";

    private FragmentChildDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChildDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindFeaturedChild();
        populateTimeline();
    }

    private void bindFeaturedChild() {
        Child featuredChild = DemoDataProvider.getFeaturedChild();
        if (featuredChild == null) {
            return;
        }
        binding.textChildName.setText(featuredChild.getName());
        binding.textCaregiver.setText(getString(R.string.label_caregiver) + ": " + featuredChild.getCaregiver());
        binding.textVillage.setText(getString(R.string.label_village) + ": " + featuredChild.getVillage());
        binding.textNextVaccine.setText("Next vaccine • " + featuredChild.getDueVaccine());
        binding.textNextDue.setText("Due date • " + featuredChild.getDueDate());
    }

    private void populateTimeline() {
        List<String> timeline = DemoDataProvider.getChildTimeline();
        binding.containerTimeline.removeAllViews();
        for (String entry : timeline) {
            TextView textView = (TextView) LayoutInflater.from(requireContext())
                    .inflate(R.layout.simple_timeline_row, binding.containerTimeline, false);
            textView.setText(entry);
            binding.containerTimeline.addView(textView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

