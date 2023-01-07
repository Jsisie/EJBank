# EJBank

## Required

- Maven
- Docker

## Build

``mvn clean install``

## Execute

*Mac/Linux :*
``sudo docker-compose up
``

*Windows :*
``docker-compose -f docker-compose.windows.yml up --build
``



## Implementation Choices

### Nombre de transactions non affiché pour les Customers

Nous avons décidé de cacher pour les Customers le nombre de transactions en attente, car ce nombre est affiché sur la page "Comptes externes", pages accessible pour les Advisors uniquement. De plus, nous avons compris que ce nombre servait d'informations pour les Advisors afin qu'ils sachent combien de transactions en attente il lui reste à régler. Nous avons ainsi décidé qu'il était plus logique de ne montrer ce nombre que pour les Advisors.

### Transaction supprimée si non validé par un Advisor

Il nous a semblé plus logique que si une transaction est annulé par un Advisor, celle-ci est supprimée car avec l'application front qui nous est fournie, l'opération serait restée en attente pour toujours (ou alors elle aurait été validée ce qui semble encore moins logique). Le seul soucis avec cette solution est que le Customer perd toute trace de sa transaction, dans une application plus complète on aurait pu imaginer un système de notification, mais dans l'état actuel, nous avons décidé qu'il était plus logique de supprimer cette transaction pour ne pas la laisser en attente indéfiniment. 

### Eager et Lazy

Pour rappel, Eager signifie que toutes les données vont être chargées à la création de l'objet. Cette méthode est rapide et efficace mais très consommatrice en mémoire notamment. Lazy quant à lui signifie que les données ne vont être chargées que lorsqu'elles seront demandées. Nous avons décidé d'implémenter un chargement Lazy par défaut pour toutes les relations ManyToOne/OneToMany, et un chargement Eager pour les 2 relations ci-dessous :

- relation ManyToOne de AccountEntity vers CustomerEntity, pour le champ "customer_id",
- relation ManyToOne de AccountEntity vers AccountTypeEntity, pour le champ "account_type_id",
