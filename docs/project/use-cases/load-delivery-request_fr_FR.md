## Ouvrir une demande de livraison

### Préconditions

- Un plan est chargé

### Scenario

1. L'utilisateur demande l'ouverture d'une demande de livraison.
2. Le système propose à l'utilisateur de choisir le fichier décrivant la demande de livraison.
3. Le système charge le plan et affiche ses données: entrepôt, adresses de livraison
   et contraintes de livraison (horaires de passage).

### Alternatives

- Le fichier est invalide, une erreur a lieu au chargement
  - Annuler le chargement et afficher une erreur
