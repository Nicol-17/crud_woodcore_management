package com.crud.crudProyecto.service;

import java.io.File;

public interface IEmailService {
    void sendEmail(String[] toUser, String subject, String message);

    void sendEmailWhiteFile(String[] toUser, String subject, String message, File file);
}
