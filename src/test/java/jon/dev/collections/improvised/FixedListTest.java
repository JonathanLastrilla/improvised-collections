/*
 * COPYRIGHT LASTRILLA, Jonathan
 */
package jon.dev.collections.improvised;

import junit.framework.TestCase;

/**
 *
 * @author bogarts
 */
public class FixedListTest extends TestCase {

    FixedList instance = null;

    public FixedListTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        instance = new FixedList(1);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of add method, of class FixedList.
     */
    public void testAdd() {
        Object e = null;
        instance.add(e);
        assertFalse("should be false because limit is 1", instance.add("another"));
        assertTrue("size should be 1", instance.size() == 1);

    }

}
