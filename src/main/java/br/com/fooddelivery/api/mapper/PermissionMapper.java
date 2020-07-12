package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.dto.output.PermissionOutput;
import br.com.fooddelivery.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionMapper {
    private final ModelMapper modelMapper;

    public PermissionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PermissionOutput toOutput(Permission permission) {
        return this.modelMapper.map(permission, PermissionOutput.class);
    }

    public List<PermissionOutput> toCollectionOutput(Collection<Permission> permissions) {
        return permissions
                .stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }
}
