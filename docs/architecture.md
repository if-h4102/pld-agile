# Architecture

Notre application est partagée en une partie modèle et services indépendante
de l'interface graphique et de la partie interface graphique.
Pour la partie interface graphique, nous avons utilisé une approche par
composant (avec JavaFX) avec un contrôleur et vue par noeud / élément de notre
application. L'application peut être vue comme un document avec une
arborescence de noeuds.

## Organisation de l'IHM

Voici une vue simplifiée de la hiérarchie de notre IHM:

- racine
  - barre d'outils
    - bouton "Load CityMap"
    - bouton "Load DeliveryRequest"
    - ...
  - zone centrale
    - carte
    - info-bulle (détails sur un noeud de la carte)
  - panneau latéral
    - Détails de la demande de livraison
      - Entrepôt
      - Point de passage 1  
      - Point de passage 2
      - ...
    - Détails du planning
      - Etape 1
        - Horaires
          - Heure de départ
          - Heure d'arrivée
          - Heure de début de livraison (après attente éventuelle)
        - Point de passage
          - titre
          - coordonnées
          - Bouton "supprimer"
      - Etape 2
        - Bouton "ajouter" (insérer un nouveau point de passage juste avant)
        - (idem)
      - ...
      - formulaire d'ajout de point de passage
        - durée de livraison
        - contraintes
          - heure début
          - heure fin
 
On remarque qu'on a de nombreux éléments de types assez variés.
Pour chaque noeuds de cette arborescence, on a un controleur et une vue qui
est responsable de gérer les données du modèle relatives à son sous-arbre.
Cette architecture nous permet de travailer sur les différents noeuds de
manière assez indépendante à condition de garder l'interface avec le noeud
parent identique.


La communication entre composants se fait de 3 manières:
- Un noeud parent peut fournir des données à un noeud enfant (cas classique
  pour l'affichage de données: le parent est un objet complexe et il passe un
  des attributs à un composant dédié à son affiche (ex: horaires))
- Un noeud enfant peut émettre un événement pour notifier ses ancêtres.
  On l'utilise quand un événement est créée en profondeur mais il faut un
  contexte plus important pour le traiter (exemple: ajout de point de passage).
- Deux composants sur des sous-arbres séparés peuvent communiquer par un
  service partagé qu'ils ont tous les deux reçu de leur ancêtre commun
  (exemple: cliquer sur un point de passage dans le planning sélectionne un
  noeud dans la zone centrale)
Les données sont toutes observables donc une mise à jour à la racine entraine
automatiquement une mise à jour de tous les éléments dépendants.

Chaque composant peut avoir plusieurs états mais ils sont généralement assez
simples et peuvent être géré par des attributs booléens (focus d'un champ,
test d'une valeur, ...) mais pour les composants plus complexes, on utilise
un pattern state. Nous l'avons utilisé deux fois dans notre application:
pour les détails du planning (affichage, attente d'ajout, formulaire d'ajout)
et pour la racine (cf diagramme d'état-transition UML).

De plus, pour gérer efficacement les undo/redo des modifications apportées par
l'utilisateur sur la tournée calculée, nous utilisons le design pattern 
commande.

Certains composants réutilises des services pour faire des tâches spécifiques.
On les as isolé dans des packages séparés (résolution du tsp, rendu du PDF du
planning, parser).


