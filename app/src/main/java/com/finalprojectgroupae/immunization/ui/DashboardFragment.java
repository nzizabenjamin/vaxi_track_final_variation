package com.finalprojectgroupae.immunization.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.finalprojectgroupae.immunization.MainActivity;
import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.data.DemoDataProvider;
import com.finalprojectgroupae.immunization.data.model.DashboardStat;
import com.finalprojectgroupae.immunization.databinding.FragmentDashboardBinding;

import java.util.List;

public class DashboardFragment extends Fragment {

    public static final String TAG = "DashboardFragment";

    private FragmentDashboardBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupActions();
        binding.filterToggle.check(binding.btnFilterOutreach.getId());
        bindStats();
    }

    private void bindStats() {
        List<DashboardStat> stats = DemoDataProvider.getDashboardStats();
        if (stats.isEmpty()) {
            return;
        }
        bindHero(stats.get(0));
        if (stats.size() >= 4) {
            bindSingleStat(binding.statDue, stats.get(0));
            bindSingleStat(binding.statOverdue, stats.get(1));
            bindSingleStat(binding.statCompleted, stats.get(2));
            bindSingleStat(binding.statSessions, stats.get(3));
        }
    }

    private void bindSingleStat(com.finalprojectgroupae.immunization.databinding.ItemDashboardStatBinding statBinding,
                                DashboardStat stat) {
        int color = ContextCompat.getColor(requireContext(), stat.getAccentColorRes());
        statBinding.textStatLabel.setText(stat.getLabel());
        statBinding.textStatValue.setText(stat.getValue());
        statBinding.textStatValue.setTextColor(color);
        statBinding.textStatSubtitle.setText(stat.getSubtitle());
    }

    private void bindHero(@NonNull DashboardStat primaryStat) {
        binding.textHeroValue.setText(primaryStat.getValue());
        binding.textHeroLabel.setText(primaryStat.getLabel());
    }

    private void setupActions() {
        binding.btnViewRoster.setOnClickListener(v -> navigateFromDashboard(R.id.nav_home));
        binding.btnLaunchSchedule.setOnClickListener(v -> navigateFromDashboard(R.id.nav_schedule));
        binding.btnReviewReminders.setOnClickListener(v -> navigateFromDashboard(R.id.nav_reminders));
    }

    private void navigateFromDashboard(@IdRes int destinationId) {
        if (requireActivity() instanceof MainActivity) {
            ((MainActivity) requireActivity()).navigateToTab(destinationId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

