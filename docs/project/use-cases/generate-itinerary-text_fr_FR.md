## Générer la feuille de route

### Préconditions

- Un plan est chargé
- Une demande de livraison est chargée
- Une tournée a été calculée

### Scenario

1. L'utilisateur demande à générer la feuille de route correspondant au planning.
2. Le système demande à l'utilisateur l'emplacement et le nom du fichier à créer.
3. L'utilisateur renseigne un chemin de fichier.
4. Le système crée la feuille de route au chemin renseigné par l'utilisateur.

### Alternatives
3a. L'utilisateur annule l'opération -> Le système revient à l'état initial.
