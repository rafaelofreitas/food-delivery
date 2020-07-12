package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.dto.entry.OrderItemEntry;
import br.com.fooddelivery.api.dto.output.OrderItemOutput;
import br.com.fooddelivery.domain.model.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemMapper {
    private final ModelMapper modelMapper;

    public OrderItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderItemOutput toOutput(OrderItem orderItem) {
        return this.modelMapper.map(orderItem, OrderItemOutput.class);
    }

    public List<OrderItemOutput> toCollectionOutput(Collection<OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    public OrderItem toDomain(OrderItemEntry orderItemEntry) {
        return this.modelMapper.map(orderItemEntry, OrderItem.class);
    }

    public void copyPropertiesToDomain(OrderItemEntry orderItemEntry, OrderItem orderItem) {
        this.modelMapper.map(orderItemEntry, orderItem);
    }
}