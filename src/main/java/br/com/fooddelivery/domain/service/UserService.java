package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.BusinessException;
import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.UserNotFoundException;
import br.com.fooddelivery.domain.model.User;
import br.com.fooddelivery.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Transactional
    public void changePassword(Integer id, String currentPassword, String newPassword) {
        var user = this.getUserById(id);

        if (user.passwordNotMatch(currentPassword)) {
            throw new BusinessException("Current password entered does not match the user's password.");
        }

        user.setPassword(newPassword);
    }

    @Transactional
    public void deleteById(Integer id) {
        try {
            this.userRepository.deleteById(id);
            this.userRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("User cannot be removed as it is in use: %s", id));
        }
    }
}
