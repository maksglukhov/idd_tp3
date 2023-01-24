
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.pojo.*;


public class ExemplaireTest extends TestCase {
	
	Exemplaire e1, e2;
	Livre l1, l2;
	Personne p1, p2;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		e1 = new Exemplaire();
		e2 = new Exemplaire();
		e2.setId(2);
		e2.setPrix(8.64);
		l1 = new Livre();
		l2 = new Livre();
		l2.setIsbn(9780);
		l2.setTitre("The Tipping Point: How Little Things Can Make a Big Difference");
		e2.setDulivre(l2);
		p1 = new Personne();
		p2 = new Personne();
		p2.setNom("Dupont");
		e2.setEmprunteur(p2);
	}

	public void testGetId() {
		assertNull(e1.getId());
		assertEquals(2, e2.getId().intValue());
	}
	
	public void testSetId() {
		e1.setId(9680);
		assertEquals(9680, e1.getId().intValue());
		e2.setId(9880);
		assertEquals(9880, e2.getId().intValue());
	}
	
	public void testGetPrix() {
		assertNull(e1.getPrix());
		assertEquals(8.64, e2.getPrix());
	}
	
	public void testSetPrix() {
		e1.setPrix(96.80);
		assertEquals(96.80, e1.getPrix());
		e2.setPrix(97.80);
		assertEquals(97.80, e2.getPrix());
	}
	
	public void testGetDuLivre() {
		assertNull(e1.getDulivre());
		assertEquals("The Tipping Point: How Little Things Can Make a Big Difference", e2.getDulivre().getTitre());
	}
	
	public void testSetDuLivre() {
		e1.setDulivre(l2);
		assertEquals("The Tipping Point: How Little Things Can Make a Big Difference", e1.getDulivre().getTitre());
		e2.setDulivre(l1);
		assertNull(e2.getDulivre().getIsbn());
	}
	
	public void testGetEmprunteur() {
		assertNull(e1.getEmprunteur());
		assertEquals("Dupont", e2.getEmprunteur().getNom());
	}
	
	public void testSetEmprunteur() {
		e1.setEmprunteur(p2);
		assertEquals("Dupont", e1.getEmprunteur().getNom());
		e2.setEmprunteur(p1);
		assertNull(e2.getEmprunteur().getNom());
	}
	
	
	
	public static Test suite() {
		return new TestSuite(ExemplaireTest.class);
	}

}
