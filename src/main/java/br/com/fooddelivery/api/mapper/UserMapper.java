package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.model.entry.UserEntry;
import br.com.fooddelivery.api.model.output.UserOutput;
import br.com.fooddelivery.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserOutput toOutput(User user) {
        return this.modelMapper.map(user, UserOutput.class);
    }

    public List<UserOutput> toCollectionOutput(Collection<User> users) {
        return users
                .stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    public User toDomain(UserEntry userEntry) {
        return this.modelMapper.map(userEntry, User.class);
    }

    public void copyPropertiesToDomain(UserEntry userEntry, User user) {
        this.modelMapper.map(userEntry, user);
    }
}
