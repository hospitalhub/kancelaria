package pl.kalisz.szpital.kancelaria.data.db;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Iterator;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.google.gwt.thirdparty.guava.common.collect.Multimap;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.NormalUIScoped;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.Notification;

import pl.kalisz.szpital.kancelaria.data.pojo.Address;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;

/**
 * The Class AddressMerger.
 */
@SuppressWarnings("serial")
@NormalUIScoped
public class AddressMerger implements Serializable {

  /** The Constant logger. */
  static final Logger LOGGER = Logger.getLogger(ContainerFactory.class.toString());

  @Inject
  ContainerFactory dbHelper;

  /**
   * Merge adresy.
   * 
   * @param id1 the id1
   * @param id2 the id
   * @return the int
   */
  public int mergeAdresy(final int id1, final int id2) {
    LOGGER.info("mergeuje " + id1 + " do " + id2);
    Connection conn = null;
    int updatedRows = 0;
    // get containers
    JPAContainer<Transaction> c = dbHelper.getContainer(ContainerFactory.TRANSACTION_CONTAINER);
    JPAContainer<Address> ca = dbHelper.getContainer(ContainerFactory.ADDRESS_CONTAINER);
    // get address (id1)
    ca.removeAllContainerFilters();
    ca.addContainerFilter(new Compare.Equal(Address.ID, id1));
    Iterator i1 = ca.getItemIds().iterator();
    if (!i1.hasNext()) {
      return 0;
    }
    Object adresId1 = i1.next();
    Address a1 = ca.getItem(adresId1).getEntity();
    // get address (id2)
    ca.removeAllContainerFilters();
    ca.addContainerFilter(new Compare.Equal(Address.ID, id2));
    Iterator i2 = ca.getItemIds().iterator();
    if (!i2.hasNext()) {
      return 0;
    }
    Object adresId2 = i2.next();
    Address a2 = ca.getItem(adresId2).getEntity();
    // move transactions.adres__id1 => transaction.adres__id2
    c.removeAllContainerFilters();
    c.addContainerFilter(new Compare.Equal(Transaction.ADDRESS, a2));
    for (Object itemid : c.getItemIds()) {
      Transaction i = c.getItem(itemid).getEntity();
      i.setAdres(a1);
      c.addEntity(i);
      updatedRows++;
      c.commit();
    }
    c.removeAllContainerFilters();
    ca.removeAllContainerFilters();
    return updatedRows;
  }


  /**
   * Polacz adresy.
   * 
   * @param multimap the multimap
   * @return the int
   */
  public int polaczAdresy(final Multimap<Address, Integer> multimap,
      JPAContainer<Address> adresContainer) {
    int adresowPolaczonych = 0;
    for (Address a : multimap.keySet()) {
      Integer[] ids = multimap.get(a).toArray(new Integer[] {});
      if (ids.length > 1) {
        adresowPolaczonych++;
        int id1 = ids[0];
        int num = 0;
        int numRemoved = 1;
        for (int id2 : ids) {
          if (id1 != id2) {
            num += mergeAdresy(id1, id2);
            boolean ok = adresContainer.removeItem(id2);
            if (ok) {
              numRemoved++;
            }
          }
        }
        try {
          adresContainer.commit();
        } catch (UnsupportedOperationException e) {
          e.printStackTrace();
        }
        adresContainer.refresh();
        Notification.show("Przeniesiono " + num + " dokumentów na jeden adres i połączono "
            + numRemoved + " adresow", a.toString(), Notification.Type.TRAY_NOTIFICATION);
      }
    }
    return adresowPolaczonych;
  }

}
