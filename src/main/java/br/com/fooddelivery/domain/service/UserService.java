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

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private EntityManager entityManager;
    private GroupService groupService;

    @Autowired
    public UserService(UserRepository userRepository, EntityManager entityManager, GroupService groupService) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
        this.groupService = groupService;
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
        this.entityManager.detach(user);

        Optional<User> userRegistered = this.userRepository.findByEmail(user.getEmail());

        if (userRegistered.isPresent() && !userRegistered.get().equals(user)) {
            throw new BusinessException(String.format("E-mail %s already registered", user.getEmail()));
        }

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

    @Transactional
    public void associateGroup(Integer userId, Integer groupId) {
        var user = this.getUserById(userId);

        var group = this.groupService.getGroupById(groupId);

        user.addGroup(group);
    }

    @Transactional
    public void disassociateGroup(Integer userId, Integer groupId) {
        var user = this.getUserById(userId);

        var group = this.groupService.getGroupById(groupId);

        user.removeGroup(group);
    }
}
