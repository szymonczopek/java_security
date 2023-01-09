package sc.pai_security.dao;
import sc.pai_security.entity.User;
import org.springframework.data.repository.CrudRepository;
public interface UserDao extends CrudRepository<User, Integer> {
 public User findByLogin(String login);
}
