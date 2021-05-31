package fr.unice.polytech.entities;

import fr.unice.polytech.utils.CardType;
import fr.unice.polytech.utils.ItemType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
public class CardItem extends OrderItem{

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    public CardItem(){
        super(ItemType.CARD);
        this.cardType = CardType.CARTEX;
    }

    public CardItem(int quantity){
        super(quantity);
        this.type = ItemType.CARD;
        this.cardType = CardType.CARTEX;
    }

    public CardItem(CardType cardType){
        super(ItemType.CARD);
        this.cardType = cardType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardItem)) return false;
        if (!super.equals(o)) return false;
        CardItem cardItem = (CardItem) o;
        return cardType == cardItem.cardType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cardType);
    }

    @Override
    public double getPrice(){
        return cardType.getPrice();
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "CardItem{" +
                "cardType=" + cardType +
                ", type=" + type +
                ", quantity=" + quantity +
                ", available=" + available +
                ", price=" + price +
                '}';
    }
}
