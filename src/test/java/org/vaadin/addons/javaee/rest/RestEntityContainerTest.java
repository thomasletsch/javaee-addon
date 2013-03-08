package org.vaadin.addons.javaee.rest;

import static com.github.restdriver.clientdriver.RestClientDriver.*;
import static org.junit.Assert.*;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.vaadin.addons.javaee.container.EntityItem;
import org.vaadin.addons.javaee.jpa.TestEntity;

import com.github.restdriver.clientdriver.ClientDriverRule;

@Ignore
public class RestEntityContainerTest {

    @Rule
    public ClientDriverRule driver = new ClientDriverRule();

    private RestEntityContainer<TestEntity> container;

    private TestEntity entity3;

    private TestEntity entity2;

    private TestEntity entity1;

    private String mediaType = MediaType.APPLICATION_XML;

    @Test
    public void testFindAllEntities() throws Exception {
        List<TestEntity> entities = Arrays.asList(entity1, entity2, entity3);
        Lists<TestEntity> lists = new Lists<>(entities);
        String content = marshal(lists);
        driver.addExpectation(onRequestTo("/testEntity"), giveResponse(content, mediaType).withStatus(200));
        Collection<?> itemIds = container.getItemIds();
        assertNotNull(itemIds);
    }

    @Test
    public void testGetSubContainer() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateItem() {
        fail("Not yet implemented");
    }

    @Test
    public void testRefreshItem() {
        fail("Not yet implemented");
    }

    @Test
    public void testLoadItemWithRelations() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetItemLong() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetItemObject() throws Exception {
        String content = marshal(entity1);
        driver.addExpectation(onRequestTo("/testEntity/1"), giveResponse(content, mediaType).withStatus(200));
        EntityItem<TestEntity> item = container.getItem(entity1.getId());
        assertNotNull(item);
    }

    @Test
    public void testGetItemIds() {
        fail("Not yet implemented");
    }

    @Test
    public void testSize() {
        fail("Not yet implemented");
    }

    @Test
    public void testContainsId() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveItem() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveAllItems() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddItem() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddItemENTITY() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetCollectionType() {
        fail("Not yet implemented");
    }

    @Before
    public void createContainer() {
        container = new RestEntityContainer<>(driver.getBaseUrl(), TestEntity.class, "testEntity");
    }

    @Before
    public void createEntities() {
        entity1 = new TestEntity(1L, TestEntity.ORIGINAL_TEST_STRING);
        entity2 = new TestEntity(2L, TestEntity.UPDATED_TEST_STRING);
        entity3 = new TestEntity(3L, TestEntity.ORIGINAL_TEST_STRING);
    }

    protected String marshal(Object object) throws Exception, JAXBException {
        Marshaller jaxbMarshaller = getMarshaller();
        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(object, writer);
        String content = writer.toString();
        return content;
    }

    protected Marshaller getMarshaller() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(TestEntity.class, Lists.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        return jaxbMarshaller;
    }

}
