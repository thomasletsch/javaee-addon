The JavaEE Vaadin Addon helps you to build a JavaEE 6 based Vaadin Application fast.

It supports the following features:
- Root Application is injected by CDI and therefore CDI is available in all custom classes
- Fixed standard layout of a web application with left side menu
- Navigation through pages is done by sending CDI events
- CRUD support for JPA entities:
	- Create a container by just subclassing EntityContainer
	- Create a Table for all entities by just subclassing BasicEntityTable (you can set the visible columns you like)
	- Create a search Form by just subclassing BasicSearchForm and set the columns you want to include in the search
	- Create a edit form by just subsclasses BasicEditForm and set the columns you want to edit
	- Dependent entities are just accessed through their path (e.g. for the customers street "address.street")
- Extended formatting
- Extended Field creation

Tested and used with Hibernate 4 in JBoss 7.1.

The JavaEE Addon is compatible to Vaadin 7 Beta 9! No stable version!