package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.GroupNotFoundException;
import br.com.fooddelivery.domain.model.Group;
import br.com.fooddelivery.domain.repository.GroupRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final PermissionService permissionService;

    public GroupService(GroupRepository groupRepository, PermissionService permissionService) {
        this.groupRepository = groupRepository;
        this.permissionService = permissionService;
    }

    public Page<Group> getGroups(Pageable pageable) {
        return this.groupRepository.findAll(pageable);
    }

    public Group getGroupById(Integer id) {
        return this.groupRepository
                .findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));
    }

    @Transactional
    public Group saveGroup(Group group) {
        return this.groupRepository.save(group);
    }

    @Transactional
    public void deleteById(Integer id) {
        try {
            this.groupRepository.deleteById(id);
            this.groupRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GroupNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("Group cannot be removed as it is in use: %s", id));
        }
    }

    @Transactional
    public void associatePermission(Integer groupId, Integer permissionId) {
        var group = this.getGroupById(groupId);

        var permission = this.permissionService.getPermissionById(permissionId);

        group.removePermission(permission);
    }

    @Transactional
    public void disassociatePermission(Integer groupId, Integer permissionId) {
        var group = this.getGroupById(groupId);

        var permission = this.permissionService.getPermissionById(permissionId);

        group.removePermission(permission);
    }
}
