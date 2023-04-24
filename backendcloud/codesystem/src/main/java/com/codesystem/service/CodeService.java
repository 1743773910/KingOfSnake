package com.codesystem.service;

public interface CodeService {
    String addCode(String phone);
    String matchingCode(String phone, String code);
}
