# Bienvenue dans les scenarios de la team C
Bienvenue dans notre projet Castexski. Vous trouverez dans ce fichier les différents scénarios avec leur explication et comment les exécuter. git clone --recurse-submodules

Pour le bon fonctionnement des scénarios, ceux-ci doivent s'exécuter dans l'ordre décrit ci-dessous.

Pour la suite, on suppose que le script "run.sh" et "client.sh" ont bien été exécutés. Les commandes suivantes sont à copier/coller dans la CLI du client.

## Scenarios

### Initialisation
Pour un soucis d'efficacité, nous conseillons de d'abord lancer une ligne dans le CLI qui permet de vérifier le bon fonctionnement des statistiques.
Les commandes à exécuter sont :

 - [ ] On note les statistiques en début de journée :
**Attention** ! Il faut remplacer par la date du jour pour pouvoir observer les résultats adéquats.
```
pass-analytics 09 05 2021  // Remplacer avec la date du jour !
visit-analytics 09 05 2021
```

 - [ ] On doit ensuite initialiser la station, comme le ferait un employé :
```
add-zone snow_park
add-skilift les_menez snow_park
add-gate LM1 les_menez
```
Dans les lignes au dessus, on ajoute :
- une zone skiable (snow_park)
- une remontée mécanique (les_menez)
- Et un portique de passage à cette remontée (LM1).

### Scénario 1 : 
Notre premier scenario est celui d'un client qui crée son compte, puis ajoute une carte et un forfait à son panier et qui paye. 

```
register fabien@gmail.com Fabien 896983
add-card fabien@gmail.com 1 cartex
checkout fabien@gmail.com
// Une ligne s'affichera ici, indiquant l'ID de la carte. 
// Si ce numéro est différent de 201, il faudra le remplacer dans la ligne suivante
add-pass fabien@gmail.com 1 classic 201 full_day 30 2021-05-09 adult snow_park
checkout fabien@gmail.com
```
Dans l'ordre : 
- le client s'enregistre dans le système avec une adresse mail, son nom, et son numéro de carte bleue
- le client ajoute une carte à son panier : une seule de type cartex, donc classique.
- le client valide son panier et paye
- le client ajoute un forfait adulte à son panier, lié à sa carte (201, ou autre ID renvoyé précédemment par le back), de type classique, valide pendant 30 jours complets, et qui débute le 07/05/2021 valable uniquement dans les remontées mécaniques de la zone snow_park
- Et le client revalide son panier

### Scénario 2 :
Le deuxième scénario consiste à passer la carte crée précédemment à la remontée mécanique. Ce scénario prend en compte que les étapes précédentes ont été correctement exécutées. Si ce n'est pas le cas, il suffit de relancer le script run.sh et recommencer les étapes.
**Attention** ! Bien mettre l'ID de la carte renvoyé précédemment.
```
passage 201 LM1
```
Le résultat de cette requête est soit :
- Invalid passage : Zone closed : dans ce cas, c'est que la météo indique qu'il y a une avalanche, et la zone est donc fermée. 
- Passage validated : dans ce cas là, la zone est ouverte et le passage a bien été validé !

### Scénario 3 :
Le troisième scénario consiste à vérifier que les statistiques se soient bien incrémentées. Ainsi, il suffit de taper les commandes :
**Attention** ! Bien mettre la date du jour.
```
pass-analytics 09 05 2021
visit-analytics 09 05 2021
```
Et dans ce cas là, on devrait voir le nombre de cartes (CLASSIC) vendues et le nombre de passages à chaque remontée mécanique (ici il n'y en a qu'une "les_menez", mais libre à vous d'en rajouter !)


### Merci de votre attention ! 
