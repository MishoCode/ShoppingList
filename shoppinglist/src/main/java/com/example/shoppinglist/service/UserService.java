package com.example.shoppinglist.service;

import com.example.shoppinglist.dto.UploadImageRequest;
import com.example.shoppinglist.dto.UserResponse;
import com.example.shoppinglist.entity.Image;
import com.example.shoppinglist.entity.User;
import com.example.shoppinglist.entity.VerificationToken;
import com.example.shoppinglist.exception.UserAlreadyExistsException;
import com.example.shoppinglist.exception.UserNotFoundException;
import com.example.shoppinglist.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenService verificationTokenService;
    private ImageService imageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
            () -> new UserNotFoundException(
                String.format("User with username %s not found.", username)));
    }

    public UserResponse signUpUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            if (!dbUser.isEnabled() && hasTheSameFields(dbUser, user)) {
                String newToken = generateToken(dbUser);
                return UserResponse.from(dbUser, newToken);
            }

            if (dbUser.isEnabled() || !hasTheSameFields(dbUser, user)) {
                throw new UserAlreadyExistsException(
                    String.format("Email %s has already been taken.", user.getEmail()));
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        String token = generateToken(user);

        return UserResponse.from(savedUser, token);
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    public UserResponse addImage(Long userId, MultipartFile image) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException(String.format("User with id %d does not exist", userId))
        );

        Image userImage = imageService.addImage(
            new UploadImageRequest(
                "user_" + user.getEmail() + "_image",
                image));

        user.setImage(userImage);
        userRepository.updateImage(userId, userImage);
        return UserResponse.from(user, null);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(u -> UserResponse.from(u, null)).toList();
    }

    private String generateToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusSeconds(60),
            user);
        verificationTokenService.saveToken(verificationToken);

        return token;
    }

    private boolean hasTheSameFields(User dbUser, User requestUser) {
        return dbUser.getFirstName().equals(requestUser.getFirstName()) &&
               dbUser.getLastName().equals(requestUser.getLastName()) &&
               dbUser.getEmail().equals(requestUser.getEmail()) &&
               passwordEncoder.matches(requestUser.getPassword(), dbUser.getPassword());
    }
}
