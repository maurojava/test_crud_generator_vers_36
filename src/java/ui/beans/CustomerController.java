package ui.beans;

import java.util.Collection;
import ui.beans.util.MobilePageController;
import mauro.entity.Customer;
import mauro.entity.PurchaseOrder;
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
    /**
     * modified by mauro/kay
     */
    public String navigatePurchaseOrderCollection() {
        if (this.getSelected() != null) {
            // Re-attach entity to persistence context
            //Collection<PurchaseOrder> purchaseOrderCollection = this.ejbFacade.attach(this.getSelected()).getPurchaseOrderCollection();
            Collection<PurchaseOrder> purchaseOrderCollection = ((CustomerFacade) this.getEjbFacade()).getPurchaseOrders(this.getSelected());
            if (purchaseOrderCollection != null) {
                FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("PurchaseOrder_items", purchaseOrderCollection);
            }
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/entity/purchaseOrder/index";
    }

    /**
     * Checks if customer has purchaseOrder entities
     * @return
     */
    public boolean isPurchaseOrderCollectionEmpty() {
        if (this.getSelected() != null) {
            // Re-attach entity to persistence context
            //Collection<PurchaseOrder> purchaseOrderCollection = this.ejbFacade.attach(this.getSelected()).getPurchaseOrderCollection();
            Collection<PurchaseOrder> purchaseOrderCollection = ((CustomerFacade) this.getEjbFacade()).getPurchaseOrders(this.getSelected());
            if (purchaseOrderCollection != null) {
                return purchaseOrderCollection.isEmpty();
            }
        }
        return true;
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

}
