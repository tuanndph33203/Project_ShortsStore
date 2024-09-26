package com.example.website_ban_ao_the_thao_ps.exception;

public class EmailExistenceResponse {
    private boolean checkEmail;

    public EmailExistenceResponse(boolean checkEmail) {
        this.checkEmail = checkEmail;
    }

    public boolean isCheckEmail() {
        return checkEmail;
    }

    public void setCheckEmail(boolean checkEmail) {
        this.checkEmail = checkEmail;
    }
}
