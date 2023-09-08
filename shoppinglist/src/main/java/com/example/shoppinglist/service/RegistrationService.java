package com.example.shoppinglist.service;

import com.example.shoppinglist.config.MailSenderConfig;
import com.example.shoppinglist.dto.RegistrationRequest;
import com.example.shoppinglist.dto.UploadImageRequest;
import com.example.shoppinglist.dto.UserResponse;
import com.example.shoppinglist.entity.Image;
import com.example.shoppinglist.entity.User;
import com.example.shoppinglist.entity.UserRole;
import com.example.shoppinglist.entity.VerificationToken;
import com.example.shoppinglist.exception.TokenAlreadyConfirmedException;
import com.example.shoppinglist.exception.TokenExpiredException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private UserService userService;
    private EmailService emailService;
    private VerificationTokenService verificationTokenService;
    private MailSenderConfig mailSenderConfig;
    private ImageService imageService;

    public UserResponse register(RegistrationRequest request) {
        User user = new User(
            request.getFirstName(),
            request.getLastName(),
            request.getEmail(),
            request.getPassword(),
            UserRole.USER);

        /*Image userImage = imageService.addImage(
            new UploadImageRequest(
                "user_" + user.getEmail() + "_image",
                image));
        user.setImage(userImage); */

        UserResponse userResponse = userService.signUpUser(user);
        String token = userResponse.getToken();

        String link = "http://localhost:8080/users/registration/confirm?token=" + token;
        emailService.send(mailSenderConfig.getMyEmail(), // request.getEmail()
            writeEmail(userResponse.getFirstName(), link));

        return userResponse;
    }

    public boolean confirmToken(String token) {
        VerificationToken verificationToken = verificationTokenService.getToken(token);

        if (verificationToken.getConfirmedAt() != null) {
            throw new TokenAlreadyConfirmedException("This email is already confirmed.");
        }

        if (verificationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("This token is expired.");
        }

        verificationTokenService.setConfirmedAt(token);
        userService.enableUser(verificationToken.getUser().getEmail());
        return true;
    }

    private String writeEmail(String name, String link) {
        return "<p> Hi, " + name + ", </p>" +
               "<p>Thank you for registering with us," + "" +
               "Please, follow the link below to complete your registration.</p>" +
               "<a href=\"" + link + "\">Verify your email to activate your account</a>" +
               "<p> Thank you <br> Users Registration Portal Service";
    }
}
