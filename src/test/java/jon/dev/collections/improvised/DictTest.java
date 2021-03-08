/*
 * 
 */
package jon.dev.collections.improvised;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import junit.framework.TestCase;

/**
 *
 * @author Essel
 */
public class DictTest extends TestCase {

    final Random r = new Random();

    public DictTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRootNewInstance() {
        Dict root = Dict.createNew();
        assertNotNull("root instance must have been created", root);

        assertNull("root name is not allowed", root.getName());

        Function<Dict, Boolean> throwAfterGettingValueOfRootNode = (d) -> {
            try {
                d.getValue();
                return false;
            } catch (Exception e) {
                return true;
            }
        };
        assertTrue(
                "exception must have been thrown because parent doesn't have "
                + "access to values directly",
                throwAfterGettingValueOfRootNode.apply(root));
    }

    public void testRootAddSubParent() {
        Dict root = Dict.createNew();
        String subName = "subParent";
        Dict sub = root.add(subName);
        assertEquals(subName, sub.getName());

        sub.add("name", "value");

        Function<Dict, Boolean> throwIfFailedAtGetCall = (d) -> {
            try {
                d.getValue();
                return true;
            } catch (Exception e) {
                return false;
            }
        };
        assertTrue("Dict.add is called creating new leaf node, "
                + "there should be no exception", throwIfFailedAtGetCall.apply(sub.get("name")));

        final Counter c = new Counter();

        IntStream.range(1, 100)
                .forEach(ignored -> {
                    root.get(subName)
                            .add("test", r.nextFloat());
                });
        
        root.get(subName)
                .forEach(ignored -> c.count());
        assertEquals(String.format("expected children of %s is 100", subName), 100, c.getI());
    }

    class Counter {

        int i;

        public Counter() {
            i = 0;
        }

        int getI() {
            return i;
        }

        void count() {
            i += 1;
        }
    }

}
