package coder.dasu.meizi.data.dao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

public class UserTest extends AbstractDaoTestLongPk<UserDao, User> {

    public UserTest() {
        super(UserDao.class);
    }

    @Override
    protected User createEntity(Long key) {
        User entity = new User();
        entity.setId(key);
        return entity;
    }

}
