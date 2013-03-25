The JavaEE Addon for Vaadin helps you to build a JavaEE 6 based Vaadin Application fast.

It supports the following features:
- CDI support through CDI-Utils (https://vaadin.com/directory#addon/cdi-utils:vaadin)
- Fixed standard layout of a web application with left side menu
- Navigation through pages is done by sending CDI events
- CRUD support for JPA entities (through JPA or other services):
	- Create a container by just subclassing JPAEntityContainer or ServiceContainer
	- Create a Table for all entities by just subclassing BasicEntityTable (you can set the visible columns you like)
	- Create a search Form by just subclassing BasicSearchForm and set the columns you want to include in the search
	- Create a edit form by just subsclasses BasicEditForm and set the columns you want to edit
	- Dependent entities are just accessed through their path (e.g. for the customers street "address.street")
- Extended formatting
- Extended Field creation (automatic field types derived from attribute types)

Tested and used with Hibernate 4 in JBoss 7.1. For an example on how to use it, have a look at https://github.com/thomasletsch/javaee-addon-sample.

The JavaEE Addon is compatible to Vaadin 7!