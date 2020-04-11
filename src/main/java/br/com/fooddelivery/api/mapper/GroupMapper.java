package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.model.entry.GroupEntry;
import br.com.fooddelivery.api.model.output.GroupOutput;
import br.com.fooddelivery.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public GroupMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GroupOutput toOutput(Group group) {
        return this.modelMapper.map(group, GroupOutput.class);
    }

    public List<GroupOutput> toCollectionOutput(List<Group> groups) {
        return groups.stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    public Group toDomain(GroupEntry groupEntry) {
        return this.modelMapper.map(groupEntry, Group.class);
    }

    public void copyPropertiesToDomain(GroupEntry groupEntry, Group group) {
        this.modelMapper.map(groupEntry, group);
    }
}