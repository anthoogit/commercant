package fr.event.commercant;

import java.util.ArrayList;
import java.util.List;

public class OfferDatabase {
    private List<Offer> offerList = new ArrayList<>();

    public Offer getOffer(int id) {
        return offerList.get(id);
    }

    public List<Offer> getAllOffers(){
        return offerList;
    }

    public void insertOffer(Offer offer) {
        this.offerList.add(offer);
    }
}
