package com.finalprojectgroupae.immunization.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.data.AuthManager;
import com.finalprojectgroupae.immunization.databinding.ActivityLoginBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthManager authManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authManager = new AuthManager(this);
        authManager.ensureSeedUsers();
        authManager.logout(); // force re-authentication on every fresh app launch

        binding.roleToggle.addOnButtonCheckedListener(this::handleRoleToggleState);
        binding.roleToggle.check(binding.roleUser.getId());

        binding.btnLogin.setOnClickListener(v -> attemptLogin());
        binding.btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void handleRoleToggleState(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if (!isChecked) {
            // Ensure at least one role stays selected.
            if (group.getCheckedButtonId() == View.NO_ID) {
                group.check(binding.roleUser.getId());
            }
        }
    }

    private void attemptLogin() {
        clearErrors();
        String username = binding.inputUsername.getText() != null ? binding.inputUsername.getText().toString().trim() : "";
        String password = binding.inputPassword.getText() != null ? binding.inputPassword.getText().toString() : "";
        String role = getSelectedRole();

        if (TextUtils.isEmpty(username)) {
            binding.usernameLayout.setError(getString(R.string.error_empty_username));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            binding.passwordLayout.setError(getString(R.string.error_empty_password));
            return;
        }

        boolean success = authManager.login(username, password, role);
        if (success) {
            Toast.makeText(this, R.string.msg_login_success, Toast.LENGTH_SHORT).show();
            navigateToDashboard();
        } else {
            binding.passwordLayout.setError(getString(R.string.error_auth_failed));
        }
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private String getSelectedRole() {
        int checkedId = binding.roleToggle.getCheckedButtonId();
        if (checkedId == binding.roleAdmin.getId()) {
            return AuthManager.ROLE_ADMIN;
        }
        return AuthManager.ROLE_USER;
    }

    private void clearErrors() {
        binding.usernameLayout.setError(null);
        binding.passwordLayout.setError(null);
    }
}


