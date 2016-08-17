package ui.beans;

import java.lang.reflect.Method;
import java.util.Collection;
import ui.beans.util.MobilePageController;
import mauro.entity.Customer;
import mauro.entity.PurchaseOrder;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "customerController")
@ViewScoped
public class CustomerController extends AbstractController<Customer> {

    @Inject
    private DiscountCodeController discountCodeController;
    @Inject
    private MicroMarketController zipController;
    @Inject
    private MobilePageController mobilePageController;

    // Helper variables for OneToMany collections
    private final Method getPurchaseOrderCollectionMethod;
    private Collection<PurchaseOrder> purchaseOrderCollection;

    public CustomerController() throws NoSuchMethodException {
        // Inform the Abstract parent controller of the concrete Customer Entity
        super(Customer.class);
        this.getPurchaseOrderCollectionMethod = Customer.class.getMethod("getPurchaseOrderCollection", (Class<?>[]) null);
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
    public String navigatePurchaseOrderCollection() {
        if (this.getSelected() != null && this.purchaseOrderCollection != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("PurchaseOrder_items", this.purchaseOrderCollection);
            return this.mobilePageController.getMobilePagesPrefix() + "/entity/purchaseOrder/index";
        } else {
            return null;
        }
    }

    /**
     * Checks if customer has purchaseOrder entities
     *
     * @return
     */
    public boolean isPurchaseOrderCollectionEmpty() {
        if (this.getSelected() != null) {
            // Re-attach entity to persistence context
            //Collection<PurchaseOrder> purchaseOrderCollection = this.ejbFacade.attach(this.getSelected()).getPurchaseOrderCollection();
            this.purchaseOrderCollection = (Collection<PurchaseOrder>) this.getEjbFacade().getChildren(this.getSelected(), getPurchaseOrderCollectionMethod);
            if (this.purchaseOrderCollection != null) {
                return this.purchaseOrderCollection.isEmpty();
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
