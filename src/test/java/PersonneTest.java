import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.pojo.*;


public class PersonneTest extends TestCase {
	
	Personne p1, p2;
	Exemplaire e1, e2;

	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		p1 = new Personne();
		p2 = new Personne();
		p2.setId(1);
		p2.setNom("Dupont");
		p2.setPrenom("Paul");
		e1 = new Exemplaire();
		e2 = new Exemplaire();
		e2.setId(2);
		e2.setPrix(8.64);
		p2.addExemplaireEmprunte(e2);
	}
	
	public void testGetId() {
		assertNull(p1.getId());
		assertEquals(1, p2.getId().intValue());
	}

	public void testSetId() {
		p1.setId(9680);
		assertEquals(9680, p1.getId().intValue());
		p2.setId(9880);
		assertEquals(9880, p2.getId().intValue());
	}
	
	public void testGetNom() {
		assertNull(p1.getNom());
		assertEquals("Dupont", p2.getNom());
	}

	public void testSetNom() {
		p1.setNom("testNom1");
		assertEquals("testNom1", p1.getNom());
		p2.setNom("testNom2");
		assertEquals("testNom2", p2.getNom());
	}
	
	public void testGetPrenom() {
		assertNull(p1.getPrenom());
		assertEquals("Paul", p2.getPrenom());
	}

	public void testSetPrenom() {
		p1.setPrenom("testPrenom1");
		assertEquals("testPrenom1", p1.getPrenom());
		p2.setPrenom("testPrenom2");
		assertEquals("testPrenom2", p2.getPrenom());
	}
	
	public void testGetExemplairesEmpruntes() {
		assertTrue(p1.getExemplaireList().isEmpty());
		assertEquals(1, p2.getExemplaireList().size());
	}
	
	public void testSetExemplairesEmpruntes() {
		p1.setExemplaireList(p2.getExemplaireList());
		assertEquals(1, p1.getExemplaireList().size());
	}
	
	public void testAddExemplaireEmprunte() {
		p2.addExemplaireEmprunte(e1);
		assertEquals(2, p2.getExemplaireList().size());
	}
	
	public void testRemoveExemplaireEmprunte() {
		p2.removeExemplaireEmprunte(e2);
		assertTrue(p2.getExemplaireList().isEmpty());
	}
	
	public static Test suite() {
		return new TestSuite(PersonneTest.class);
	}

}
