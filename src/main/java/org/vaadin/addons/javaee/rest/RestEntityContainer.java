/*******************************************************************************
 * Copyright 2012 Thomas Letsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
package org.vaadin.addons.javaee.rest;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.container.AbstractEntityContainer;
import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.container.EntityItem;
import org.vaadin.addons.javaee.jpa.JPAEntityProvider;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Item;
import com.vaadin.ui.UI;

public class RestEntityContainer<ENTITY extends PersistentEntity> extends AbstractEntityContainer<ENTITY> {

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(RestEntityContainer.class);

    /**
     * For getCollectionType
     */
    @EJB
    private JPAEntityProvider jpaEntityProvider;

    private String resourcePath;

    private URI restURI;

    private URI baseRestURI;

    public RestEntityContainer(Class<ENTITY> entityClass, String resourcePath) {
        this.entityClass = entityClass;
        this.resourcePath = resourcePath;
        URI location = UI.getCurrent().getPage().getLocation();
        baseRestURI = location.resolve("/facade/rest");
        restURI = baseRestURI.resolve("/facade/rest/" + resourcePath);
        initProperties(entityClass);
    }

    public RestEntityContainer(String baseRestURI, Class<ENTITY> entityClass, String resourcePath) {
        this.entityClass = entityClass;
        try {
            this.baseRestURI = new URI(baseRestURI);
        } catch (URISyntaxException e) {
            log.error("Could not parse " + baseRestURI, e);
            throw new RuntimeException(e);
        }
        this.resourcePath = resourcePath;
        restURI = this.baseRestURI.resolve("/" + resourcePath);
        initProperties(entityClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <SUB_ENTITY extends PersistentEntity> EntityContainer<SUB_ENTITY> getSubContainer(String propertyId) {
        Class<SUB_ENTITY> entityClass = (Class<SUB_ENTITY>) getType(propertyId);
        return new RestEntityContainer<>(entityClass, resourcePath + "/" + propertyId); // TODO path should contain id
    }

    @Override
    public EntityItem<ENTITY> getItem(Long itemId) {
        ClientRequest request = createRequest(itemId);
        try {
            ClientResponse<ENTITY> clientResponse = request.get(entityClass, entityClass);
            ENTITY entity = clientResponse.getEntity();
            EntityItem<ENTITY> item = new EntityItem<ENTITY>(this, entity);
            return item;
        } catch (Exception e) {
            log.error("Could not GET " + itemId, e);
        }

        return null;
    }

    @Override
    public Item getItem(Object itemId) {
        return getItem((Long) itemId);
    }

    @Override
    public void updateItem(EntityItem<ENTITY> item) {
        assert (item.getEntity().getId() != null);
        ClientRequest request = createRequest(item.getEntity().getId());
        try {
            request.body(MediaType.APPLICATION_XML, item.getEntity()).put(entityClass);
        } catch (Exception e) {
            log.error("Could not PUT " + item.getEntity(), e);
        }
    }

    @Override
    public void refreshItem(EntityItem<ENTITY> item) {
        assert (item.getEntity().getId() != null);
        ClientRequest request = createRequest(item.getEntity().getId());
        try {
            ENTITY entity = request.get(entityClass).getEntity();
            item.setEntity(entity);
        } catch (Exception e) {
            log.error("Could not GET " + item.getEntity(), e);
        }
    }

    @Override
    public void loadItemWithRelations(EntityItem<ENTITY> item) {
        refreshItem(item);
    }

    @Override
    public Collection<?> getItemIds() {
        if (!needProcess()) {
            return Collections.EMPTY_LIST;
        }
        List<ENTITY> entitys = findAllEntities();
        List<Long> ids = new ArrayList<Long>(entitys.size());
        for (ENTITY entity : entitys) {
            ids.add(entity.getId());
        }
        return ids;
    }

    @Override
    public int size() {
        if (!needProcess()) {
            return 0;
        }
        List<ENTITY> entitys = findAllEntities();
        int size = entitys.size();
        return size;
    }

    @Override
    public boolean containsId(Object itemId) {
        List<ENTITY> entitys = findAllEntities();
        for (ENTITY entity : entitys) {
            if (itemId.equals(entity.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeItem(Object itemId) throws UnsupportedOperationException {
        ClientRequest request = createRequest((Long) itemId);
        try {
            ClientResponse<?> response = request.delete();
            notifyItemSetChanged();
            return Response.Status.OK.equals(response.getResponseStatus());
        } catch (Exception e) {
            log.error("Could not DELETE " + itemId, e);
        }
        return false;
    }

    @Override
    public boolean removeAllItems() throws UnsupportedOperationException {
        ClientRequest request = createRequest();
        try {
            ClientResponse<?> response = request.delete();
            notifyItemSetChanged();
            return Response.Status.OK.equals(response.getResponseStatus());
        } catch (Exception e) {
            log.error("Could not DELETE ", e);
        }
        return false;
    }

    @Override
    public Object addItem() throws UnsupportedOperationException {
        ClientRequest request = createRequest();
        try {
            ClientResponse<ENTITY> response = request.body(MediaType.APPLICATION_XML, entityClass.newInstance()).post(entityClass);
            notifyItemSetChanged();
            return response.getEntity().getId();
        } catch (Exception e) {
            log.error("Could not POST ", e);
        }
        return null;
    }

    @Override
    public EntityItem<ENTITY> addItem(ENTITY entity) {
        ClientRequest request = createRequest();
        try {
            ClientResponse<ENTITY> response = request.body(MediaType.APPLICATION_XML, entity).post(entityClass);
            notifyItemSetChanged();
            return new EntityItem<ENTITY>(this, response.getEntity());
        } catch (Exception e) {
            log.error("Could not POST ", e);
        }
        return null;
    }

    @Override
    public Class<?> getCollectionType(String propertyId) {
        // TODO create non - JPA alternative
        return jpaEntityProvider.getType(entityClass, propertyId);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<ENTITY> findAllEntities() {
        ClientRequest request = createRequest();
        Type genericType = (new GenericType<List<ENTITY>>() {
        }).getGenericType();
        try {
            ClientResponse<List> response = request.get(List.class, genericType);
            return response.getEntity();
        } catch (Exception e) {
            log.error("Could not GET ", e);
        }
        return null;
    }

    private ClientRequest createRequest(Long id) {
        ClientRequest request = new ClientRequest(restURI.toString() + "/" + id.toString());
        return request;
    }

    private ClientRequest createRequest() {
        ClientRequest request = new ClientRequest(restURI.toString());
        return request;
    }
}