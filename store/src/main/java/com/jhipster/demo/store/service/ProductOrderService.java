package com.jhipster.demo.store.service;

import com.jhipster.demo.store.domain.ProductOrder;
import com.jhipster.demo.store.repository.ProductOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ProductOrder}.
 */
@Service
@Transactional
public class ProductOrderService {

    private final Logger log = LoggerFactory.getLogger(ProductOrderService.class);

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    /**
     * Save a productOrder.
     *
     * @param productOrder the entity to save.
     * @return the persisted entity.
     */
    public Mono<ProductOrder> save(ProductOrder productOrder) {
        log.debug("Request to save ProductOrder : {}", productOrder);
        return productOrderRepository.save(productOrder);
    }

    /**
     * Partially update a productOrder.
     *
     * @param productOrder the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ProductOrder> partialUpdate(ProductOrder productOrder) {
        log.debug("Request to partially update ProductOrder : {}", productOrder);

        return productOrderRepository
            .findById(productOrder.getId())
            .map(existingProductOrder -> {
                if (productOrder.getPlacedDate() != null) {
                    existingProductOrder.setPlacedDate(productOrder.getPlacedDate());
                }
                if (productOrder.getStatus() != null) {
                    existingProductOrder.setStatus(productOrder.getStatus());
                }
                if (productOrder.getCode() != null) {
                    existingProductOrder.setCode(productOrder.getCode());
                }
                if (productOrder.getInvoiceId() != null) {
                    existingProductOrder.setInvoiceId(productOrder.getInvoiceId());
                }

                return existingProductOrder;
            })
            .flatMap(productOrderRepository::save);
    }

    /**
     * Get all the productOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<ProductOrder> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOrders");
        return productOrderRepository.findAllBy(pageable);
    }

    /**
     * Returns the number of productOrders available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return productOrderRepository.count();
    }

    /**
     * Get one productOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ProductOrder> findOne(Long id) {
        log.debug("Request to get ProductOrder : {}", id);
        return productOrderRepository.findById(id);
    }

    /**
     * Delete the productOrder by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete ProductOrder : {}", id);
        return productOrderRepository.deleteById(id);
    }
}
