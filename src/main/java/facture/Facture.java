package facture;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Date;
import java.util.List;

public class Facture {

    Client destinataire;
    Date emission; 
    int numero;
    List<LigneFacture> lignes = new LinkedList<>();

    public Facture(Client destinataire, Date emission, int numero) {
        this.destinataire = destinataire;
        this.emission = emission; 
        this.numero = numero;
        
    }

    /**
     * Get the value of numero
     *
     * @return the value of numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Get the value of emission
     *
     * @return the value of emission
     */
    public Date getEmission() {
        return emission;
    }

    /**
     * Get the value of destinataire
     *
     * @return the value of destinataire
     */
    public Client getDestinataire() {
        return destinataire;
    }

    public void ajouteLigne(Article a, int nombre, float remise) {
        lignes.add(new LigneFacture(nombre, this, a, remise));
    }
    
    public float montantTTC(float tauxTVA) {
        float montant = 0; 
        for (LigneFacture l : lignes) {
            montant += l.montantLigne() * (1 + tauxTVA);
        }
        return montant;
    }
   
    public void print(PrintStream out, float tva) {
        for (LigneFacture l : lignes) {
            out.println(l.toString());
        }
    }
   
}
