import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.pojo.*;


public class LivreTest extends TestCase {
	
	Livre l1, l2;
	Exemplaire e1, e2;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		l1 = new Livre();
		l2 = new Livre();
		l2.setIsbn(9780);
		l2.setTitre("The Tipping Point: How Little Things Can Make a Big Difference");
		e1 = new Exemplaire();
		e1.setId(1);
		e2 = new Exemplaire();
		e2.setId(2);
		l2.addExemplaire(e1);
	}

	public void testGetISBN() {
		assertNull(l1.getIsbn());
		assertEquals(9780, l2.getIsbn().intValue());
	}

	public void testSetISBN() {
		l1.setIsbn(9680);
		assertEquals(9680, l1.getIsbn().intValue());
		l2.setIsbn(9880);
		assertEquals(9880, l2.getIsbn().intValue());
	}
	
	public void testGetTitre() {
		assertNull(l1.getTitre());
		assertEquals("The Tipping Point: How Little Things Can Make a Big Difference", l2.getTitre());
	}

	public void testSetTitre() {
		l1.setTitre("testTitre1");
		assertEquals("testTitre1", l1.getTitre());
		l2.setTitre("testTitre2");
		assertEquals("testTitre2", l2.getTitre());
	}
	
	public void testGetExemplaires() {
		assertTrue(l1.getExemplaireList().isEmpty());
		assertEquals(1, l2.getExemplaireList().size());
	}
	
	public void testSetExemplaires() {
		l1.setExemplaireList(l2.getExemplaireList());
		assertEquals(1, l1.getExemplaireList().size());
	}
	
	public void testAddExemplaire() {
		l2.addExemplaire(e2);
		assertEquals(2, l2.getExemplaireList().size());
	}
	
	public void testRemoveExemplaire() {
		l2.removeExemplaire(e1);
		assertTrue(l2.getExemplaireList().isEmpty());
	}
	
	public static Test suite() {
		return new TestSuite(LivreTest.class);
	}
	
}
