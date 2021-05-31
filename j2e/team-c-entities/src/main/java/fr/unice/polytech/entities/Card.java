package fr.unice.polytech.entities;

import fr.unice.polytech.utils.CardType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Card {

    @Enumerated(EnumType.STRING)
    @NotNull
    private CardType cardType;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;


    @OneToOne
    private Pass pass;

    @OneToOne(mappedBy = "card")
    private Customer owner;

    private DateSerializable endDate;

    public Card(){
        this.cardType = CardType.CARTEX;
    }

    public Card(CardType cardType) {
        this.setCardType(cardType);
        this.endDate = new DateSerializable();
        this.endDate.setDate(LocalDateTime.now().plusYears(1));
    }

    public Card(CardItem cardItem){
        cardType = cardItem.getCardType();
        this.endDate = new DateSerializable();
        this.endDate.setDate(LocalDateTime.now().plusYears(1));
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Pass getPass() {
        return pass;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public DateSerializable getEndDate() {
        return endDate;
    }

    public void setEndDate(DateSerializable endDate) {
        this.endDate = endDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        if (!super.equals(o)) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", type=" + cardType +
                ", pass=" + pass +
                ", endDate=" + endDate +
                '}';
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }
}