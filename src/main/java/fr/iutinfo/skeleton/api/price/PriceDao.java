package fr.iutinfo.skeleton.api.price;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

public interface PriceDao {
	@SqlUpdate("create table prices (item varchar(100) primary key, price varchar(10))")
	void createPriceTable();

	@SqlUpdate("insert into prices (item, price) values (:item, :price)")
	@GetGeneratedKeys
	int insert(@BindBean() Price user);

	@SqlQuery("select * from prices where item = :item")
    @RegisterMapperFactory(BeanMapperFactory.class)
	Price findByName(@Bind("item") String item);

	@SqlUpdate("drop table if exists prices")
	void dropPriceTable(); 

	@SqlQuery("select * from prices")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<Price> all();

	void close();
}
