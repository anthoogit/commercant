package fr.event.commercant;

import java.util.ArrayList;
import java.util.List;

public class ClientDatabase {
    private List<Client> clientList = new ArrayList<>();

    public Client getClient(int id) throws Exception {
        for (Client c : clientList) {
            System.out.println("idclient = " + c.getId());
            if (c.getId() == id) {
                int index = clientList.indexOf(c);
                return clientList.get(index);
            }
        }
        throw new Exception("Client non trouvé");
    }

    public void insertClient(Client client) {
        try {
            if (getClient(client.getId()) != null) {
                System.out.println("Le client existe déjà");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.clientList.add(client);
    }

    public int getClientDbSize() {
        return clientList.size();
    }

    public List<Client> getClientList() {
        return clientList;
    }
}
