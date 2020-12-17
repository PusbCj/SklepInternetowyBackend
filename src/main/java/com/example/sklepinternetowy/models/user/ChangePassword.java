package com.example.sklepinternetowy.models.user;

public class ChangePassword {
    private String oldPassword;
    private String newPassword;

    public ChangePassword() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
