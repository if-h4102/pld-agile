package models;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class DeliveryRequestTest {

    @Test
    public void addIntersectionTest() {

    }

    @Test
    public void equalsTest() {
        Warehouse baseWarehouse = new Warehouse(new Intersection(0, 24, 53));
        List<DeliveryAddress> baseDeliveryAddresses = new LinkedList<DeliveryAddress>();
        baseDeliveryAddresses.add(new DeliveryAddress(new Intersection(1, 42, 26), 150));
        baseDeliveryAddresses.add(new DeliveryAddress(new Intersection(2, 41, 25), 140));
        baseDeliveryAddresses.add(new DeliveryAddress(new Intersection(3, 40, 24), 130));
        DeliveryRequest base = new DeliveryRequest(null, baseWarehouse, baseDeliveryAddresses, 36000);

        Warehouse sameAsBaseW = new Warehouse(new Intersection(0, 24, 53));
        List<DeliveryAddress> sameAsBaseDA = new LinkedList<DeliveryAddress>();
        sameAsBaseDA.add(new DeliveryAddress(new Intersection(1, 42, 26), 150));
        sameAsBaseDA.add(new DeliveryAddress(new Intersection(2, 41, 25), 140));
        sameAsBaseDA.add(new DeliveryAddress(new Intersection(3, 40, 24), 130));
        DeliveryRequest sameAsBase = new DeliveryRequest(null, sameAsBaseW, sameAsBaseDA, 36000);

        Warehouse modifiedWarehouseW = new Warehouse(new Intersection(4, 24, 53)); // this warehouse has been modified from base
        List<DeliveryAddress> modifiedWarehouseDA = new LinkedList<DeliveryAddress>();
        modifiedWarehouseDA.add(new DeliveryAddress(new Intersection(1, 42, 26), 150));
        modifiedWarehouseDA.add(new DeliveryAddress(new Intersection(2, 41, 25), 140));
        modifiedWarehouseDA.add(new DeliveryAddress(new Intersection(3, 40, 24), 130));
        DeliveryRequest modifiedWarehouse = new DeliveryRequest(null, modifiedWarehouseW, modifiedWarehouseDA, 36000);

        Warehouse modifiedDeliveryAddressesW = new Warehouse(new Intersection(0, 24, 53));
        List<DeliveryAddress> modifiedDeliveryAddressesDA = new LinkedList<DeliveryAddress>();
        modifiedDeliveryAddressesDA.add(new DeliveryAddress(new Intersection(4, 42, 26), 150)); // This DA has been modified from base
        modifiedDeliveryAddressesDA.add(new DeliveryAddress(new Intersection(2, 41, 25), 140));
        modifiedDeliveryAddressesDA.add(new DeliveryAddress(new Intersection(3, 40, 24), 130));
        DeliveryRequest modifiedDeliveryAddresses = new DeliveryRequest(null, modifiedDeliveryAddressesW, modifiedDeliveryAddressesDA, 36000);

        Warehouse moreDeliveryAddressesW = new Warehouse(new Intersection(0, 24, 53));
        List<DeliveryAddress> moreDeliveryAddressesDA = new LinkedList<DeliveryAddress>();
        moreDeliveryAddressesDA.add(new DeliveryAddress(new Intersection(1, 42, 26), 150));
        moreDeliveryAddressesDA.add(new DeliveryAddress(new Intersection(2, 41, 25), 140));
        moreDeliveryAddressesDA.add(new DeliveryAddress(new Intersection(3, 40, 24), 130));
        moreDeliveryAddressesDA.add(new DeliveryAddress(new Intersection(4, 39, 23), 130)); // This DA has been added from base
        DeliveryRequest moreDeliveryAddresses = new DeliveryRequest(null, moreDeliveryAddressesW, moreDeliveryAddressesDA, 36000);

        Warehouse modifiedStartTimeW = new Warehouse(new Intersection(0, 24, 53));
        List<DeliveryAddress> modifiedStartTimeDA = new LinkedList<DeliveryAddress>();
        modifiedStartTimeDA.add(new DeliveryAddress(new Intersection(1, 42, 26), 150));
        modifiedStartTimeDA.add(new DeliveryAddress(new Intersection(2, 41, 25), 140));
        modifiedStartTimeDA.add(new DeliveryAddress(new Intersection(3, 40, 24), 130));
        // The start time has been modified
        DeliveryRequest modifiedStartTime = new DeliveryRequest(null, modifiedStartTimeW, modifiedStartTimeDA, 35000);

        assertTrue(base.equals(base));
        assertTrue(base.equals(sameAsBase));
        assertTrue(!base.equals(modifiedWarehouse));
        assertTrue(!base.equals(modifiedDeliveryAddresses));
        assertTrue(!base.equals(moreDeliveryAddresses));
        assertTrue(!base.equals(modifiedStartTime));
    }
}
