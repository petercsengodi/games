package hu.csega.superstition.collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.csega.games.library.collection.Pool;
import hu.csega.games.library.collection.PoolItem;

public class PoolTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		Pool<PoolTestItem> pool = new Pool<>(PoolTestItem.class, 10);

		PoolItem<PoolTestItem> allocated1 = pool.allocate();
		allocated1.getObject().name = "First";
		PoolItem<PoolTestItem> allocated2 = pool.allocate();
		allocated2.getObject().name = "Second";
		PoolItem<PoolTestItem> allocated3 = pool.allocate();
		allocated3.getObject().name = "Third";

		Assert.assertEquals(3, pool.getAllocated());

		allocated2.release();

		Assert.assertEquals(2, pool.getAllocated());

		allocated1.release();
		allocated3.release();

		Assert.assertEquals(0, pool.getAllocated());
	}

	@Test(expected = IllegalStateException.class)
	public void test2() {
		Pool<PoolTestItem> pool = new Pool<>(PoolTestItem.class, 10);

		try {
			for(int i = 0; i < 10; i++)
				pool.allocate();

			Assert.assertEquals(10, pool.getAllocated());
		} catch(IllegalStateException ex) {
			Assert.fail("Too soon.");
		}

		pool.allocate();
	}

}
