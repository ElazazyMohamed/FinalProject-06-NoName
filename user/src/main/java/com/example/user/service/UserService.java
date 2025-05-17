package com.example.user.service;

import com.example.user.models.BaseUser;
import com.example.user.models.Status;
import com.example.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;

    // UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Helper Function
    public boolean emailExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    // CRUD Functions
    @Transactional
    public BaseUser signUp(BaseUser baseUser) {
        return userRepository.save(baseUser);
    }

    public UserDTO getUserById(Integer id) {
        BaseUser baseUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userDTOMapper.apply(baseUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getStatus() == Status.ACTIVE)
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        BaseUser baseUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (userDTO.getName() != null && !userDTO.getName().trim().isEmpty()) {
            baseUser.setName(userDTO.getName());
        }
        if (userDTO.getPhone() != null && !userDTO.getPhone().trim().isEmpty()) {
            baseUser.setPhone(userDTO.getPhone());
        }
        BaseUser updatedUser = userRepository.save(baseUser);
        return userDTOMapper.apply(updatedUser);
    }

    @Transactional
    public void deleteUser(Integer id) {
        BaseUser baseUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        baseUser.setStatus(Status.INACTIVE);
        userRepository.save(baseUser);
    }

//    --------------------------------------------------------------------------

    // 3 Required Functions
    public List<UserDTO> searchByEmailSubstring(String emailSubstring) {
        if (emailSubstring == null || emailSubstring.trim().isEmpty()) {
            throw new IllegalArgumentException("Email substring cannot be empty");
        }
        return userRepository.findByEmailContainingIgnoreCase(emailSubstring)
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deactivatedUser(Integer id) {
        BaseUser baseUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        baseUser.setStatus(Status.INACTIVE);
        userRepository.save(baseUser);
    }
}