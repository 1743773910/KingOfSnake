package com.codesystem.pojo;

public class C_P {
    private String phone;
    private String code;

    public C_P(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }

    public C_P() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "C_P{" +
                "phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
