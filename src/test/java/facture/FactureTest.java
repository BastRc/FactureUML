package facture;

import java.io.PrintStream;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FactureTest {

	Catalogue catalogue;
	Client bastide;
	Facture f;
	Article disk, iPad;

	@Before
	public void setUp() {
            catalogue = new Catalogue();
            /// "disk" : code de l'article
            disk = new Article("disk", "Disque 2To", 100f);
            iPad = new Article("ipad", "IPad 2", 700f);
            catalogue.addArticle(disk);
            catalogue.addArticle(iPad);

            bastide = new Client("Rémi Bastide", "Rue du Bac, Castres");
            Date dateDuJour = new Date();
            int numeroFacture = 345;
            f = new Facture(bastide, dateDuJour, numeroFacture);
            f.ajouteLigne(iPad, 1, 0f); // 700€, 0% de remise
            f.ajouteLigne(disk, 5, 0.1f); //5*100€, 10% de remise

	}

        @Test
        public void testSetClient() {
            assertEquals("Rémi Bastide", bastide.getName());
            bastide.setName("Rémi de la Bastide");
            assertEquals("Rémi de la Bastide", bastide.getName());
            assertEquals("Rue du Bac, Castres", bastide.getAddress());
            bastide.setAddress("Rue du Bac à sable, Castres");
            assertEquals("Rue du Bac à sable, Castres", bastide.getAddress());
        }
        
        @Test
        public void testSetArticle() {
            assertEquals("IPad 2", iPad.getNom());
            iPad.setNom("IPad 3");
            assertEquals("IPad 3", iPad.getNom());
            assertEquals("ipad", iPad.getCode());
            iPad.setCode("ipad2");
            assertEquals("ipad2", iPad.getCode());
            assertEquals(700f, iPad.getPrix(), 0.001f);
            iPad.setPrix(800f);
            assertEquals(800f, iPad.getPrix(), 0.001f);
        }
        
        @Test
        public void testSetLigneFacture() {
            LigneFacture l = new LigneFacture(1, f, iPad, 0f);
            assertEquals(iPad, l.getArticleFacture());
            assertEquals(f, l.getFigureDans());
        }
        
        @Test
        public void testSetNombreLigneFacture() {
            LigneFacture l = new LigneFacture(1, f, iPad, 0f);
            l.setNombre(2);
            assertEquals(2, l.getNombre());
        }
        
        @Test (expected = IllegalArgumentException.class)
        public void testErrorSetNombreLigneFacture() {
            LigneFacture l = new LigneFacture(1, f, iPad, 0f);
            l.setNombre(-1);
        }
        
        @Test
        public void testSetTauxDeRemise() {
            LigneFacture l = new LigneFacture(1, f, iPad, 0f);
            l.setTauxRemise(0.1f);
            assertEquals(0.1f, l.getTauxRemise(), 0.001f);
        }
        
        @Test
        public void testFacture() {
            assertEquals(bastide, f.getDestinataire());
            Date d = new Date();
            assertTrue(d.equals(f.getEmission()));
            assertEquals(345, f.getNumero());
        }
        
	@Test
	public void testCatalogue() {
            assertSame(iPad, catalogue.findByCode("ipad"));
            assertSame(disk, catalogue.findByCode("disk"));		
	}
	
	@Test
	public void testTTC() {
            float tva = 0.2f;
            float expected = (1*iPad.getPrix() + 5*disk.getPrix()*(0.9f)) * (1+tva);
            f.print(System.out, tva);
            assertEquals(expected, f.montantTTC(tva), 0.001f);
	}
        
}
