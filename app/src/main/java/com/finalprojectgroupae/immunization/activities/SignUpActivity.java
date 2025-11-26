package com.finalprojectgroupae.immunization.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.data.AuthManager;
import com.finalprojectgroupae.immunization.databinding.ActivitySignUpBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private AuthManager authManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        authManager = new AuthManager(this);

        binding.roleToggle.addOnButtonCheckedListener(this::ensureRoleSelected);
        binding.roleToggle.check(binding.roleUser.getId());

        binding.btnCreateAccount.setOnClickListener(v -> attemptSignUp());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void ensureRoleSelected(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if (!isChecked && group.getCheckedButtonId() == MaterialButtonToggleGroup.NO_ID) {
            group.check(binding.roleUser.getId());
        }
    }

    private void attemptSignUp() {
        clearErrors();

        String name = getInputText(binding.inputName);
        String username = getInputText(binding.inputUsername);
        String password = getInputText(binding.inputPassword);
        String confirmPassword = getInputText(binding.inputPasswordConfirm);
        String role = getSelectedRole();

        if (TextUtils.isEmpty(name)) {
            binding.nameLayout.setError(getString(R.string.error_empty_name));
            return;
        }
        if (TextUtils.isEmpty(username)) {
            binding.usernameLayout.setError(getString(R.string.error_empty_username));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            binding.passwordLayout.setError(getString(R.string.error_empty_password));
            return;
        }
        if (!TextUtils.equals(password, confirmPassword)) {
            binding.passwordConfirmLayout.setError(getString(R.string.error_password_mismatch));
            return;
        }

        boolean created = authManager.signUp(name, username, password, role);
        if (created) {
            Toast.makeText(this, R.string.msg_signup_success, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            binding.usernameLayout.setError(getString(R.string.error_account_exists));
        }
    }

    private String getSelectedRole() {
        int checkedId = binding.roleToggle.getCheckedButtonId();
        if (checkedId == binding.roleAdmin.getId()) {
            return AuthManager.ROLE_ADMIN;
        }
        return AuthManager.ROLE_USER;
    }

    private String getInputText(TextInputEditText editText) {
        return editText.getText() != null ? editText.getText().toString().trim() : "";
    }

    private void clearErrors() {
        binding.nameLayout.setError(null);
        binding.usernameLayout.setError(null);
        binding.passwordLayout.setError(null);
        binding.passwordConfirmLayout.setError(null);
    }
}


