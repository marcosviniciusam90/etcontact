package com.mvam.etcontact.services;

import com.mvam.etcontact.dto.UserDTO;
import com.mvam.etcontact.entities.User;
import com.mvam.etcontact.mappers.UserMapper;
import com.mvam.etcontact.repositories.UserRepository;
import com.mvam.etcontact.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {

    private static final UserMapper USER_MAPPER = UserMapper.INSTANCE;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> optionalEntity = userRepository.findById(id);
        User entity = optionalEntity.orElseThrow(() -> new ResourceNotFoundException(id));
        return USER_MAPPER.entityToDTO(entity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }
}
