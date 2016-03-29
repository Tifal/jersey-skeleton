package fr.iutinfo.skeleton.api.command;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

public interface CommandDao {
	@SqlUpdate("create table command (id integer primary key autoincrement,userid integer,addressLivraison varchar(200),addressRetrait varchar(200),dateRetrait varchar(14),dateLivraison varchar(14), price varchar(10), details varchar(1024), paid integer(1))")
	void createCommandTable();

	@SqlUpdate("insert into command ( userid,addressLivraison,addressRetrait,dateLivraison,dateRetrait,price,details,paid) values (:userid,:addressLivraison,:addressRetrait,:dateLivraison,:dateRetrait,:price,:details,:paid)")
	@GetGeneratedKeys
	int insert(@BindBean() Command command);
	
	@SqlUpdate("update from command set addressRetrait = :addressRetrait, dateRetrait = :dateRetrait, addressLivraison = :addressLivraison, dateLivraison = :dateLiraison, price = :price, details = :details where id = :id")
	@GetGeneratedKeys
	int update(@BindBean() Command command);
	
	@SqlQuery("select * from command where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
	Command findById(@Bind("id") int id);
	
	@SqlQuery("select * from command where userid = :userid")
    @RegisterMapperFactory(BeanMapperFactory.class)
	List<Command> findAllByUserId(@Bind("userid") int userid);

	@SqlUpdate("drop table if exists command")
	void dropCommandTable(); 

	@SqlQuery("select * from command")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<Command> all();

	void close();
}
