## Ajouter un point à une demande de livraison

### Préconditions

- Un plan est chargé
- Une demande de livraison est chargée
- Une tournée a été calculée

### Scenario

1. L'utilisateur demande à ajouter un point après une adresse de livraison.
2. L'utilisateur sélectionne l'intersection sur le plan de la nouvelle adresse de livraison.
3. Le système demande à l'utilisateur la durée de livraison.
4. L'utlisateur renseigne la durée de livraison.
5. Le système demande à l'utilisateur les contraintes horaires.
6. L'utilisateur renseigne les contraintes horaires.
7. L'utilisateur valide la création.
8. Le système crée la nouvelle adresse de livraison, et modifie la tournée en correspondance. 
Il met notamment à jour les adresses de livraisons suivantes si des contraintes horaires ne sont plus respectées.


### Alternatives

2a. L'utilisateur sélectionne une intersection qui est déjà une adresse de livraison -> La demande de livraison est annulée.
4a. L'utilisateur renseigne une durée négative -> Le système redemande une durée à l'utilisateur.
6a. L'utilisateur renseigne des contraintes horaires invalides (heure de fin avant heure de début, heure de fin trop peu de temps après heure de début pour pouvoir livrer, heure de fin sans heure de début, heure de début sans heure de fin) -> Le système redemande des contraintes horaires à l'utilisateur.
6b. L'utilisateur ne renseigne pas de contraintes horaires et valide directement -> Le système crée une nouvelle adresse de livraison sans contraintes horaires.
2b-4b-6c L'utilisateur annule la création de la nouvelle adresse de livraison -> Le système retourne dans l'état avant la demande utilisateur 1.
