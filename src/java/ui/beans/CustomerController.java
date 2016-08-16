package ui.beans;

import ui.beans.util.MobilePageController;
import mauro.entity.Customer;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import mauro.facade.CustomerFacade;

@Named(value = "customerController")
@ViewScoped
public class CustomerController extends AbstractController<Customer> {

    @Inject
    private DiscountCodeController discountCodeController;
    @Inject
    private MicroMarketController zipController;
    @Inject
    private MobilePageController mobilePageController;

    public CustomerController() {
        // Inform the Abstract parent controller of the concrete Customer Entity
        super(Customer.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        discountCodeController.setSelected(null);
        zipController.setSelected(null);
    }

    /**
     * Sets the "items" attribute with a collection of PurchaseOrder entities
     * that are retrieved from Customer?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for PurchaseOrder page
     */
    
    
    /**modified by mauro */
    
    public String navigatePurchaseOrderCollection() {
        if (this.getSelected() != null) {
            
          /**modified by mauro 
           re- attach the Cusotmer selected to persisteneew context for get the PurchaseOrderCollection loaded from db .
           It use the method added to CustomerFacade named: attach_and_load_PurchaseOrderCollection(Customer customer) that return the Custoemr wuth purchaseOderCOllection loaded.
           
           */   
            Customer attached=this.getCustomerFacade().attach_and_load_PurchaseOrderCollection(this.getSelected());
     this.setSelected(attached);
            
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("PurchaseOrder_items", this.getSelected().getPurchaseOrderCollection());
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/entity/purchaseOrder/index";
    }

    /**
     * Sets the "selected" attribute of the DiscountCode controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareDiscountCode(ActionEvent event) {
        if (this.getSelected() != null && discountCodeController.getSelected() == null) {
            discountCodeController.setSelected(this.getSelected().getDiscountCode());
        }
    }

    /**
     * Sets the "selected" attribute of the MicroMarket controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareZip(ActionEvent event) {
        if (this.getSelected() != null && zipController.getSelected() == null) {
            zipController.setSelected(this.getSelected().getZip());
        }
    }
    public CustomerFacade getCustomerFacade(){
    
   return  (CustomerFacade) super.getEjbFacade();}
}
