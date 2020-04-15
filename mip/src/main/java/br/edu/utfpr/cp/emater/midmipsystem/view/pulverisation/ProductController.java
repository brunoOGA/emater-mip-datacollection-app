package br.edu.utfpr.cp.emater.midmipsystem.view.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Product;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.ProductUnit;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Target;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.ToxiClass;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.UseClass;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation.ProductService;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class ProductController extends AbstractCRUDController<Product> {

    private final ProductService productService;

    @Getter
    @Setter
    protected Long id;

    @Getter
    @Setter
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    private String name;

    @Getter
    @Setter
    private ProductUnit unit;

    @Getter
    @Setter
    private UseClass useClass;

    @Getter
    @Setter
    private String concentrationActiveIngredient;

    @Getter
    @Setter
    private Long registerNumber;

    @Getter
    @Setter
    private String company;

    @Getter
    @Setter
    private String activeIngredient;

    @Getter
    @Setter
    private ToxiClass toxiClass;

    @Getter
    @Setter
    private Long targetId;

    @Override
    public List<Product> readAll() {
        return productService.readAll();
    }

    public ProductUnit[] readAllUnits() {
        return ProductUnit.values();
    }

    public UseClass[] readAllUseClasses() {
        return UseClass.values();
    }

    public ToxiClass[] readAllToxiClasses() {
        return ToxiClass.values();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var newProduct = Product.builder()
                .name(this.getName())
                .unit(this.getUnit())
                .useClass(this.getUseClass())
                .activeIngredient(this.getActiveIngredient())
                .company(this.getCompany())
                .concentrationActiveIngredient(this.getConcentrationActiveIngredient())
                .registerNumber(this.getRegisterNumber())
                .toxiClass(this.getToxiClass())
                .build();

        productService.create(newProduct);
    }

    @Override
    protected void doPrepareUpdate(Long anId) throws EntityNotFoundException {
        var existentProduct = productService.readById(anId);

        this.setId(existentProduct.getId());
        this.setName(existentProduct.getName());
        this.setUnit(existentProduct.getUnit());
        this.setUseClass(existentProduct.getUseClass());
        this.setActiveIngredient(existentProduct.getActiveIngredient());
        this.setCompany(existentProduct.getCompany());
        this.setConcentrationActiveIngredient(existentProduct.getConcentrationActiveIngredient());
        this.setRegisterNumber(existentProduct.getRegisterNumber());
        this.setToxiClass(existentProduct.getToxiClass());
    }

    @Override
    public String update() {

        try {
            var updatedProduct = Product.builder()
                    .id(this.getId())
                    .name(this.getName())
                    .unit(this.getUnit())
                    .useClass(this.getUseClass())
                    .activeIngredient(this.getActiveIngredient())
                    .company(this.getCompany())
                    .concentrationActiveIngredient(this.getConcentrationActiveIngredient())
                    .registerNumber(this.getRegisterNumber())
                    .toxiClass(this.getToxiClass())
                    .build();

            productService.update(updatedProduct);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Produto alterado!"));

            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um produto com esse nome! Use um nome diferente."));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Produto não pode ser alterado porque não foi encontrada na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }

    public List<Target> readAllTargets() {
        return productService.readAllTargets();
    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        productService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Produto";
    }
}
