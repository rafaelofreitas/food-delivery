package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.controller.GroupController;
import br.com.fooddelivery.api.controller.UserController;
import br.com.fooddelivery.api.dto.entry.UserEntry;
import br.com.fooddelivery.api.dto.output.UserOutput;
import br.com.fooddelivery.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserMapper extends RepresentationModelAssemblerSupport<User, UserOutput> {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        super(UserController.class, UserOutput.class);

        this.modelMapper = modelMapper;
    }

    @Override
    public UserOutput toModel(User user) {
        UserOutput userOutput = super.createModelWithId(user.getId(), user);

        this.modelMapper.map(user, userOutput);

        userOutput.add(linkTo(methodOn(UserController.class)
                .getUserById(userOutput.getId()))
                .withSelfRel());

        userOutput.add(linkTo(methodOn(UserController.class)
                .getUsers())
                .withRel(IanaLinkRelations.COLLECTION));

        userOutput.add(linkTo(methodOn(GroupController.class)
                .getGroupById(user.getId()))
                .withRel(IanaLinkRelations.COLLECTION));

        return userOutput;
    }

    @Override
    public CollectionModel<UserOutput> toCollectionModel(Iterable<? extends User> users) {
        return super.toCollectionModel(users)
                .add(linkTo(UserController.class).withSelfRel());
    }

    public User toDomain(UserEntry userEntry) {
        return this.modelMapper.map(userEntry, User.class);
    }

    public void copyPropertiesToDomain(UserEntry userEntry, User user) {
        this.modelMapper.map(userEntry, user);
    }
}
