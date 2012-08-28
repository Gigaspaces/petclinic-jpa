package com.gigaspaces.petclinic.processor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openspaces.core.GigaSpace;
import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.Owner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

/**
 * Integration test for the Processor. Uses similar xml definition file (ProcessorIntegrationTest-context.xml)
 * to the actual pu.xml. Writs an unprocessed Data to the Space, and verifies that it has been processed by
 * taking a processed one from the space.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/pu.xml"})
public class ClinicTests {

    @Resource
    private GigaSpace gigaSpace;

    @Resource
    private Clinic clinic;

    @Before
    @After
    public void clearSpace() {
        gigaSpace.clear(null);
    }

    @Test
    public void testStoreOwner() {
        Owner owner = new Owner();
        owner.setAddress("test address");
        owner.setCity("test city");
        owner.setTelephone("234556");
        owner.setFirstName("Uri");
        owner.setLastName("Cohen");
        clinic.storeOwner(owner);
        assertNotNull(owner.getId());



    }
}
