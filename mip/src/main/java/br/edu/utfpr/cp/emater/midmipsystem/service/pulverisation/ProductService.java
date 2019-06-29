package br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.service.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.RegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Product;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Target;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.ProductRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.TargetRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.ICRUDService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ProductService implements ICRUDService<Product> {

    private final ProductRepository productRepository;
    
    @Autowired
    public ProductService(ProductRepository aProductRepository) {
        this.productRepository = aProductRepository;
    }

    @Override
    public List<Product> readAll() {
        return List.copyOf(productRepository.findAll());
    }

    public Product readById(Long anId) throws EntityNotFoundException {
        return productRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
    }

    public void create(Product aProduct) throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {

        if (productRepository.findAll().stream().anyMatch(currentTarget -> currentTarget.equals(aProduct))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            productRepository.save(aProduct);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public void update(Product aProduct) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {

        var existentProduct = productRepository.findById(aProduct.getId()).orElseThrow(EntityNotFoundException::new);
        
        var allProductsWithoutExistentProduct = new ArrayList<Product>(productRepository.findAll());
        allProductsWithoutExistentProduct.remove(existentProduct);
        
        if (allProductsWithoutExistentProduct.stream().anyMatch(currentProduct -> currentProduct.equals(aProduct))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            existentProduct.setName(aProduct.getName());
            existentProduct.setDose(aProduct.getDose());
            existentProduct.setUnit(aProduct.getUnit());

            productRepository.saveAndFlush(existentProduct);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public void delete(Long anId) throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        
        var existentProduct = productRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
        
        try {
            productRepository.delete(existentProduct);
            
        } catch (DataIntegrityViolationException cve) {
            throw new EntityInUseException();
            
        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }
}
