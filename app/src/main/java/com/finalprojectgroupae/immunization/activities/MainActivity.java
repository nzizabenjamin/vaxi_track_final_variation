package com.finalprojectgroupae.immunization.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.data.AuthManager;
import com.finalprojectgroupae.immunization.databinding.ActivityMainBinding;
import com.finalprojectgroupae.immunization.ui.fragments.ChildDetailFragment;
import com.finalprojectgroupae.immunization.ui.fragments.DashboardFragment;
import com.finalprojectgroupae.immunization.ui.fragments.HomeFragment;
import com.finalprojectgroupae.immunization.ui.fragments.RemindersFragment;
import com.finalprojectgroupae.immunization.ui.fragments.ScheduleFragment;
import com.finalprojectgroupae.immunization.utils.SampleDataPopulator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AuthManager authManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authManager = new AuthManager(this);
        if (!authManager.isLoggedIn()) {
            navigateToLogin();
            return;
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(R.string.app_name);

        binding.bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                openFragment(new HomeFragment(), HomeFragment.TAG);
            } else if (id == R.id.nav_child_detail) {
                openFragment(new ChildDetailFragment(), ChildDetailFragment.TAG);
            } else if (id == R.id.nav_schedule) {
                openFragment(new ScheduleFragment(), ScheduleFragment.TAG);
            } else if (id == R.id.nav_reminders) {
                openFragment(new RemindersFragment(), RemindersFragment.TAG);
            } else if (id == R.id.nav_dashboard) {
                openFragment(new DashboardFragment(), DashboardFragment.TAG);
            } else {
                return false;
            }
            return true;
        });

        if (savedInstanceState == null) {
            binding.bottomNav.setSelectedItemId(R.id.nav_home);
        }
    }

    private void openFragment(@NonNull Fragment fragment, @NonNull String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            authManager.logout();
            navigateToLogin();
            return true;
        } else if (item.getItemId() == R.id.action_populate_sample_data) {
            new SampleDataPopulator(getApplication()).populateSampleData();
            Toast.makeText(this, "Populating sample data...", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_register_patient) {
            // Check if user is CHW or Admin
            String role = authManager.getActiveRole();
            if (AuthManager.ROLE_ADMIN.equals(role) || AuthManager.ROLE_USER.equals(role)) {
                Intent intent = new Intent(this, PatientRegistrationActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Only CHWs and Admins can register patients", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (item.getItemId() == R.id.action_record_vaccination) {
            // Check if user is CHW or Admin
            String role = authManager.getActiveRole();
            if (AuthManager.ROLE_ADMIN.equals(role) || AuthManager.ROLE_USER.equals(role)) {
                Intent intent = new Intent(this, RecordVaccinationActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Only CHWs and Admins can record vaccinations", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void navigateToTab(@IdRes int menuItemId) {
        if (binding != null) {
            binding.bottomNav.setSelectedItemId(menuItemId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
