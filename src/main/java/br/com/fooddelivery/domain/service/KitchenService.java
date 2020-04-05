package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.KitchenNotFoundException;
import br.com.fooddelivery.domain.model.Kitchen;
import br.com.fooddelivery.domain.repository.KitchenRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitchenService {
    private final KitchenRepository kitchenRepository;

    @Autowired
    public KitchenService(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    public List<Kitchen> getKitchens() {
        return this.kitchenRepository.findAll();
    }

    public Kitchen getKitchenById(Integer id) {
        return this.kitchenRepository
                .findById(id)
                .orElseThrow(() -> new KitchenNotFoundException(id));
    }

    public Kitchen createKitchen(Kitchen kitchen) {
        return this.kitchenRepository.save(kitchen);
    }

    public void deleteKitchenById(Integer id) {
        try {
            this.kitchenRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new KitchenNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("Kitchen cannot be removed as it is in use: %s", id));
        }
    }

    public Kitchen updateKitchen(Integer id, Kitchen kitchen) {
        var savedKitchen = this.getKitchenById(id);

        BeanUtils.copyProperties(kitchen, savedKitchen, "id");
        savedKitchen = this.createKitchen(savedKitchen);

        return savedKitchen;
    }
}
