package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.KitchenNotFoundException;
import br.com.fooddelivery.domain.model.Kitchen;
import br.com.fooddelivery.domain.repository.KitchenRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class KitchenService {
    private final KitchenRepository kitchenRepository;

    public KitchenService(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    public Page<Kitchen> getKitchens(Pageable pageable) {
        return this.kitchenRepository.findAll(pageable);
    }

    public Kitchen getKitchenById(Integer id) {
        return this.kitchenRepository
                .findById(id)
                .orElseThrow(() -> new KitchenNotFoundException(id));
    }

    @Transactional
    public Kitchen saveKitchen(Kitchen kitchen) {
        return this.kitchenRepository.save(kitchen);
    }

    @Transactional
    public void deleteKitchenById(Integer id) {
        try {
            this.kitchenRepository.deleteById(id);
            this.kitchenRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new KitchenNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("Kitchen cannot be removed as it is in use: %s", id));
        }
    }
}
