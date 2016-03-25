package fr.iutinfo.skeleton.api.relais;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

public interface RelaisDao {

	@SqlUpdate("create table relais (name varchar(64), address varchar(256))")
	void createRelaisTable();
	
	@SqlUpdate("insert into relais (name, address) values (:name, :address)")
	@GetGeneratedKeys
	int insert(@BindBean() Relais relais);
	
	@SqlUpdate("drop table if exists relais")
	void dropRelaisTable();
	
	@SqlUpdate("delete from relais where name = :name")
	void deleteRelais(@BindBean() Relais relais);
	
	@SqlQuery("select * from relais")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<Relais> all();
	
}
