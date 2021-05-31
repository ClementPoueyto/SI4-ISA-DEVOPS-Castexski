## Start Client
    export TEAMC_IP="localhost"
    mvn clean package
    mvn exec:java


## Construction d'une station
1. Ajouter des zones :
   _add-zone [NOM]_


    add-zone debuttant
    add-zone bas_station
    add-zone haut_station
    add-zone liaison

2. Ajouter des remontées mécaniques :
   _add-skilift [NOM] [NOM_ZONE_CORRESPONDANTE]_


    add-skilift tapis debuttant
    add-skilift marmottes bas_station
    add-skilift lievre bas_station
    add-skilift signal haut_station
    add-skilift observatoire liaison

3. Ajouter des portiques :
   _add-gate [ID] [NOM_SKILIFT_RATACHE]_


    add-gate T1 tapis
    add-gate M1 marmottes
    add-gate M2 marmottes
    add-gate M3 marmottes
    add-gate L1 lievre
    add-gate L2 lievre
    add-gate S1 signal
    add-gate Ls1 liaison
    add-gate Ls2 liaison
    

## Enregistrement

   ```register [MAIL] [NOM] [CREDIT_CARD_NB]```


    register denis@me.com Denis 896983

## Achat de carte

   ```add-card [MAIL] [QTE]```


    add-card denis@me.com 1
    checkout denis@me.com


## Achat de forfait

```add-pass [CUSTOMER_MAIL] [QUANTITY] [PASS_TYPE] [CARD_LINK_ID] [morning|afternoon|full_day] [NB_DAY] [START_DATE yyyy-mm-dd] [IS_CHILD] [zones_id]```

    add-pass denis@me.com 1 classic 201 morning 1 2021-04-02 true debuttant
    add-pass denis@me.com 1 classic 201 full_day 2 2021-04-02 false bas_station,haut_station
    checkout denis@me.com

## Passage à un portique

```passage [CARD_ID] [GATE_ID]```

    passage 201 M1

## Version Fast 
    add-zone bas_station
    add-skilift marmottes bas_station
    add-gate M1 marmottes
    register denis@me.com Denis 896983
    add-card denis@me.com 1 cartex
    checkout denis@me.com
    add-pass denis@me.com 1 classic 201 full_day 1 2021-05-06 adult bas_station
    checkout denis@me.com
    passage 201 M1
    
## Demo 1
    pass-analytics 07 05 2021
    visit-analytics 07 05 2021
    add-zone snow_park
    add-skilift ride snow_park
    add-gate R1 ride
    register kevin@duchamps.fr Kevin 896983
    add-card kevin@duchamps.fr 1 cartex
    checkout kevin@duchamps.fr
    add-pass kevin@duchamps.fr 1 classic 201 full_day 2 2021-05-06 adult snow_park
    checkout kevin@duchamps.fr
    passage 201 R1
    passage 201 R1
    pass-analytics 07 05 2021
    visit-analytics 07 05 2021
    
## Demo 2
    pass-analytics 07 05 2021
    visit-analytics 07 05 2021
    add-zone ski_park
    add-skilift chevreau ski_park
    add-gate C1 chevreau
    register titi@hotmal.fr Titi 896983
    add-card titi@hotmal.fr 1 cartex
    checkout titi@hotmal.fr
    add-pass titi@hotmal.fr1 classic 201 full_day 2 2021-05-06 adult ski_park
    checkout titi@hotmal.fr
    passage 201 C1
    passage 201 C1
    pass-analytics 07 05 2021
    visit-analytics 07 05 2021

