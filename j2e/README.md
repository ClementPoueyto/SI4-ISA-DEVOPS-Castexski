## Clone this repo

    git clone --recurse-submodules

## Update this repo (git pull)


    git pull
    git submodule update --init --recursive (si c'est la première fois que vous pullez avec les modules)
    git submodule update --recursive (update tous les modules si déjà présents)
    
(Pas sûre que ça fonctionne, updatez le README selon le comportement)

## Update only one submodule 
For example, a submodule got an update. You should be in the submodule directory (in the parent repo) and then : 

    git fetch

    git merge


> Source [Les Sous-Module | Github](https://git-scm.com/book/fr/v2/Utilitaires-Git-Sous-modules)
